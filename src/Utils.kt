import java.io.File
import java.math.BigInteger
import java.security.MessageDigest


object FileUtil {

    private val pathName = "aoc/resources";
    fun getTrimmedText(fileName:String): String{
        return File(pathName, fileName).readText().trim();
    }

    fun getListOfLines(fileName: String):List<String>{
        return File(pathName, fileName).readLines();
    }
}