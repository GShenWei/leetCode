package exam

import com.alibaba.excel.EasyExcel
import com.alibaba.excel.ExcelReader
import com.alibaba.excel.read.metadata.ReadSheet
import org.junit.Test
import java.io.File


class BespImport {
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

    private val bigBestMap = mutableMapOf(
        Pair("肺活量", true),
        Pair("50米", false),
        Pair("坐位体前屈", true),
        Pair("立定跳远", true),
        Pair("仰卧起坐", true),
        Pair("折返跑", false),
        Pair("跳绳", true),
        Pair("引体", true),
        Pair("1000米跑", false),
        Pair("800米跑", false),
    )

    companion object {
        val lastPlusValue = mutableMapOf<Int, MutableMap<Int, Double>>()
    }

    @Test
    fun xx1() {
        // 读取部分sheet
        //val fx = "指标"
        val fx = "有加分的指标"
        xx(fx)
    }

    @Test
    fun yy1() {
        // 读取部分sheet
        //val fx = "指标"
        val fx = "bmi"
        yy(fx)
    }

    @Test
    fun xx2() {
        val fx1 = "指标"
        val fx2 = "有加分的指标"
        xx(fx1)
        xx(fx2)
        val fx3 = "bmi"
        yy(fx3)
    }

    fun xx(fx: String) {
        val fileName = "/Users/weichen/docs/企鹅成绩/体育国测指标/$fx.xlsx"
        //限定sheet页的结尾，以为有些时候只要导入某一些sheet页
        val thisBatch = ""
        var excelReader: ExcelReader? = null
        try {
            excelReader = EasyExcel.read(fileName).build()

            val sheetList = excelReader.excelExecutor().sheetList()
            val allSheets = mutableListOf<ReadSheet>()
            for ((i, v) in sheetList.withIndex()) {
                if (!v.sheetName.endsWith(thisBatch)) {
                    continue
                }
                val ss = v.sheetName.split("-")
                val sbjName = ss[0]
                val sbjId = sbjMap[sbjName]
                if (sbjId == null) {
                    println(sbjName)
                    throw Exception("错错错错")
                }
                val bigBest = bigBestMap[sbjName]
                if (bigBest == null) {
                    println(sbjName)
                    throw Exception("错错错错")
                }
                val sex = if (ss[ss.size - 1] == "男") {
                    "M"
                } else {
                    "F"
                }
                val isPlus = if (ss.size == 3) {
                    ss[1] == "加分"
                } else {
                    false
                }
                val headLine = if (isPlus) 2 else 1
                allSheets.add(
                    EasyExcel
                        .readSheet(i)
                        .headRowNumber(headLine)
                        .registerReadListener(
                            ScoreMetricsListener(
                                "/Users/weichen/docs/企鹅成绩/体育国测指标/out$fx",
                                "outfile-$fx.txt",
                                sex,
                                sbjId,
                                bigBest,
                                isPlus,
                            )
                        )
                        .build()
                )
            }
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(allSheets)
        } finally {
            excelReader?.finish()
        }
    }

    fun yy(fx: String) {
        val fileName = "/Users/weichen/docs/企鹅成绩/体育国测指标/$fx.xlsx"
        //限定sheet页的结尾，以为有些时候只要导入某一些sheet页
        val thisBatch = ""
        var excelReader: ExcelReader? = null
        try {
            excelReader = EasyExcel.read(fileName).build()

            val sheetList = excelReader.excelExecutor().sheetList()
            val allSheets = mutableListOf<ReadSheet>()
            for ((i, v) in sheetList.withIndex()) {
                if (!v.sheetName.endsWith(thisBatch)) {
                    continue
                }
                val ss = v.sheetName.split("-")
                val sbjName = ss[0]
                val sbjId = sbjMap[sbjName]
                if (sbjId == null) {
                    println(sbjName)
                    throw Exception("错错错错")
                }
                val sex = if (ss[ss.size - 1] == "男") {
                    "M"
                } else {
                    "F"
                }
                val isPlus = if (ss.size == 3) {
                    ss[1] == "加分"
                } else {
                    false
                }
                val headLine = if (isPlus) 2 else 1
                allSheets.add(
                    EasyExcel
                        .readSheet(i)
                        .headRowNumber(headLine)
                        .registerReadListener(
                            ScoreMetricsBMIListener(
                                "/Users/weichen/docs/企鹅成绩/体育国测指标/out$fx",
                                "outfile-$fx.txt",
                                sex,
                                sbjId,
                                true,
                                isPlus,
                            )
                        )
                        .build()
                )
            }
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(allSheets)
        } finally {
            excelReader?.finish()
        }
    }

    @Test
    fun deleteDate() {
        val currentDir = "/Users/weichen/docs/智脑/out"
        val dir = File(currentDir)
        if (dir.exists()) {
            dir.deleteRecursively()
        }
        dir.mkdirs()
    }

    @Test
    fun yyx() {
        val r = """^\d{4}/\d{1,2}/\d{1,2} \d{1,2}:\d{1,2}:\d{1,2}$""".toRegex()
        var s = "2020/02/01 2:1:1"
        if (r.matches(s)) {
            s = s.replace("/", "-")
        }
        println(s)
    }
}