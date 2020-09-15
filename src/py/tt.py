from typing import List
import unittest


class Solution:
    def containsPattern(self, arr: List[int], m: int, k: int) -> bool:
        for v in arr:
            print(v)
        arr = map(lambda x: x + 2, arr)
        for v in arr:
            print(v)
        return True


class MyTestCase(unittest.TestCase):
    def test_something(self):
        so = Solution()
        print(so.containsPattern([1, 2, 3, 4, 5], 1, 3))
        print(11111)


if __name__ == '__main__':
    unittest.main()
