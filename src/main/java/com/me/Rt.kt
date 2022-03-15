package com.me

import kotlinx.coroutines.*
import org.junit.Test
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class Rt {
    @Test
    fun xx() {
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
            println("World!") // 在延迟后打印输出
        }
        println("Hello,") // 协程已在等待时主线程还在继续
        runBlocking {     // 但是这个表达式阻塞了主线程
            delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
        }
    }

    @Test
    fun writeData() {
        val file = File("/Users/weichen/docs/临时文件", "xxxx.csv")
        if (!file.exists()) {
            file.createNewFile()
        } else {
            file.delete()
            file.createNewFile()
        }
        file.appendText("id,name,age\n")
        var ss = ""
        val str = "abcdefghijklmnopqrstuvwxyz"
        val r = Random(Date().time)
        val len = str.length

        var id = 1
        for (x in 1..50) {
            for (y in 0..10000) {
                var s = ""
                for (j in 0..8) {
                    s += str[r.nextInt(len)]
                }
                ss += ("$id,$s,${r.nextInt(100000)}\n")
                id++
            }
            file.appendText(ss, Charsets.UTF_8)
        }
    }

    // /Users/weichen/docs/临时文件/tx
    @Test
    fun writeData1() {
        val file = File("/Users/weichen/docs/临时文件", "gg.csv")
        if (!file.exists()) {
            file.createNewFile()
        } else {
            file.delete()
            file.createNewFile()
        }
        var ss = ""
        var id = 1
        val r = Random(Date().time)
        repeat(1000) {
            ss += ("$id,${r.nextInt(100000)}\n")
            id++
        }
        file.appendText(ss, Charsets.UTF_8)
    }

    @Test
    fun tt() = runBlocking {
        val id = AtomicInteger(1)

        val str = "abcdefghijklmnopqrstuvwxyz"
        val r = Random(Date().time)
        val len = str.length
        val request = launch {
            repeat(10) { i -> // 启动少量的子作业
                launch(Dispatchers.IO) {
                    val file = File("/Users/weichen/docs/临时文件/tx2", "x$i.csv")
                    repeat(100) {
                        var ss = ""
                        repeat(10000) {
                            var s = ""
                            for (j in 0..8) {
                                s += str[r.nextInt(len)]
                            }
                            ss += ("$id,$s,${r.nextInt(100000)}\n")
                            id.incrementAndGet()
                        }
                        file.appendText(ss, Charsets.UTF_8)
                    }
                }
            }
        }
        request.join() // 等待请求的完成，包括其所有子协程
    }
}
