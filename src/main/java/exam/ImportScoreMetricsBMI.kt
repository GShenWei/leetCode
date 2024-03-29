package exam

import com.alibaba.excel.context.AnalysisContext
import com.alibaba.excel.enums.CellDataTypeEnum
import com.alibaba.excel.event.AnalysisEventListener
import com.alibaba.excel.metadata.data.CellData
import java.io.File
import java.math.BigDecimal


class ScoreMetricsBMIListener(
    private val currentDir: String,
    private val outFile: String,
    private val sex: String,
    private val subject: Int,
    private val biggerBest: Boolean,
    private val isPlus: Boolean,
) :
    AnalysisEventListener<Map<Int, Any>>() {
    private val li: MutableList<Metrics> = mutableListOf()
    private val headMap: MutableMap<Int, String> = mutableMapOf()
    private var file: File? = null

    init {
        val dir = File(currentDir)
        if (dir.exists()) {
            dir.deleteRecursively()
        }
        dir.mkdirs()
    }

    override fun doAfterAllAnalysed(context: AnalysisContext) {
        //println("完成所有")
        dealData()
    }

    var lastLevel: String? = null
    override fun invoke(data: Map<Int, Any>, context: AnalysisContext) {
        val rowIndex = context.readRowHolder().rowIndex
        //print("收到一条数据$rowIndex")
        //println(data)
        val cellMap = context.readRowHolder().cellMap
        val mtx = Metrics()
        cellMap.forEach { (k, v) ->
            var s = headMap[k]
            if (s == null) {
                s = ""
            }
            if (s == "等级") {
                mtx.levelName = v.toString()
            }
            v as CellData<*>
            when (s) {
                "等级" -> {
                    val currentLevel = v.stringValue?.replace("""\s""".toRegex(), "")
                    if (lastLevel == null) {
                        lastLevel = currentLevel
                    }
                    if (currentLevel == null) {
                        mtx.levelName = lastLevel
                    } else {
                        if (currentLevel != lastLevel) {
                            lastLevel = currentLevel
                        }
                        mtx.levelName = lastLevel
                    }
                }
                "单项得分", "加分" -> mtx.cmptScore = v.numberValue?.toDouble()
                else -> {
                    try {
                        val grade = Integer.parseInt(s)
                        when (v.type) {
                            CellDataTypeEnum.STRING -> {
                                if (v.stringValue != null) {
                                    if (v.stringValue.contains("~")) {
                                        val split = v.stringValue.split("~")
                                        val low = (split[0].toBigDecimal() - BigDecimal("0.1")).setScale(1)
                                        val high = split[1].toDouble().toBigDecimal().setScale(1)
                                        mtx.gradeRange[grade] = "(${low},$high]"
                                    } else {
                                        if (v.stringValue.contains("≤")) {
                                            val xx = v.stringValue.replace("≤", "").toDouble()
                                            mtx.gradeRange[grade] = "(-∞,$xx]"
                                        }
                                        if (v.stringValue.contains("≥")) {
                                            val xx = v.stringValue.replace("≥", "").toBigDecimal().setScale(1)
                                            val newX = (xx - BigDecimal("0.1")).setScale(1)
                                            mtx.gradeRange[grade] = "($newX,+∞)"
                                        }
                                    }
                                }
                            }
                            else -> {
                                // 不能处理空白的项目，这个年级不能有数据
                                return
                            }
                        }
                    } catch (_: NumberFormatException) {
                    }
                }
            }
        }
        li.add(mtx)
        if (li.size == 300) {
            dealData()
            li.clear()
        }
    }

    private var headMappingMap = mapOf(
        Pair("一年级", 1),
        Pair("二年级", 2),
        Pair("三年级", 3),
        Pair("四年级", 4),
        Pair("五年级", 5),
        Pair("六年级", 6),
        Pair("初一", 7),
        Pair("初二", 8),
        Pair("初三", 9),
        Pair("高一", 10),
        Pair("高二", 11),
        Pair("高三", 12),
    )
    private var plusMap = mutableMapOf<Int, Double>()
    private var basePlus: Double = 0.0
    var rg = """\s""".toRegex()
    override fun invokeHeadMap(data: Map<Int, String>, context: AnalysisContext) {
        val rowIndex = context.readRowHolder().rowIndex
        if (rowIndex == 0) {
            for ((k, v) in data) {
                if (v == null) {
                    continue
                }
                val v2 = v.replace(rg, "")
                if (headMappingMap.contains(v2)) {
                    headMap[k] = headMappingMap[v2].toString()
                } else {
                    headMap[k] = v2
                }
            }
            file = if (outFile.isNotEmpty()) {
                File("$currentDir/$outFile")
            } else {
                val stName = context.readSheetHolder().sheetName
                File("$currentDir/$stName.txt")
            }
        }
    }

    private fun dealData() {
        // insert 顺序需要固定
        var sql =
            """ insert into t_subject_metrics(actual_grade, sex, subject_id, score_range, cmpt_score, int_level, level_name, is_plus,
                              proportion) values """
        val resLi = mutableListOf<String>()
        for ((i, data) in li.withIndex()) {
            for ((gd, score) in data.gradeRange) {
                val mex = MetricsData()
                mex.actualGrade = gd
                mex.sex = "'${sex}'"
                mex.subjectId = subject
                mex.scoreRange = "'${data.gradeRange[gd]}'"
                mex.cmptScore = data.cmptScore
                mex.intLevel = when (data.levelName) {
                    "优秀", "正常" -> {
                        1
                    }
                    "良好", "低体重" -> {
                        2
                    }
                    "及格", "超重" -> {
                        3
                    }
                    "不及格", "肥胖" -> {
                        4
                    }
                    else -> {
                        0
                    }
                }
                mex.levelName = "'${data.levelName}'"
                mex.isPlus = if (isPlus) 1 else 0
                mex.proportion = 0.0
                dealProportion(mex)
                val res = getStringList(mex)
                resLi.add(res.joinToString(",", "(", ")") { it })
            }
        }
        sql += resLi.joinToString(",")
        println(sql)
        file?.appendText("$sql;\n")
    }

    private fun getStringList(dt: MetricsData): List<String> {
        val x = mutableListOf<String>()
        x.add(dt.actualGrade.toString())
        x.add(dt.sex.toString())
        x.add(dt.subjectId.toString())
        x.add(dt.scoreRange.toString())
        x.add(dt.cmptScore.toString())
        x.add(dt.intLevel.toString())
        x.add(dt.levelName.toString())
        x.add(dt.isPlus.toString())
        x.add(dt.proportion.toString())
        return x
    }

    private fun dealProportion(dt: MetricsData) {
        val sbjMapRevert = mutableMapOf<Int, String?>()
        for ((k, v) in sbjMap) {
            sbjMapRevert[v] = k
        }
        val sbj = sbjMapRevert[dt.subjectId]
        when (dt.actualGrade) {
            1, 2 -> {
                dt.proportion = m12[sbj]
            }
            3, 4 -> {
                dt.proportion = m34[sbj]
            }
            5, 6 -> {
                dt.proportion = m56[sbj]
            }
            else -> {
                dt.proportion = mOther[sbj]
            }
        }
    }

    private val sbjMap = mutableMapOf(
        Pair("肺活量", 10000004),
        Pair("50米", 10000019),
        Pair("坐位体前屈", 10000052),
        Pair("立定跳远", 10000038),
        Pair("仰卧起坐", 10000013),
        Pair("折返跑", 10000031),
        Pair("跳绳", 10000036),
        Pair("引体", 10000011),
        Pair("1000米跑", 10000029),
        Pair("800米跑", 10000030),
        Pair("体重指数（BMI）", 10000003),
    )
    private val m12 = mutableMapOf(
        Pair("体重指数（BMI）", 0.15),
        Pair("肺活量", 0.15),
        Pair("50米", 0.20),
        Pair("坐位体前屈", 0.30),
        Pair("跳绳", 0.20),
    )
    private val m34 = mutableMapOf(
        Pair("体重指数（BMI）", 0.15),
        Pair("肺活量", 0.15),
        Pair("50米", 0.20),
        Pair("坐位体前屈", 0.20),
        Pair("跳绳", 0.20),
        Pair("仰卧起坐", 0.10),
    )
    private val m56 = mutableMapOf(
        Pair("体重指数（BMI）", 0.15),
        Pair("肺活量", 0.15),
        Pair("50米", 0.20),
        Pair("坐位体前屈", 0.10),
        Pair("跳绳", 0.10),
        Pair("仰卧起坐", 0.20),
        Pair("折返跑", 0.10),
    )
    private val mOther = mutableMapOf(
        Pair("体重指数（BMI）", 0.15),
        Pair("肺活量", 0.15),
        Pair("50米", 0.20),
        Pair("坐位体前屈", 0.10),
        Pair("立定跳远", 0.10),
        Pair("引体", 0.10),
        Pair("仰卧起坐", 0.10),
        Pair("1000米跑", 0.20),
        Pair("800米跑", 0.20),
    )
}

