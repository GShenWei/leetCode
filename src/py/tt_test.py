import hashlib
import time
import unittest
import requests


class MyTestCase(unittest.TestCase):
    def test_something(self):
        self.assertEqual(True, False)

    def test_te(self):
        a = 1
        b = 2
        m = hashlib.md5()
        m.update(b'12345\n')
        print(m.hexdigest())

    def test_te2(self):
        mg = '44343'
        print('12' + mg)

    def test_okk(self):
        url = 'https://test-nsiapi-app.campus.qq.com/v1/examination/external/DoSomething'
        actName = 'PullLaborData'

        jobKey = "3TRvnELjNf3QwM6wyEfNctpTMVPkzoQouz2sJD5f}"
        actArgs = 'a'
        ts = int(time.time())
        str = '%s%s%s%d\n' % (actName, actArgs, jobKey, ts)
        m = hashlib.md5()
        m.update(str.encode('utf-8'))
        sign = m.hexdigest()
        print(str)
        print(sign)
        r = requests.get(url=url, params={'ActName': actName, 'Args': actArgs, 'Ts': ts, 'Sign': sign})
        print(r.content.decode('utf-8'))

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


if __name__ == '__main__':
    unittest.main()
