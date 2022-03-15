package exam

import com.alibaba.fastjson.JSON
import org.junit.Test
import java.io.File
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import kotlin.random.Random

class KanBanSourceData {
    companion object Const {
        val FD = WeekFields.of(DayOfWeek.MONDAY, 7)
        val RDD = Random(System.currentTimeMillis())
    }

    data class Student(
        var studentId: Int,
        var studentName: String,
        var classInf: Class,
        var gradeInf: Grade,
        var schoolInf: School,
    )

    data class Class(
        var classId: Int,
        var className: String,
        var gradeInf: Grade,
    )

    data class Grade(
        var gradeId: Int,
        var gradeName: String,
        var schoolInf: School,
    )

    data class School(
        var districtId: String,
        var districtName: String,
        var schoolId: Int,
        var schoolName: String,
    )


    data class Sport(
        var sportId: Int,
        var sportName: String,
        // 最大运动次数
        var maxCount: Int,
        var type: String,

        var timesPerTask: Int,// 任务要求数量
        var taskCount: Int,//任务要求次数
        var target: Int, //每天的目标
    )

    data class RandData(
        var studentList: List<Student>,
        var classList: List<Class>,
        var gradeList: List<Grade>,
        var schoolList: List<School>,
        var sportList: List<Sport>,
    ) {
        fun getAllTaskCount(): Int {
            var res = 0
            for (sport in sportList) {
                res += sport.taskCount
            }
            return res
        }

        fun getNewTaskPool(): List<Pair<Int, Sport>> {
            val res = mutableListOf<Pair<Int, Sport>>()
            var j = 1;
            for (sport in sportList) {
                for (i in 1..sport.taskCount) {
                    res.add(Pair(j++, sport))
                }
            }
            return res
        }


        fun getTotalCount(timeId: String, timeType: Int): Int {
            val now = LocalDate.now()
            var tm = now
            return when (timeType) {
                1 -> {
                    //如果是天，那么每天的目标数目就是1
                    1
                }
                2 -> {
                    val y = timeId.substring(0, 4)
                    val w = timeId.substring(4, 6)
                    tm = tm.withYear(y.toInt())
                    tm = tm.with(FD.weekOfYear(), w.toLong())
                    // 如果日期到今天位置小于7天，那么以实际为准，否则为7
                    if (tm.year == now.year && Period.between(tm, now).days + 1 < 7) {
                        Period.between(tm, now).days + 1
                    } else {
                        7
                    }
                }
                3 -> {
                    val y = timeId.substring(0, 4)
                    val w = timeId.substring(4, 6)
                    tm = tm.withYear(y.toInt())
                    tm = tm.withMonth(w.toInt())
                    if (tm.year == now.year && tm.month == now.month) {
                        Period.between(tm, now).days + 1
                    } else {
                        tm.plusMonths(1).minusDays(1).dayOfMonth
                    }
                }
                else -> 0
            }
        }

    }

    private fun getRandStuList(): RandData {
        val schoolList = mutableListOf(
            School("1", "神奇区", 102, "完小2"),
            School("1", "神奇区", 103, "完小3")
        )
        val gradeList = mutableListOf<Grade>()
        val classList = mutableListOf<Class>()
        val studentList = mutableListOf<Student>()

        for (s in schoolList) {
            for (i in 1..6) {
                val gd = Grade("${s.schoolId}$i".toInt(), "${i}年级", s)
                gradeList.add(gd)
                for (j in 1..3) {
                    val cl = Class("${gd.gradeId}$j".toInt(), "${j}班", gd)
                    classList.add(cl)
                    for (k in 1..RDD.nextInt(5, 20)) {
                        val st = Student("${cl.classId}$k".toInt(), "学生${k}", cl, gd, s)
                        studentList.add(st)
                    }
                }
            }
        }
        val sportList = mutableListOf(
            Sport(901, "跳绳", 100, "out", 50, 2, 100),
            Sport(902, "仰卧起坐", 20, "in", 20, 3, 40),
            Sport(903, "俯卧撑", 10, "in", 10, 3, 30),
            Sport(904, "开合跳", 30, "out", 30, 3, 90),
            Sport(905, "仰卧举腿", 20, "in", 10, 3, 30),
            Sport(906, "踢毽子", 50, "out", 20, 3, 60),
        )

        return RandData(studentList, classList, gradeList, schoolList, sportList)
    }


