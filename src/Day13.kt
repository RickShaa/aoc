import com.beust.klaxon.Klaxon


fun main() {

    val fileName = "day13.txt"
    val testFileName = "day13_test.txt"
    val input = FileUtil.getListOfLines(testFileName);

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

    fun compare(left:List<Any>, right:List<Any>):Boolean{
            //CASE BOTH LISTS
            if(left.isEmpty() && right.isNotEmpty()){
                return true
            }else if(left.isNotEmpty() && right.isEmpty()){
                return false
            }else {
                //iterate through list
                val max = minTupleEndIdx(left, right)
                for (x in 0..max) {
                    println("${left[x]}  ------ ${right[x]}")
                    if (left[x].isInt() && right[x].isInt()) {
                        val r = right[x] as Int
                        val l = left[x] as Int
                        if (r > l) {
                            return true
                        } else if (r < l) {
                            return false
                        }
                    } else if (left[x].isInt() && right[x].isList()) {
                        val l = left[x] as Int
                        val r = right[x] as List<Any>
                        return compare(listOf(l), r)
                    }else if(left[x].isList() && right[x].isList()){
                       compare(left[x] as List<Any>, right[x] as List<Any>)
                    } else {
                        val r = right[x] as Int
                        val l = left[x] as List<Any>
                        return compare(l, listOf(r))
                    }
                }
            }
        return false
    }

    val chunkedInput = input.mapNotNull { it.takeIf { s: String -> s.isNotEmpty() } }
    for(x in chunkedInput.indices step 2){
        val left = Klaxon().parseArray<Any>(chunkedInput[x])!!
        val right = Klaxon().parseArray<Any>(chunkedInput[x+1])!!
        println(compare(left,right))
    }
}