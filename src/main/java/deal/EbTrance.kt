package deal

import org.junit.Test

class EbTrance {
    @Test
    fun xx() {
        var str = """
type AnswerInfoKnowledgeAnalysisRsp struct {
	Suggest       string          `json:"Suggest"`
	KnowledgeList []KnowledgeList `json:"KnowledgeList"`
}
type KnowledgeList struct {
	Name  string  `json:"Name"`
	Score float64 `json:"Score"`
}
"""
        str = str.replace("""type (\w+)List""".toRegex(),"""type $1Item""")
        str = str.replace("""\[](\w+)List""".toRegex(),"""[]$1Item""")
        str = str.replace("""type (\w+) .*\{""".toRegex(), """message $1 {""")
        str = str.replace("""`.*`""".toRegex(), "")
        str = str.replace("""\t(\w+)\s+(.*?) """.toRegex(), """    $2 $1 = 1;""")
        str = str.replace("""int """.toRegex(), "int32 ")
        str = str.replace("""time\.Time """.toRegex(), "string ")
        str = str.replace("""\[]""".toRegex(), "repeated ")
        str = str.replace("""\*""".toRegex(), "")
        str = str.replace("float64".toRegex(), "double")

        str = str.replace("Req_".toRegex(), "Rsp_")
        str = str.replace("ID".toRegex(), "Id")
        println(str)
    }

    @Test
    fun gg(){
        println(1.2-1.1)
    }
}