    data class Record(
        var student: Student,
        var sport: Sport,
        var count: Int,
        var useTime: Int,
        var date: LocalDate,
    )

    private val days = 30
    fun gen(rdData: RandData): List<Record> {
        val now = LocalDate.now()
        val res = mutableListOf<Record>()
        for (t in -days..days) {
            for (stu in rdData.studentList) {
                // 每天每次可以选择要不要做这个任务，如果选择不做，或者没有完成，那么这个任务会被还回去
                // 所欲完成任务的实际由，任务数量的1.5被决定
                var taskPool = rdData.getNewTaskPool()
                for (i in 1..(taskPool.size * 1.2).toInt()) {
                    // 选择要不要做
                    if (RDD.nextInt(0, 10) >= 3) {
                        val task = taskPool.last()
                        val rec = rdSportRecord(stu, task.second, now.plusDays(t.toLong()))
                        if (rec.count >= task.second.timesPerTask) {
                            taskPool = taskPool.dropLast(1)
                        }
                        res.add(rec)
                    }
                }
            }
        }
        return res
    }

    private fun rdSportRecord(stu: Student, sport: Sport, now: LocalDate): Record {
        return Record(stu, sport, RDD.nextInt(0, sport.maxCount), RDD.nextInt(5, 20), now)
    }

