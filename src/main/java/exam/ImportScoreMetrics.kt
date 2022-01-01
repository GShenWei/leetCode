package exam

import com.alibaba.excel.context.AnalysisContext
import com.alibaba.excel.enums.CellDataTypeEnum
import com.alibaba.excel.event.AnalysisEventListener
import com.alibaba.excel.metadata.data.CellData
import java.io.File

class Metrics : Cloneable {
    var levelName: String? = null
    var cmptScore: Double? = null
    var gradeScoreMap: MutableMap<Int, Double> = mutableMapOf()
    var gradeRange: MutableMap<Int, String> = mutableMapOf()
}

class ScoreMetricsListener(
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
                                if (v.stringValue != null && v.stringValue.contains("""['"]""".toRegex())) {
                                    // 说明可能是个时分秒
                                    val split = v.stringValue.split("""['"]""".toRegex())
                                    if (split.isNotEmpty()) {
                                        if (split.size == 2) {
                                            mtx.gradeScoreMap[grade] = split[0].toDouble()
                                        } else if (split.size == 3) {
                                            mtx.gradeScoreMap[grade] = split[0].toDouble() * 60 + split[1].toDouble()
                                        }
                                    }
                                } else {
                                    mtx.gradeScoreMap[grade] = v.numberValue.toDouble()
                                }
                            }
                            CellDataTypeEnum.NUMBER -> {
                                mtx.gradeScoreMap[grade] = v.numberValue.toDouble()
                            }
                            else -> {
                                // 不能处理空白的项目，这个年级不能有数据
                                return
                            }
                        }
                        // 如果是加分项
                        if (isPlus) {
                            if (mtx.cmptScore == null) {
                                return
                            }
                            if (mtx.cmptScore!! <= basePlus) {
                                mtx.cmptScore = mtx.cmptScore!! + basePlus
                            }
                            mtx.levelName = "优秀"
                            mtx.gradeScoreMap[grade] = mtx.gradeScoreMap[grade]!! + plusMap[grade]!!
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
        } else if (rowIndex == 1) {
            // 说明是确定加分项的
            for ((col, colData) in data) {
                if (colData == null) {
                    continue
                }
                if (col == 0) {
                    basePlus = colData.toDouble()
                } else {
                    val gradeIntStr: String = headMap[col] ?: continue
                    val gradeInt = try {
                        gradeIntStr.toInt()
                    } catch (e: Exception) {
                        continue
                    }
                    if (colData.contains("""['"]""".toRegex())) {
                        // 说明可能是个时分秒
                        val split = colData.split("""['"]""".toRegex())
                        if (split.isNotEmpty()) {
                            if (split.size == 1) {
                                plusMap[gradeInt] = split[0].toDouble()
                            } else {
                                plusMap[gradeInt] = split[0].toDouble() * 60 + split[1].toDouble()
                            }
                        }
                    } else {
                        plusMap[gradeInt] = colData.toDouble()
                    }
                }
            }
        }
    }

    private fun dealData() {
        // insert 顺序需要固定
        var sql =
            """ insert into t_subject_metrics(actual_grade, sex, subject_id, score_range, cmpt_score, int_level, level_name, is_plus,
                              proportion) values """
        val lastScoreMap = mutableMapOf<Int, Double>()
        val resLi = mutableListOf<String>()
        for ((i, data) in li.withIndex()) {
            for ((gd, score) in data.gradeScoreMap) {
                val mex = MetricsData()
                mex.actualGrade = gd
                mex.sex = "'${sex}'"
                mex.subjectId = subject
                var range: String
                if (i == 0) {
                    val dt = SbjScore(subject, sex, gd)
                    val ahaha = BespImport.lastPlusValue[dt]
                    range = if (ahaha != null) {
                        if (biggerBest) {
                            "[${score},$ahaha)"
                        } else {
                            "($ahaha,${score}]"
                        }
                    } else {
                        if (biggerBest) {
                            "[${score},+∞)"
                        } else {
                            "(-∞,${score}]"
                        }
                    }
                } else {
                    val lastScore = lastScoreMap[gd]
                    range = if (biggerBest) {
                        "[${score},${lastScore})"
                    } else {
                        "(${lastScore},${score}]"
                    }
                }
                lastScoreMap[gd] = score
                mex.scoreRange = "'${range}'"
                mex.cmptScore = data.cmptScore
                mex.intLevel = when (data.levelName) {
                    "优秀" -> {
                        1
                    }
                    "良好" -> {
                        2
                    }
                    "及格" -> {
                        3
                    }
                    "不及格" -> {
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
                if (!isPlus && i == li.size - 1) {
                    val newRes = mutableListOf<Any>().apply {
                        addAll(res)
                    }
                    if (biggerBest) {
                        newRes[3] = "'(-∞,${score})'"
                    } else {
                        newRes[3] = "'(${score},+∞)'"
                    }
                    newRes[4] = 0
                    resLi.add(newRes.joinToString(",", "(", ")") { it.toString() })
                }
                if (isPlus && i == li.size - 1) {
                    val dt = SbjScore(subject, sex, gd)
                    val ahaha = BespImport.lastPlusValue[dt]
                    BespImport.lastPlusValue[dt] = score
                }
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
        // Pair("体重指数", 0.15),
        Pair("肺活量", 0.15),
        Pair("50米", 0.20),
        Pair("坐位体前屈", 0.30),
        Pair("跳绳", 0.20),
    )
    private val m34 = mutableMapOf(
        // Pair("体重指数", 0.15),
        Pair("肺活量", 0.15),
        Pair("50米", 0.20),
        Pair("坐位体前屈", 0.20),
        Pair("跳绳", 0.20),
        Pair("仰卧起坐", 0.10),
    )
    private val m56 = mutableMapOf(
        // Pair("体重指数", 0.15),
        Pair("肺活量", 0.15),
        Pair("50米", 0.20),
        Pair("坐位体前屈", 0.10),
        Pair("跳绳", 0.10),
        Pair("仰卧起坐", 0.20),
        Pair("折返跑", 0.10),
    )
    private val mOther = mutableMapOf(
        // Pair("体重指数", 0.15),
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

class MetricsData {
    var actualGrade: Int? = null
    var sex: String? = null
    var subjectId: Int? = null
    var scoreRange: String? = null
    var cmptScore: Double? = null
    var intLevel: Int? = null
    var levelName: String? = null
    var isPlus: Int? = null
    var proportion: Double? = null
}

