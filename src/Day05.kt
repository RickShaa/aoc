import java.util.Stack
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

    val stacks = cratesAndInstructions[0].split("\n")
        .map {
            it
            .replace("    ", "[#]")
            .replace(" ", "")
            .replace("[", "")
            .replace("]", "")
        }.map { it -> it.patchLastValue()}.take(8)

    fun MutableMap<Int,MutableList<Char>>.reverseListEntries(){
        for(entry in this.entries.iterator())
            this[entry.key] = entry.value.reversed() as MutableList<Char>
    }

    fun fillStacksWithCrates(stacks:List<List<Char>>):MutableMap<Int,MutableList<Char>>{
        var filledStack:MutableMap<Int,MutableList<Char>> = mutableMapOf()
        stacks.forEach { crates -> crates.forEachIndexed { index, c ->
            val idx = index+1;
            if(c.toString() != "#"){
                if(filledStack.contains(idx)){
                    filledStack[idx]?.add(c)
                }else{
                    filledStack[idx] = mutableListOf(c)
                }
            }
        }}
        filledStack.reverseListEntries()
        return filledStack
    }


    fun createInstructions(text:List<String>, regex: Regex): List<Instruction> {
        return text
            .map{ instruction -> regex.findAll(instruction).map { it.groupValues[1] }.joinToString()}
            .map { it.split(",").map{ it.trim().toInt()} }
            .map { item -> Instruction(item[0], item[1], item[2]) }
    }

    val instText = cratesAndInstructions[1].split("\n")
    val instRegex = "([1-9])".toRegex()
    val instructions = createInstructions(instText,instRegex)
    val stacksAndCrates = fillStacksWithCrates(stacks)



    println(stacksAndCrates)
    fun moveFromOriginToDestination(instruction: Instruction) {
        val originStack = stacksAndCrates[instruction.originStack]
        val destinationStack = stacksAndCrates[instruction.destinationStack]
        if(originStack?.size != 0){
            val removedElement = originStack?.removeAt(originStack.size -1)
            if (removedElement != null) {
                destinationStack?.add(removedElement)
            }
        }

    }

    instructions.map { instruction -> repeat(instruction.numberOfCrates){moveFromOriginToDestination(instruction)} }


    //println(stacks)
    println(stacksAndCrates)

}


data class Instruction(val numberOfCrates:Int,val originStack:Int, val destinationStack:Int)
