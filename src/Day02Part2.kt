fun main() {


    val fileName = "day02.txt"
    val testFileName = "day02_test.txt"
    val input:String  = FileUtil.getTrimmedText(fileName);
    val testInput:String = FileUtil.getTrimmedText(testFileName);

    val WON = 6;
    val DRAW =3;

    //Create Lookup map to store
    val choiceMap = mapOf<String, Choice>(
        "A" to Choice.ROCK,
        "B" to Choice.PAPER,
        "C" to Choice.SCISSORS,
    )

    val outcomeMap = mapOf<String,Outcome>(
        "Z" to Outcome.WIN,
        "X" to Outcome.LOSS,
        "Y" to Outcome.DRAW
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

    fun getOutcome(key:String): Outcome?{
        return outcomeMap[key]
    }

    fun calculateDrawOutCome(opponentChoice:Choice): Int {
        //just return same choice as opponent
        return getPointsByChoice(opponentChoice)!!
    }

    fun calculateWinOutCome(opponentChoice: Choice): Int? {
        return if(opponentChoice == Choice.ROCK){
            getPointsByChoice(Choice.PAPER)
        }else if(opponentChoice == Choice.PAPER){
            getPointsByChoice(Choice.SCISSORS)
        }else{
            getPointsByChoice(Choice.ROCK)
        }
    }

    fun calculateLossOutCome(opponentChoice: Choice):Int?{
        if(opponentChoice == Choice.ROCK){
            return getPointsByChoice(Choice.SCISSORS)
        }else if(opponentChoice == Choice.PAPER){
            return getPointsByChoice(Choice.ROCK)
        }
        return getPointsByChoice(Choice.PAPER)
    }

    var finalScore = 0;

    val matches = input.split("\n");
    matches.forEach{
        match->
        val picks = match.split(" ")
        //Translate A,B,X to ROCK, PAPER, SCISSORS and translate outcome
        val preferredOutcome = getOutcome(picks[1])!!
        val oppChoice = getChoice(picks[0])!!
        //draw case
        if(preferredOutcome == Outcome.DRAW){
            finalScore+= DRAW + calculateDrawOutCome(oppChoice)
        }else if(preferredOutcome == Outcome.WIN){
            finalScore+= WON + calculateWinOutCome(oppChoice)!!
        }else{
            finalScore+= calculateLossOutCome(oppChoice)!!
        }

    }

    println(finalScore)

}

enum class Outcome {
    WIN,
    DRAW,
    LOSS

}




