import unittest


class MyTestCase(unittest.TestCase):
    def test_something(self):
        self.assertEqual(True, False)

    def test_te(self):
        a = 1
        b = 2
        print(a +b)


if __name__ == '__main__':
    unittest.main()
