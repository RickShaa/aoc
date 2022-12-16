import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.sign

fun main() {
    val fileName = "day10.txt"
    val testFileName = "day10_test.txt"

    fun String.getCPUInstruction():List<String>{
        return this.split(" ")
    }
    fun calculateSignalStrength(cycle:Int, relevantCycles:MutableList<Int>, registerValue:Int):Int{
        if(relevantCycles.size >= 1 && relevantCycles[0] == cycle){
            val cycleNumber = relevantCycles.removeAt(0)
            return registerValue * cycleNumber
        }
        return 0
    }

    // PART 2

    fun getPixel(registerX:Int, pixelIdx:Int):String{
        if(pixelIdx == registerX -2 || pixelIdx == registerX - 1 || pixelIdx == registerX ){
            return "#"
        }
        return "."
    }

    fun drawPixel(pixels:MutableList<String>,registerX:Int){
        if(pixels.size<40){
            pixels.add(getPixel(registerX,pixels.size - 1))
        }else{
            pixels.add(getPixel(registerX,0))
        }
    }

    fun drawRow(pixels:MutableList<String>){
        if(pixels.size == 40){
            println(pixels.joinToString(""))
            pixels.removeAll(pixels)
        }
    }

    fun draw(pixels:MutableList<String>, x:Int){
        drawPixel(pixels,x) //during cycle
        drawRow(pixels)
    }

    val input = FileUtil.getListOfLines(fileName);
    var x = 1;
    val relevantCycle = mutableListOf(20,60,100,140,180,220)
    var currentCycle = 0
    var signalStrength = 0
    var pixels = mutableListOf<String>()

    for(i in input.indices){
        val cpuInstruction = input[i].getCPUInstruction()
        if(cpuInstruction.size == 1){
            currentCycle++ //start cycle
            draw(pixels,x)
            signalStrength+=calculateSignalStrength(currentCycle, relevantCycle, x)
        }else{
            for(i in 0..1){
                //DRAWS PIXEL
                currentCycle++ //start cycle
                draw(pixels,x)
                signalStrength+=calculateSignalStrength(currentCycle, relevantCycle, x)
            }
            x+= cpuInstruction[1].toInt()
        }
    }
    println(signalStrength)
}