fun main() {


    val fileName = "day04.txt"
    val testFileName = "day04_test.txt"
    val input = FileUtil.getListOfLines(fileName);

    //Comparing strings does not work because comparing "1" or "23" with  "234567891011" would trigger true
    //ALternative comparing "1,2" with "21,22" would return true


    fun String.rangeToBytes():List<Byte>{
        var bytes = mutableListOf<Byte>()
        val (start, end) = this.split("-")
        for(i in start.toInt()..end.toInt()){
            bytes.add(i.toByte())
        }
        return bytes
    }
    fun List<List<Byte>>.contains():Boolean{
        val (first,second) =this
        return first.containsAll(second) || second.containsAll(first)
    }


    val digits = input.map { it -> it.split(",").map { it.rangeToBytes() }}.map { it.contains() }.count { it }
    println(digits)

}

