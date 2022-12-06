import java.io.File
import java.math.BigInteger
import java.security.MessageDigest


object FileUtil {

    private val pathName = "aoc/resources";
    fun getTrimmedText(fileName:String): String{
        return File(pathName, fileName).readText().trim();
    }

    fun getText(fileName: String):String{
        return File(pathName, fileName).readText()
    }

    fun getListOfLines(fileName: String):List<String>{
        return File(pathName, fileName).readLines();
    }
}

object ListUtil {
    //Source for generic concat function: https://www.techiedelight.com/concatenate-multiple-lists-kotlin/
    fun <T> concat(vararg lists:List<T>):List<T>{
        return listOf(*lists).flatten()
    }
}

object AlphabetUtil {
    //Helper function for generating a list of chars
    fun createLetters(start:Int, end:Int):List<Char>{
        var letters = mutableListOf<Char>();
        for(i in start..end){
            letters.add(i.toChar())
        }
        return letters
    }
}