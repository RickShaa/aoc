import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val fileName = "day09.txt"
    val testFileName = "day09_test.txt"
    val input = FileUtil.getListOfLines(fileName)

    fun moveHead(move: String, head: Point) {
        when (Direction.valueOf(move)) {
            Direction.L -> head.x -= 1
            Direction.R -> head.x += 1
            Direction.U -> head.y += 1
            Direction.D -> head.y -= 1
        }
    }

    /*
        CHESSBOARD DISTANCE

        dis(p1,p2) = (|xa -xb|, |ya-yb|)

        Use case:
        Finds the shortest distance while only considering
        horizontal and vertical movements

        If distance > 1 tail can follow head safely

        Source theory:
        https://towardsdatascience.com/3-distances-that-every-data-scientist-should-know-59d864e5030a


        Source Kotlin Advent of Code Day 9 Video
        https://www.youtube.com/watch?v=ShU9dNUa_3g&t=2153s
     */
    fun distance(head:Point, tail:Point): Int {
        val deltaX = head.x - tail.x
        val deltaY = head.y - tail.y
        return maxOf(abs(deltaX),abs(deltaY))
    }

    /*
        Direction Vector

        When subtracting two points the result is a Direction Vector

       Example:

       Head(x=2,y=4) Tail(x=1,y=2)
       Head - Tail = V
       V = <1,2>

       V represents an arrow from the origin (0,0) to (1,2),
       giving us the direction to move in

        Source Study Smarter:
        https://www.studysmarter.de/schule/mathe/geometrie/richtungsvektor/


       Source Kotlin Advent of Code Day 9 Video
       https://www.youtube.com/watch?v=ShU9dNUa_3g&t=2153s
     */

    fun moveTail(head:Point,tail:Point){
        val(dX,dY) = head.delta(tail)
        val directionX = dX.coerceIn(-1,1)
        val directionY = dY.coerceIn(-1,1)
        tail.x+=directionX
        tail.y+=directionY
    }

    fun moveTail(head:Point,tail:Point, tailPos:MutableSet<Point>){
        val(dX,dY) = head.delta(tail)
        /*
            Ensures value to be in a specific range

            Meaning: if head is two steps ahead of tail, we only want to move tail
            next to head, not make tail land on head. This would happen if we would move
            the entire length of the direction vector
        */
        val directionX = dX.coerceIn(-1,1)
        val directionY = dY.coerceIn(-1,1)

        tail.x+=directionX
        tail.y+=directionY
        tailPos.add(Point(tail.x,tail.y))
    }

    //Init head and tail position
    //val head = Point(0, 0)
    //val tail = Point(0, 0)
    val rope = Array(10){Point(0,0)}
    //Add distinct tail positions (Set)
    val tailPositions: MutableSet<Point> = mutableSetOf(rope.last())

    for(i in input.indices){
        val (direction,steps) = input[i].split(" ")
        for(step in 0 until steps.toInt()){
            moveHead(direction,rope[0])
            for(idx in 0 until 9){
                //start at comparing head index = 0 to index +1
                    val head = rope[idx]
                    val tail = rope[idx +1]
                    if(distance(head, tail) > 1){
                        if(idx + 1 == 9){
                            moveTail(head,tail,tailPositions)
                        }else{
                            moveTail(head, tail)
                        }
                    }
            }
        }
    }

    println(tailPositions.count())
}
data class Point(var x:Int, var y:Int){
    fun delta(p2:Point):Pair<Int,Int>{
        val deltaX = this.x - p2.x
        val deltaY = this.y - p2.y
        return Pair(deltaX,deltaY)
    }
}

enum class Direction {
    L,
    R,
    U,
    D;
}