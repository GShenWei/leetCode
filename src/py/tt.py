from typing import List
import unittest
import pandas as pd
import numpy as np


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

    def test_ahaha(self):
        x = {'key1': ['a', 'a', 'b', 'b', 'a'],
             'key2': ['one', 'two', 'one', 'two', 'one'],
             'data1': np.random.randn(5),
             'data2': np.random.randn(5)}
        df = pd.DataFrame(x)
        print(df)


if __name__ == '__main__':
    unittest.main()
