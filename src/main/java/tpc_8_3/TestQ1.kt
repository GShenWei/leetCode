package tpc_8_3

import java.util.*

fun stringToInt(li: List<String>): List<Int> {
    return li.map { it.toInt() }
}

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    scanner.nextLine()
    for (i in 0 until n) {
        val a = stringToInt(scanner.nextLine().split(" "))
        val b = stringToInt(scanner.nextLine().split(" "))
        println(xx(a, b))
    }
}

fun xx(a: List<Int>, b: List<Int>): String {
    val n = a[0]
    val k = a[1]

    val ok = check(b, k)
    if (!ok) {
        return "INF"
    }
    val winMp = arrayOfNulls<Int>(b.size + 1)
    var notZeroIndex = -1
    var i = 0
    while (true) {
        var score = 0
        val win = getWin(b, ++i)
        score = if (notZeroIndex != win || winMp[win] == null) {
            1
        } else {
            winMp[win]!! + 1
        }
        notZeroIndex = win
        winMp[win] = score
        if (winMp[win] == k) {
            return "$i $win"
        }
        if (i>=1000){
            return "INF"
        }
    }
}

fun check(li: List<Int>, k: Int): Boolean {
    val newLi = mutableListOf<Int>()
    newLi.addAll(li)
    newLi.addAll(li)
    var i = 1
    var pre = newLi[0]
    var maxSame = 0
    var same = 1
    while (i <= newLi.size - 1) {
        if (pre == newLi[i]) {
            same++
        } else {
            same = 1
        }
        if (maxSame < same) {
            maxSame = same
        }
        pre = newLi[i]
        i++
    }
    if (maxSame == newLi.size) {
        return true
    }
    if (maxSame < k) {
        return false
    }
    return true
}

fun getWin(li: List<Int>, w: Int): Int {
    val g = w - 1
    return li[g % li.size]
}
