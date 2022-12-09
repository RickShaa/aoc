import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.sign

fun main() {
    val fileName = "day06.txt"
    val testFileName = "day06_test.txt"
    val input = FileUtil.getTrimmedText(fileName);

    fun isStarterPacket(packet:List<Char>):Boolean{
        return packet.distinct().size == packet.size
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
    }

    val dataStream = input.toCharArray().toList();
    println(dataStream.findDistinctSequence(14))

}
