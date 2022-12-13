import java.util.*

fun main() {
    val fileName = "day07.txt"
    val testFileName = "day07_test.txt"
    val input = FileUtil.getListOfLines(fileName);
    val rootDirectory = Directory("/", null)
    var currentDirectory = rootDirectory

    fun String.toChar(): Char {
        return this.toCharArray()[0]
    }
    fun getActionType(line:String): ActionType {
        return when(line.first()){
            "$".toChar() -> ActionType.COMMAND
            "d".toChar() -> ActionType.DIRECTORY
            else -> {
                ActionType.FILE
            }
        }
    }
    fun changeDir(id:String): Directory {
        return currentDirectory.directories.find { it.id == id }!!
    }

    fun executeCommand(prompt:String){
        val splitPrompt = prompt.split(" ")
        if(splitPrompt.size == 3){
            val moveTo = splitPrompt[2]
            if(moveTo != "/" && moveTo != ".."){
                currentDirectory = changeDir(moveTo)
            }else if(moveTo == ".."){
                currentDirectory = currentDirectory.parentDir!!
            }
        }
    }


    fun storeDirectory(prompt:String){
        val (_, dirId) = prompt.split(" ")
        currentDirectory.directories.add(Directory(dirId, currentDirectory))
    }
    fun storeFile(prompt:String){
        val (size, fileId) = prompt.split(" ")
        currentDirectory.files.add(File(fileId,size.toInt()))
    }

    fun String.performAction(){
        when(getActionType(this)){
            ActionType.COMMAND -> executeCommand(this)
            ActionType.FILE -> storeFile(this)
            ActionType.DIRECTORY -> storeDirectory(this)
        }
    }


    input.forEach { it.performAction()}

    var sizes = mutableListOf<Int>()

    fun getSizes(node:Directory) {
        val dirSize = node.getDirectorySize()
        if(dirSize <= 100000){
            sizes.add(dirSize)
            sizes.add(node.directories.sumOf { it.getDirectorySize() })
        }else{
            println("${node.id} directory is in too large")
            if(node.directories.size > 0){
                node.directories.forEach { getSizes(it) }
            }
        }
    }

    getSizes(rootDirectory)
    println(sizes.sum())
}

enum class ActionType {
    FILE,
    DIRECTORY,
    COMMAND
}


class Directory(val id:String,
                val parentDir:Directory?,
                val files:MutableList<File> = mutableListOf(),
                val directories:MutableList<Directory> = mutableListOf())
{
    fun getDirectorySize(): Int {
        return files.sumOf { it.size } + getChildDirectorySize()
    }
    private fun getChildDirectorySize():Int {
        if(directories.size > 0){
            return directories.sumOf { directory: Directory -> directory.getDirectorySize() }
        }
        return 0
    }
}

class File(val id:String, val size:Int)