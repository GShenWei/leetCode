package q0100_0199.q198_house_robber

import org.junit.Test
import kotlin.math.max

class Q198Solution {
    fun rob(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        val peak = IntArray(nums.size)
        return when (nums.size) {
            1 -> nums[0]
            2 -> max(nums[0], nums[1])
            else -> {
                peak[0] = nums[0]
                peak[1] = max(nums[0], nums[1])
                for (i in 2 until nums.size) {
                    peak[i] = max(peak[i - 2] + nums[i], peak[i - 1])
                }
                peak[nums.size - 1]
            }
        }
    }

    @Test
    fun xx() {
        val x = intArrayOf(2)
        println(rob(x))
    }
}