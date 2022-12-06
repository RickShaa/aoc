import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.sign

fun main() {
    val fileName = "day05.txt"
    val testFileName = "day05_test.txt"
    val input = FileUtil.getText(fileName);
    val cratesAndInstructions = input.split("\n\n")
    fun String.patchLastValue(): List<Char> {
        var crates = this
        if(this.length == 8){
            crates+="#"
        }
        return crates.toList()
    }

    val lines = cratesAndInstructions[0].split("\n")
        .map {
            it
            .replace("    ", "#")
            .replace(" ", "")
            .replace("[", "")
            .replace("]", "")
        }.map { it -> it.patchLastValue()}.take(8)

    lines.forEach { println(it) }


    fun createStacks(lines:List<List<Char>>):MutableMap<Int,ArrayDeque<Char>> {
        var filledStack:MutableMap<Int,ArrayDeque<Char>> = mutableMapOf()
        lines.forEach { line -> line.forEachIndexed { index, c ->
            val idx = index+1;
            if(c.toString() != "#"){
                if(filledStack.contains(idx)){
                    filledStack[idx]?.addFirst(c)
                }else{
                    filledStack[idx] = ArrayDeque(listOf(c))
                }
            }
        }}

        return filledStack
    }


    fun createInstructions(text:List<String>, regex: Regex): List<Instruction> {
        return text
            .map{ instruction -> regex.findAll(instruction).map { it.groupValues[0] }.joinToString()}
            .map { it.split(",").map{ it.trim().toInt()} }
            .map { item -> Instruction(item[0], item[1], item[2]) }
    }

    val instText = cratesAndInstructions[1].split("\n")
    val instRegex = "\\d{1,2}".toRegex()
    val instructions = createInstructions(instText,instRegex)
    val stacks = createStacks(lines)

    fun getTopCrates(sortedStacks: MutableMap<Int, ArrayDeque<Char>>): String {
        var result = ""
        for(i in 1..9){
            result+= sortedStacks.get(i)?.removeLast()
        }
        return result
    }


    fun moveFromOriginToDestination(instruction: Instruction) {
        val originStack = stacks[instruction.originStack]
        val destinationStack = stacks[instruction.destinationStack]
        val removedElement = originStack?.removeLast()
        if (removedElement != null) {
            destinationStack?.addLast(removedElement)
        }
    }
    instructions.map { instruction -> instruction.moveOrdered(stacks) }

    println(getTopCrates(stacks))
}


data class Instruction(val numberOfCrates:Int,val originStack:Int, val destinationStack:Int){
    fun moveOrdered(stacks: MutableMap<Int, ArrayDeque<Char>>){
        val originStack = stacks[originStack]
        val destinationStack = stacks[destinationStack]
        val tempList = mutableListOf<Char>()
        for (i in  1..numberOfCrates){
           tempList.add(originStack?.removeLast()!!)
        }
        tempList.reversed().forEach { it-> destinationStack?.addLast(it) }
    }
}
