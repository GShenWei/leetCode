package com.me

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.junit.Test
import java.io.File

class LoadToSql {
    val varLength = 255

    @Test
    fun xx() {
        val fileName = "/Users/weichen/docs/智脑/1112/101采集治理_数据编目_编目表_人员信息数据-教研成果.json"
        println(getSql(fileName))
    }

    @Test
    fun yy() {
        val dirName = "/Users/weichen/docs/智脑/1112/"
        val dir = File(dirName)
        for (file in dir.listFiles()) {
            println(getSql(file))
        }
    }

    private fun getSql(file: File): String {
        val str = file.readText(Charsets.UTF_8)
        val jsonObj = JSON.parseObject(str)
        val obj = jsonObj.getJSONObject("config")
        var tableName = obj["id"].toString()
        tableName = tableName.replace("_orgin", "origin")
        val desc = obj["desc"].toString()
        val fds = obj.getJSONArray("fields")
        var pk = "un"
        val pkList = mutableListOf<String>()

        val sqlList = mutableListOf<String>()
        sqlList.add("   id int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键'")
        for (fd in fds) {
            fd as JSONObject
            val name = fd["code"].toString()
            var type = fd["type"].toString()

            //类型转换列表
            type = when (type) {
                "int32" -> "int"
                "int64" -> "bigint"
                else -> "varchar($varLength)"
            }
            val cDesc = fd["name"].toString()
            if (fd.getBoolean("is_pk")) {
                pkList.add(name)
                pk += "_$name"
            }
            val s = "   $name $type COMMENT '$cDesc'"
            sqlList.add(s)
        }
        sqlList.add("   PRIMARY KEY (`id`)")
        if (pkList.size > 0) {
            val p = pkList.joinToString(",")
            sqlList.add("   UNIQUE INDEX $pk($p) USING BTREE")
        }
        val propSql = sqlList.joinToString(",\n")
        val sql = """drop table if exists $tableName;
            |CREATE TABLE $tableName  (
            |$propSql
            |) COMMENT = '$desc';""".trimMargin()
        return sql
    }

    private fun getSql(fileName: String): String {
        val file = File(fileName)
        return getSql(file)
    }
}