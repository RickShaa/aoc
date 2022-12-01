import java.io.File

fun main() {

    //Config Test Data and Input
    val path = "aoc/assets"
    val fileName = "day01.txt"
    val input:List<String>  = File(path,fileName).readLines()
    val testFileName = "day01_test.txt"
    val testInput = File(path,testFileName).readLines()


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
