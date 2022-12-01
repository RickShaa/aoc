import java.io.File

fun main() {

    val fileName = "day01.txt"
    val input:List<String>  = FileUtil.getListOfLines(fileName);
    val inputRegex:String = FileUtil.getTrimmedText(fileName);
    //\n detect one line break
    println(inputRegex.split("\n\n")
        .map {
            chunk -> chunk.split("\n")
        .sumOf {
                s: String -> s.toInt() }
        }.sortedDescending()
        .take(3).sum()
    )

    val sums:MutableList<Int> = mutableListOf()
    var maxSum = 0
    var sum = 0

    input.forEach{
        if(it != ""){
            val number = it.toInt()
            sum+= number
        }else{
            sums.add(sum)
            sum = 0
        }
    }
    val sortedSet = sums.toSortedSet()
    val lastIndex = sortedSet.size -1
    val start = lastIndex -2
    for(i in lastIndex downTo  start){
        maxSum+= sortedSet.elementAt(i)
    }

}