    fun createDataClassDay(
        rdData: RandData, recordList: List<Record>, depType: String, periodType: Int
    ): Pair<List<DtRw>, List<DtMb>> {
        val timeG = fun(d: LocalDate): Int {
            return when (periodType) {
                // 1 日  2 周  3 月
                1 -> {
                    d.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt()
                }
                2 -> {
                    "${d.year}${String.format("%02d", d.get(FD.weekOfYear()))}".toInt()
                }
                3 -> {
                    "${d.year}${String.format("%02d", d.month.value)}".toInt()
                }
                else -> 0
            }
        }
        // 根据组织分组的数据
        val mapByDep = recordList.groupBy {
            when (depType) {
                "class" -> Pair(it.student.classInf.classId, it.student.classInf.className)
                "grade" -> Pair(it.student.gradeInf.gradeId, it.student.gradeInf.gradeName)
                else -> Pair(it.student.schoolInf.schoolId, it.student.schoolInf.schoolName)
            }
        }
        val res = Pair<MutableList<DtRw>, MutableList<DtMb>>(mutableListOf(), mutableListOf())
        // 下面根据组织id来统计
        mapByDep.forEach { (depInf, reLi) ->
            val schoolId = reLi[0].student.schoolInf.schoolId
            val schoolName = reLi[0].student.schoolInf.schoolName
            val deptName: String
            val parentDepId: Int
            val parentDepName: String
            when (depType) {
                "class" -> {
                    deptName = reLi[0].student.classInf.className
                    parentDepId = reLi[0].student.gradeInf.gradeId
                    parentDepName = reLi[0].student.gradeInf.gradeName
                }
                "grade" -> {
                    deptName = reLi[0].student.gradeInf.gradeName
                    parentDepId = reLi[0].student.schoolInf.schoolId
                    parentDepName = reLi[0].student.schoolInf.schoolName
                }
                else -> {
                    parentDepId = 0
                    parentDepName = ""
                }
            }
            val userCount = rdData.studentList.count {
                when (depType) {
                    "class" -> it.classInf.classId == depInf.first
                    "grade" -> it.gradeInf.gradeId == depInf.first
                    else -> false
                }
            }
            // 按照每种体育来分类
            reLi.groupBy { it.sport }.forEach { (spt, sptReLi) ->
                val sportId = spt.sportId
                val sportName = sptReLi[0].sport.sportName
                // 按照时间再次分组
                sptReLi.groupBy { timeG(it.date) }.forEach { (tm, tmReLi) ->
                    val goalTime = tmReLi[0].date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                    val joinCount = tmReLi.distinctBy { it.student.studentId }.count()
                    // 基础目标数量（其实就是天数）*学生人数
                    val overallTotalCount = rdData.getTotalCount(tm.toString(), periodType) * userCount
                    // 有多少个记录，然后判断是否完成
                    // 再次将记录按照学生分组，然后看学生完成了多少个
                    val overallFinishCount = tmReLi.groupBy { it.student.studentId }
                        .mapValues { it.value.sumOf { re -> re.count } > it.value[0].sport.target }
                        .count { it.value }

                    val depTypeInt = when (depType) {
                        "class" -> 5
                        "grade" -> 4
                        else -> 0
                    }

                    // 组织目标统计
                    /*println(
                        "$schoolId,$schoolName,${depInf.first},${depInf.second},$depTypeInt,$parentDepId,$parentDepName," +
                                "$userCount,$joinCount,$sportId,$sportName,$goalTime,1,1,1,1,$overallTotalCount," +
                                "$overallFinishCount,$tm,$periodType"
                    )*/
                    val mb = DtMb().apply {
                        school_org_id = schoolId.toInt()
                        school_name = schoolName
                        dep_id = depInf.first
                        dept_name = depInf.second
                        dep_type = depTypeInt
                        parent_dep_id = parentDepId
                        parent_dep_name = parentDepName
                        user_count = userCount
                        join_count = joinCount
                        sport_id = sportId
                        sport_name = sportName
                        goal_time = goalTime.toLong()
                        boy_finish_count = 1
                        boy_total_count = 1
                        girl_finish_count = 1
                        girl_total_count = 1
                        overall_total_count = overallTotalCount
                        overall_finish_count = overallFinishCount
                        stat_period = tm.toLong()
                        period_type = periodType
                        app_id = 813
                    }

                    val actionTims = tmReLi.sumOf { it.count }
                    val costTime = tmReLi.sumOf { it.useTime }
                    val tryCount = tmReLi.size
                    val taskCount = tmReLi[0].sport.taskCount * rdData.getTotalCount(tm.toString(), periodType)
                    val finishCount = tmReLi.count { it.count >= it.sport.timesPerTask }
                    val sceneType = when (tmReLi[0].sport.type) {
                        "in" -> 1
                        "out" -> 2
                        else -> 0
                    }

                    // 组织任务统计
                    /* println(
                         "$schoolId,$schoolName,${depInf.first},${depInf.second},$depTypeInt,$parentDepId,$parentDepName," +
                                 "$userCount,$joinCount,$sportId,$sportName,$actionTims,1,$costTime,1,0," +
                                 "$tryCount,$taskCount,$finishCount,$tm,$periodType,$sceneType,1,1,1,1"
                     )*/

                    val rw = DtRw().apply {
                        school_org_id = schoolId
                        school_name = schoolName
                        dep_id = depInf.first
                        dept_name = depInf.second
                        dep_type = depTypeInt
                        parent_dep_id = parentDepId
                        parent_dep_name = parentDepName
                        user_count = userCount
                        join_count = joinCount
                        sport_id = sportId
                        sport_name = sportName
                        action_tims = actionTims
                        action_times_unit = 1
                        cost_time = costTime
                        cost_time_unit = 1
                        calorie = 0
                        try_count = tryCount
                        task_count = taskCount
                        finish_count = finishCount
                        stat_period = tm.toLong()
                        period_type = periodType
                        scene_type = sceneType
                        boy_task_count = 1
                        boy_finish_count = 1
                        girl_task_count = 1
                        girl_finish_count = 1
                        app_id = 813
                    }
                    res.first.add(rw)
                    res.second.add(mb)
                }
            }
        }
        return res
    }

    @Test
    fun Gogog() {
        val rdData = getRandStuList()
        val recordList = gen(rdData)

        val dir = "/Users/weichen/docs/企鹅成绩/云图看板/out3"
        iniFile(dir)
        val fileName1 = "x_rw.json"
        val fileName2 = "x_mb.json"
        val fRw = File(dir, fileName1)
        val fMb = File(dir, fileName2)
        for (depTp in listOf("class", "grade")) {
            for (periodTp in listOf(1, 2, 3)) {
                val ppp = createDataClassDay(rdData, recordList, depTp, periodTp)
                for (rw in ppp.first) {
                    fRw.appendText(JSON.toJSONString(rw) + "\n")
                }
                for (rw in ppp.second) {
                    fMb.appendText(JSON.toJSONString(rw) + "\n")
                }
            }
        }
    }

    fun iniFile(dirPath: String) {
        val dir = File(dirPath)
        if (dir.exists()) {
            dir.deleteRecursively()
        }
        dir.mkdirs()
    }

}


