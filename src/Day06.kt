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
    fun isStarterPacket(packet:List<Char>):Boolean{
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
    }

    

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
    /*fun distinctSequence(seqLen:Int): Any {
        val windowedData = dataStream.windowed(seqLen)
        val index = windowedData.indexOfFirst{window -> window.toHashSet().size == window.size}
        return index + seqLen
   }*/


    /**
     * Optimized Solution 3 BITWISE
     * Source: https://www.youtube.com/watch?v=U16RnpV48KQ&t=132s
     *
     * binary literal in kotlin 0b0101 "0b" meaning binary representation, 0101 = 5 (dez)
     *
     * Explanation:
     * 'a' = 97
     * 'b' = 98 ....
     *
     *
     * 'a' % 32 will result in a number between 0 - 31.
     * This is equal to the amount of bits in an UInt
     * after using the remainder operator the shl(shift left) operator
     * shifts a single BIT(1 = 2^0) in to the u32 store, basically setting a switch to ON
     * (we have the char 'a' stored as one bit within u32 int at index 'a' % 32)
     *
     * Now whenever we encounter two numbers as 32bits,
     * Where one equals our char store with all previous chars stored as an ON switch at a specific index 'a' % 32
     * and we bitwise OR those numbers, two scenarios can occur
     * BITWISE OR:
     * 1 | 0 = 1
     * 1 | 1 = 1
     * 0 | 1 = 1
     * 0 | 0 = 0
     * 1. Our newly calculated index represented as a single 1 (or ON switch) within an u32 int
     * is bitwise OR with our state, we will receive the exact same state,
     * because all ON state in our store and the OFF states of our bitwise shifted char will revert to ON (1)
     * and our match, ON (1) in the store and ON (1) for bitwise shifter char will return 1
     *
     * 2. if the char is not set in our store as on, the resulting "new state" will have one more bit set to ON
     * and therefore will not be equal.
     *
     * Example with Input 'a','a','a','b','c'
     * char.code.toByte() transforms input to -> [97, 97 ,97, 98, 99]
     *
     * First window is [97, 97, 97]
     * state = 0
     *
     * 1. Iterate over bytes
     * -> 97 in binary =  1100001
     * prev = state = 0 = 00000001
     *
     *      1.1 Modulo
     *          97 % 32 = 1 = m
     *      1.2 shl (why start with 1, because 0 in binary is 00000001 ???)
     *          1 shl m = 00000010
     *      1.3 bitwise OR and reassign state
     *          00000001
     *          00000010
     *          --------
     *          00000011 = reassign state
     *      1.4 compare prev and state
     *          prev = 00000001 != 00000011
     *          continue loop
     *
     * 2. iteration
     *
     * prev = state = 00000011 (base2) = 3(base10)
     *      1.1 Modulo
     *          97 % 32 = 1 = m
     *      1.2 shl
     *          1 shl m = 00000010
     *      1.3 bitwise or with state and reassign
     *          00000011
     *          00000010
     *          --------
     *          00000011 state reassigned
     *      1.4 compare state with prev state
     *          00000011 = 00000011
     *
     */

    fun List<Byte>.bitwise():Boolean{
        /**
         * UInt = 32 bit unsigned (0 - 2^32 -1) Integers
         *source: https://kotlinlang.org/docs/unsigned-integer-types.html#unsigned-arrays-and-ranges
         */
        var state:UInt = 0u
            for(byte in this){
                val prev = state
                state = state.or((1 shl (byte % 32)).toUInt())
                if(prev == state){
                    return false
                }
            }
            return true
    }

    fun distinctSequence(seqLen:Int): Any {
        val windowedData = dataStream.map { it.code.toByte() }.also { println(it) }.windowed(seqLen)
        return windowedData.indexOfFirst { it.bitwise() } + seqLen
    }
    println(distinctSequence(4))

}
