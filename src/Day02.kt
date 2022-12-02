fun main() {


    val fileName = "day02.txt"
    val testFileName = "day02_test.txt"
    val input:String  = FileUtil.getTrimmedText(fileName);
    val testInput:String = FileUtil.getTrimmedText(testFileName);

    val WON = 6;
    val DRAW =3;
    val LOSS = 0;

    //Create Lookup map to store
    val choiceMap = mapOf<String, Choice>(
        "A" to Choice.ROCK,
        "B" to Choice.PAPER,
        "C" to Choice.SCISSORS,
        "Y" to Choice.PAPER,
        "X" to Choice.ROCK,
        "Z" to Choice.SCISSORS
    )

    val pointsMap = mapOf<Choice, Int>(
        Choice.ROCK to 1,
        Choice.PAPER to 2,
        Choice.SCISSORS to 3
    )

    fun getPointsByChoice(choice: Choice):Int?{
        return pointsMap[choice]
    }

    fun getChoice(key:String): Choice? {
        return choiceMap[key]
    }

    var finalScore = 0;

    val matches = input.split("\n");
    matches.forEach{
        match->
        val picks = match.split(" ")
        //Translate A,B,X to ROCK, PAPER, SCISSORS
        val choice = getChoice(picks[1])!!
        val oppChoice = getChoice(picks[0])!!
        //draw case
        if(
            choice == Choice.ROCK && oppChoice == Choice.ROCK ||
            choice == Choice.PAPER && oppChoice == Choice.PAPER ||
            choice == Choice.SCISSORS && oppChoice == Choice.SCISSORS){
            //win case
            finalScore+= DRAW + getPointsByChoice(choice)!!
        }else if(
            choice == Choice.ROCK && oppChoice == Choice.SCISSORS ||
            choice == Choice.PAPER && oppChoice == Choice.ROCK ||
            choice == Choice.SCISSORS && oppChoice == Choice.PAPER
        ){
            finalScore+= WON + getPointsByChoice(choice)!!
        }else{
            finalScore+= getPointsByChoice(choice)!!
        }

    }

    println(finalScore)

}

enum class Choice{
    ROCK,
    PAPER,
    SCISSORS
}



