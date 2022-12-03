fun main() {


    val fileName = "day03.txt"
    val testFileName = "day03_test.txt"
    val input:List<String> = FileUtil.getListOfLines(fileName);

    //Source for generic concat function: https://www.techiedelight.com/concatenate-multiple-lists-kotlin/
    fun <T> concat(vararg lists:List<T>):List<T>{
        return listOf(*lists).flatten()
    }

    //Helper function for generating a list of chars
    fun createLetters(start:Int, end:Int):List<Char>{
        var letters = mutableListOf<Char>();
        for(i in start..end){
            letters.add(i.toChar())
        }
        return letters
    }
    //Helper function for mapping chars to priority numbers
    fun createPriorityMap(letters:List<Char>):Map<Char, Int>{
        var map = mutableMapOf<Char, Int>()
        letters.forEachIndexed{index, element -> map[element] = index+1 }
        return map
    }

    //Generate alphabet from a...zA...Z
    val lowerCaseLetters = createLetters(97,122)
    val upperCaseLetters = createLetters(65,90)
    val letters = concat(lowerCaseLetters,upperCaseLetters)
    //prioritize each letter
    val priorityMap = createPriorityMap(letters)


    //PART 1
    fun String.splitInHalf():List<String>{
        val midIndex = this.length /2;
        return listOf(this.substring(0, midIndex), this.substring(midIndex))
    }

    //Find common char in a tuple
    fun List<String>.commonChars():List<Char>{
        val first = this[0].toCharArray()
        val second = this[1].toCharArray();
        return first.map{element -> second.find { it == element }}.filterNotNull()
    }

    //sum priorities up
    val sum = input.map{ it.splitInHalf() }.map { rucksack -> priorityMap[rucksack.commonChars().first()]!! }.sumOf { s -> s }


    //PART 2
    //group by three
    val groups = input.chunked(3)
    // find common char in Triple - utilizing commonChars() method from part 1
    fun List<String>.commonCharTriple():Char{
        val firstTuple = this.toMutableList().take(2)
        val third = this[2]
        val commonChars = firstTuple.commonChars()
        return commonChars.firstNotNullOf { element -> third.find { it == element } }
    }
    //get sum
    val badgeSum = groups.map { rucksacks -> priorityMap[rucksacks.commonCharTriple()]!!}.sumOf { s -> s }
    println(badgeSum)



}

