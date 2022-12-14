import com.beust.klaxon.Klaxon


fun main() {

    val fileName = "day13.txt"
    val testFileName = "day13_test.txt"
    val input = FileUtil.getListOfLines(fileName);

    fun Any.isInt():Boolean{
        return this is Int
    }
    fun Any.isList():Boolean{
        return this is List<*>
    }

    fun minTupleEndIdx(leftTuple:List<Any>,rightTuple:List<Any>): Int {
        if(leftTuple.size >= rightTuple.size){
            return rightTuple.size-1
        }
        return leftTuple.size -1
    }

    fun handleIntComparison(leftList:List<Any>, rightList:List<Any>, x:Int, maxIndex:Int):Boolean{
        val rightValue = rightList[x] as Int
        val leftValue = leftList[x] as Int
        val leftSideRanOutOfData = x == maxIndex && leftList.size < rightList.size
        val rightSideRanOutOfData = x == maxIndex && leftList.size > rightList.size
        if(leftValue  < rightValue){
            println("CORRECT Compared $leftValue with right: $rightValue")
            return true
        }else if(leftValue > rightValue){
            println("Order not correct lerft: $leftValue  right: $rightValue")
            return false
        }else if(leftSideRanOutOfData){
            return true
        }else if (rightSideRanOutOfData){
            return false
        }
        return false
    }

    fun compareList(tuple: List<List<Any>>):Boolean{
        val leftList = tuple[0]
        val rightList = tuple[1]
        var isInOrder = false;
        if(leftList.isEmpty() && rightList.isNotEmpty()){
            return true
        }else if(leftList.isNotEmpty() && rightList.isEmpty()){
            return false
        }

        val minIndex = minTupleEndIdx(leftList,rightList)
        for(x in 0..minIndex){
            if(!isInOrder){
                if(leftList[x].isInt() && rightList[x].isInt()){
                    isInOrder = handleIntComparison(leftList, rightList, x, minIndex)
                    //exit for loop as soon as no longer in right order
                }else if(leftList[x].isList() && rightList[x].isList()){
                    isInOrder = compareList(listOf(leftList[x] as List<Any>, rightList[x] as List<Any>))
                }else{
                    isInOrder = if(leftList[x].isInt()){
                        val listifyInt = listOf(leftList[x] as Int)
                        val list = rightList[x] as List<Any>
                        compareList(listOf(listifyInt, list))
                    }else{
                        val listifyInt = listOf(rightList[x] as Int)
                        val list = leftList[x] as List<Any>
                        compareList(listOf(list, listifyInt))
                    }
                }
            }else{
                break
            }
        }
        return isInOrder
    }

    val chunkedInput = input.mapNotNull { it.takeIf { s: String -> s.isNotEmpty() } }.chunked(2)
    val parsedInput = chunkedInput
        .map { pairs -> pairs
            .map { packet -> Klaxon().parseArray<Any>(packet)!! }}


    fun sumOfIndicies(): Int {
        var sum = 0
        val orderedPackets = parsedInput.map { compareList(it)}
        println(orderedPackets)
        for(x in orderedPackets.indices){
            if(orderedPackets[x]){
                sum+=x+1
            }
        }
        return sum
    }

    println(sumOfIndicies())
}