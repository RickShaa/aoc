import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.sign

fun main() {
    val fileName = "day06.txt"
    val testFileName = "day06_test.txt"
    val input = FileUtil.getTrimmedText(testFileName);

    /**
     * Initial solution
     */
    /*fun isStarterPacket(packet:List<Char>):Boolean{
        //transform to set, because set does not allow duplicates
        return packet.toSet().size == packet.size
    }

    fun List<Char>.findDistinctSequence(size:Byte): Int? {
        var bufferEnd:Int? = null
        for (i in this.indices){
            bufferEnd = i + size
            if(isStarterPacket(this.subList(i, bufferEnd ))){
                break
            }
        }
        return bufferEnd
    }*/

    

    /**
     * Optimized Solution 1
     */
    val dataStream = input.toCharArray().toList();


    /*fun distinctSequence(seqLen:Int): Int? {
        val windowedData =  dataStream.windowed(seqLen)
        var idx:Int? = null
        for ((offset,data) in windowedData.withIndex()) {
            if (data.toHashSet().size == data.size) {
                idx = offset + seqLen
                break;
            }
        }
        return idx;
    }*/



    /*
        Optimized Solution 2

     */
   fun distinctSequence(seqLen:Int): Any {
        val windowedData = dataStream.windowed(seqLen)
        val index = windowedData.indexOfFirst{window -> window.toHashSet().size == window.size}
        return index + seqLen
   }

    println(distinctSequence(4))
}
