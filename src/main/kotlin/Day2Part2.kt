import java.io.File

class Day2Part2 {
    enum class Outcome(val score: Int) {
        WIN(6),
        LOSE(0),
        DRAW(3);

        companion object {
            fun of(outcome: String): Outcome =
                when (outcome) {
                    "X" -> LOSE
                    "Y" -> DRAW
                    else -> WIN
                }
        }
    }

    enum class Shape(val score: Int) {
        ROCK(1) {
            override fun winningShape() = SCISSOR
            override fun losingShape() = PAPER
        },
        PAPER(2) {
            override fun winningShape() = ROCK
            override fun losingShape() = SCISSOR
        },
        SCISSOR(3) {
            override fun winningShape() = PAPER
            override fun losingShape() = ROCK
        };

        companion object {
            fun opponent(opponent: String): Shape =
                when (opponent) {
                    "A" -> ROCK
                    "B" -> PAPER
                    else -> SCISSOR
                }
        }

        abstract fun winningShape(): Shape
        abstract fun losingShape(): Shape
    }

    fun result() {
        val lines = File("src/main/resources/day2.txt").readLines()
        val result = lines
            .map { it.split(" ") }
            .map { Pair(Shape.opponent(it[0]), Outcome.of(it[1])) }
            .map {
                when (it.second) {
                    Outcome.DRAW -> Pair(it.first, Outcome.DRAW)
                    Outcome.WIN -> Pair(it.first.losingShape(), Outcome.WIN)
                    Outcome.LOSE -> Pair(it.first.winningShape(), Outcome.LOSE)
                }
            }
        println("Total : ${result.sumOf { it.first.score + it.second.score }}")

    }

}
