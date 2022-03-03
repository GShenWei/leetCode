package exam

import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


class KanBanData {
    class Student {
        var name: String = ""
        var classId: String = ""
        var gradeId: String = ""
        var recordList: MutableList<Record> = mutableListOf()
    }

    class Record {
        var sportId: String = ""
        var count: Int = 0
        var useTime: Int = 0
    }


    val sportMap = mapOf(
        Pair("s01", "跳绳"),
        Pair("s02", "仰卧起坐"),
        Pair("s03", "俯卧撑")
    )
    val sportTypeMap = mapOf(
        Pair("s01", "in"),
        Pair("s02", "out"),
        Pair("s03", "in")
    )
    val sportMaxMap = mapOf(
        Pair("s01", 200),
        Pair("s02", 20),
        Pair("s03", 50)
    )

    val disId = 1
    val disName = "特别区"

    val schoolId = "101"
    val schoolName = "神奇学校"

    val classGradeMap = mapOf(
        Pair("c01", "g01"),
        Pair("c02", "g01"),
        Pair("c03", "g02")
    )
    val classNameMap = mapOf(
        Pair("c01", "一班"),
        Pair("c02", "二班"),
        Pair("c03", "三班")
    )
    val classStuCount = mapOf(
        Pair("c01", 50),
        Pair("c02", 55),
        Pair("c03", 56)
    )
    val gradeNameMap = mapOf(
        Pair("g01", "一年级"),
        Pair("g02", "二年级")
    )
    private var formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val randomRd = Random(System.currentTimeMillis())

    @Test
    fun start() {
        val now = LocalDateTime.now()
        for (i in -30..15) {
            val d = now.minusDays(i.toLong())
            soutOneDay(d.format(formatter), 1)
        }
        println()
        println()
        for (i in -30..15) {
            val d = now.minusDays(i.toLong())
            soutOneDay(d.format(formatter), 2)
        }
    }

    private fun soutOneDay(day: String, ty: Int) {
        val ttStuLi = mutableListOf<Student>()
        for (p in classNameMap) {
            val stuLi = getStudent(p.key)
            for (stu in stuLi) {
                for (s in sportMap) {
                    val record = getRecord(s.key)
                    if (record != null) {
                        stu.recordList.add(record)
                    }
                }
            }
            ttStuLi.addAll(stuLi)
        }
        if (ty == 1) {
            ptForClass(ttStuLi, day)
        } else if (ty == 2) {
            ptForSubject(ttStuLi, day)
        }
    }

    private fun ptForClass(stuLi: List<Student>, day: String) {
        stuLi.groupBy { it.gradeId }.forEach { (gradeId, gradeStuLi) ->
            gradeStuLi.groupBy { it.classId }.forEach { (classId, claStuLi) ->
                print("('$schoolId', '$schoolName', '$disId', '$disName', '$gradeId', '${gradeNameMap[gradeId]}', '$classId', '${classNameMap[classId]}' ")
                val inTime =
                    claStuLi.flatMap { it.recordList }.filter { "in" == sportTypeMap[it.sportId] }.sumOf { it.useTime }
                val outTime =
                    claStuLi.flatMap { it.recordList }.filter { "out" == sportTypeMap[it.sportId] }.sumOf { it.useTime }
                val userCount = classStuCount[classId]
                // 没有记录的就是没有参加的
                val joinCount = userCount?.minus(claStuLi.count { it.recordList.size == 0 })
                val taskCount = userCount?.times(sportMap.size)
                val finishCount = claStuLi.sumOf { it.recordList.size }
                print(", $inTime, $outTime, $userCount, $joinCount, $taskCount, $finishCount, $day, '日'),")
                println()
            }
        }
    }

    private fun ptForSubject(stuLi: List<Student>, day: String) {
        stuLi.groupBy { it.gradeId }.forEach { (gradeId, gradeStuLi) ->
            val userCount = classStuCount.filter { gradeId == classGradeMap[it.key] }.map { it.value }.sum()
            gradeStuLi.map { it.recordList }.flatten().groupBy { it.sportId }.forEach { sportId, recordLi ->
                print("('$schoolId', '$schoolName', '$disId', '$disName', '$gradeId', '${gradeNameMap[gradeId]}', '$sportId', '${sportMap[sportId]}' ")
                // 有多少这个体育的记录就有多少人参加
                val joinCount = recordLi.size
                // 每人一条任务
                val overallTotalCount = userCount
                val overallFinishCount = gradeStuLi.count {
                    for (record in it.recordList) {
                        if (record.sportId == sportId) {
                            return@count true
                        }
                    }
                    return@count false
                }
                val opm = recordLi.map { it.count / it.useTime }.average()
                print(", $userCount, $joinCount, $overallTotalCount, $overallFinishCount, $overallFinishCount, $opm, $day, '日'),")
                println()
            }
        }
    }

    private fun getStudent(classId: String): List<Student> {
        val li = mutableListOf<Student>()
        for (i in 1..classStuCount[classId]!!) {
            li.add(Student().apply {
                this.name = "学生$i"
                this.classId = classId
                this.gradeId = classGradeMap[classId]!!
            })
        }
        return li
    }

    private fun getRecord(sportId: String): Record? {

        if (randomRd.nextInt(10) == 0) {
            return null
        }
        return Record().apply {
            this.sportId = sportId
            this.count = randomRd.nextInt(sportMaxMap[sportId]!!)
            this.useTime = randomRd.nextInt(1, 30)
        }
    }
}

