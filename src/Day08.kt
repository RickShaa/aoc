import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.sign

fun main() {
    val fileName = "day08.txt"
    val testFileName = "day08_test.txt"
    val input = FileUtil.getListOfLines(fileName);
    val trees = input.map { it -> it.toCharArray().map { it.toString().toInt() } }


    fun List<List<Int>>.countEdges():Int{
        val sideA = this.size
        val sideB = this[0].size - 2
        return sideA * 2 + sideB * 2
    }

    fun currentTree(posY:Int, posX:Int):Int{
        return trees[posY][posX]
    }

    fun treeIsVisibleFromAbove(posY:Int, posX:Int):Boolean{
        val tree = currentTree(posY,posX)
        //always go from edge to current tree position if there is one tree that is bigger it is not visible anymore
        for(y in 0 until  posY){
            if(trees[y][posX] >= tree){
                return false
            }
        }
        return true;
    }

    fun getViewingDistanceAbove(posY:Int, posX:Int):Int{
        val tree = currentTree(posY,posX)
        var distance = 0;
        if(posY - 1 > 0){
            for(y in (posY -1) downTo   0){
                if(trees[y][posX] < tree){
                    distance++
                }else{
                    distance++
                    return distance
                }
            }
        }
        return distance;
    }

    fun treeIsVisibleFromBelow(posY:Int, posX:Int):Boolean{
        val tree = currentTree(posY,posX)
        for(y in (trees.size -1) downTo    (posY + 1) ){
            if(trees[y][posX] >= tree){
                return false
            }
        }
        return true;
    }
    fun getViewingDistanceFromBelow(posY:Int, posX:Int):Int{
        val tree = currentTree(posY,posX)
        var distance = 0;
        if(posY + 1 < trees.size){
            for(y in (posY + 1) until    trees.size){
                if(trees[y][posX] < tree){
                    distance++
                }else{
                    distance++
                    return distance
                }
            }
        }
        return distance;
    }

    fun isTreeVisibleInColumn(posY:Int, posX:Int):Boolean{
        return treeIsVisibleFromAbove(posY,posX) || treeIsVisibleFromBelow(posY,posX)
    }

    fun isTreeVisibleToLeft(posY:Int, posX:Int):Boolean{
        val tree = currentTree(posY,posX)
        val rowOfTrees = trees[posY]
        for(x in 0 until   posX){
            if(rowOfTrees[x] >= tree){
                return false
            }
        }
        return true;
    }

    fun getViewingDistanceLeft(posY:Int, posX:Int):Int{
        val tree = currentTree(posY,posX)
        var distance = 0;
        if((posX-1) > 0){
            for(x in (posX-1) downTo   0){
                if(trees[posY][x] < tree){
                    distance++
                }else{
                    distance++
                    return distance
                }
            }
        }
        return distance;
    }

    fun isTreeVisibleToRight(posY:Int, posX:Int):Boolean{
        val tree = currentTree(posY,posX)
        val rowOfTrees = trees[posY]
        for(x in (rowOfTrees.size -1) downTo    (posX + 1)){
            if(rowOfTrees[x] >= tree){
                return false
            }
        }
        return true;
    }

    fun getViewingDistanceRight(posY:Int, posX:Int):Int{
        val tree = currentTree(posY,posX)
        val rowOfTrees = trees[posY]
        var distance = 0;
        if(posX +1 < rowOfTrees.size){
            for(x in (posX +1) until    rowOfTrees.size){
                if(trees[posY][x] < tree){
                    distance++
                }else{
                    distance++
                    return distance
                }
            }
        }
        return distance;
    }


    fun isTreeVisibleInRow(posY:Int, posX:Int):Boolean{
        // scans left and right
        return isTreeVisibleToLeft(posY, posX) || isTreeVisibleToRight(posY,posX)
    }

    fun getNumberOfVisibleTrees(trees:List<List<Int>>):Int{
        val edgeTrees = trees.countEdges()
        var visibleTrees = edgeTrees;
        //skip first and last row because it is already visible
        for(y in 1 until trees.size - 1){
            for (x in 1 until trees[y].size -1){
                if(isTreeVisibleInColumn(y,x) || isTreeVisibleInRow(y,x)){
                    visibleTrees++
                }
            }
        }
        return visibleTrees
    }
    val scenicScores = mutableListOf<Int>()
    fun getScenicScores(trees:List<List<Int>>){
        for(y in trees.indices){
            for (x in 0 until trees[y].size){
                var score = getViewingDistanceAbove(y,x) *
                        getViewingDistanceFromBelow(y,x) *
                        getViewingDistanceLeft(y,x) *
                        getViewingDistanceRight(y,x)
                scenicScores.add(score)
            }
        }
    }
    getScenicScores(trees)
    val bestScenicScore = scenicScores.max()

    println(scenicScores)
    println(bestScenicScore)

}
