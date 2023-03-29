# -*- coding: UTF-8 -*-
# 数据发布
import hashlib
import hmac
import json
import random
import time

import requests

# 提供的appkey/accessToken
appKey = "cc74cbc0913611eba2707da5873410ef"
accessToken = b"88137f6df8d8422eb4540a6b680f144d"

url_dic = {'正式': 'https://gw.tvs.qq.com', '测试': 'https://gw.test.tvs.qq.com', '开发': 'https://gw.dev.tvs.qq.com'}

"""
1	教材
2	教材版本
3	章节
5	试题
6	作文
7	资源
8	题型
14	试卷
"""


def pub_data(pub_type, pub_data_ids, url_type):
    ## 获取请求数据(也就是HTTP请求的Body)
    postData = '''
        {
            "header": 
            {
            },
            "payload": 
            {
            "op_user": "v_lflu"
            }
        }
        '''

    # 先发布版本 -》再发布教材 -》再发布章节
    # entity_ids 为发布的id集合
    jsonReq = json.loads(postData)
    entity_list = [{"entity_ids": pub_data_ids, "entity_type": pub_type}]

    jsonReq["payload"]["entity_list"] = entity_list

    httpBody = json.dumps(jsonReq)

    ## 获得Unix时间戳
    credentialDate = (int(time.time()))

    # print(credentialDate)
    ## 拼接数据
    signingContent = httpBody + str(credentialDate)

    # ***** Task 2: 获取Signature签名 *****
    signature = hmac.new(accessToken, signingContent.encode('utf-8'), hashlib.sha256).hexdigest()
    # ***** Task 3: 在HTTP请求头中带上签名信息

    headers = {'Content-Type': 'application/json',
               'Appkey': appKey,
               'Timestamp': str(credentialDate),
               'Signature': signature
               }
    '''
    **** Send the request *****
    正式环境 ：https://gw.tvs.qq.com
    requestUrl = 'https://gw.tvs.qq.com/edu/openapi/v1/manager/data/confirm-pub'
    测试环境URL
    requestUrl = 'https://gw.test.tvs.qq.com/edu/openapi/v1/manager/data/confirm-pub'
    https://gw.dev.tvs.qq.com
    '''
    requestUrl = url_dic[url_type] + '/edu/openapi/v1/manager/data/confirm-pub'
    # print('Begin request...')
    # print('Request Url = ' + requestUrl)

    session = requests.session()
    session.headers.update(headers)
    # print('Request Headers =' + str(session.headers))
    # print('Request Body =' + httpBody)

    reqTime = time.time()
    r = session.post(requestUrl, data=httpBody.encode('utf-8'))
    respTime = time.time()

    # print('Response...')
    # print("HTTP Status Code:%d" % r.status_code, "cost:%f(ms)" % ((respTime - reqTime) * 1000))
    # print(r.text)
    return r.status_code, r.text


def read_kp_data(filename):
    qset = []
    f = open(filename, mode='r', encoding='utf8')
    for line in f:
        r1 = json.loads(line.strip(), encoding='utf-8')
        qset.append(int(r1['id']))
        # qset.append(int(line.strip()))

    return qset


if __name__ == '__main__':
    pub_type = {'教材': 1, '教材版本': 2, '章节': 3, '知识点': 4}

    # 一次发布
    # status_code, result_text = pub_data(1,
    #                                     [665750], '测试')
    # error_code = json.loads(result_text)['payload']['error_code']
    # print(error_code,result_text)

    # pub_data_ids = read_kp_data(path)
    pub_data_ids = [2175, 2172, 2180]
    num = 0
    for pub_id in pub_data_ids:
        num += 1
        # if num <= 18750:
        #     continue
        time1 = time.time()
        time.sleep(random.uniform(0.5, 0.55))
        status_code, result_text = pub_data(1, [pub_id], '测试')  # 正式 or 测试
        # error_code = json.loads(result_text)['payload']['error_code']
        print(str(pub_id) + result_text)

        if 0 == num % 25:
            time2 = time.time()
            print("%dprocessed%fs" % (num, time2 - time1))
            time.sleep(3)
        # if 0 == num % 1000:
        #     time2 = time.time()
        #     print("%dprocessed%fs" % (num, time2 - time1))
        #     time.sleep(60)
        # if 200 != error_code:
        #     print('%s error,msg :%s' % (pub_id, result_text))
    print('over!!!,len:%s' % num)
