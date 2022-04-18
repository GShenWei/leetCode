# -*- coding: utf8 -*-
import hashlib
import time
import requests


def do_action(url, actName, actArgs):
    jobKey = "3TRvnELjNf3QwM6wyEfNctpTMVPkzoQouz2sJD5f}"
    ts = int(time.time())
    str = '%s%s%s%d\n' % (actName, actArgs, jobKey, ts)
    m = hashlib.md5()
    m.update(str.encode('utf-8'))
    sign = m.hexdigest()
    print(str)
    print(sign)
    r = requests.get(url=url, params={'ActName': actName, 'Args': actArgs, 'Ts': ts, 'Sign': sign})
    print(r.content.decode('utf-8'))


def main_handler(event, context):
    mg = event.get('Message')
    do_action('https://test-nsiapi-app.campus.qq.com/v1/examination/external/DoSomething', mg, 'a')


if __name__ == '__main__':
    main_handler("", "")
