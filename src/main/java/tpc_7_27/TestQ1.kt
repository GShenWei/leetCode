package tpc_7_27

import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    scanner.nextLine()
    for (i in 0 until n) {
        val a = stringToInt(scanner.nextLine().split(" "))
        val b = stringToInt(scanner.nextLine().split(" "))
        val c = stringToInt(scanner.nextLine().split(" "))
        println(xx(a, b, c))
    }
}

fun stringToInt(li: List<String>): List<Int> {
    return li.map { it.toInt() }
}

fun xx(a: List<Int>, b: List<Int>, c: List<Int>): String {
    val req = a[1]
    val d = mutableListOf<Pair<Int, Int>>()
    for (v in b.withIndex()) {
        val ha = if (v.index == 0) {
            v.value + c[v.index]
        } else {
            v.value
        }
        d.add(Pair(v.index, ha))
    }
    d.sortByDescending { it.second }
    var rank = 0
    var count = 0
    var pre = -1
    var tencentRank = 0
    for (pair in d) {
        count++
        if (pre != pair.second) {
            rank = count
        }
        pre = pair.second
        if (pair.first == 0) {
            tencentRank = rank
            break
        }
    }
    //println(tencentRank)
    return if (tencentRank <= req) {
        "Yes"
    } else {
        "No"
    }
}
