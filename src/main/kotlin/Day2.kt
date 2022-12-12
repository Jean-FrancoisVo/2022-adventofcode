import java.io.File

class Day2 {

    enum class Victor(val score: Int) {
        ME(6),
        OPPONENT(0),
        DRAW(3)
    }

    enum class Shape(val score: Int) {
        ROCK(1) {
            override fun winAgainst(shape: Shape) = shape == SCISSOR
        },
        PAPER(2) {
            override fun winAgainst(shape: Shape) = shape == ROCK
        },
        SCISSOR(3) {
            override fun winAgainst(shape: Shape) = shape == PAPER
        };

        companion object {
            fun mine(my: String): Shape =
                when (my) {
                    "X" -> ROCK
                    "Y" -> PAPER
                    else -> SCISSOR
                }

            fun opponent(opponent: String): Shape =
                when (opponent) {
                    "A" -> ROCK
                    "B" -> PAPER
                    else -> SCISSOR
                }
        }

        abstract fun winAgainst(shape: Shape): Boolean
    }

    fun result() {
        val lines = readInput("day2")
        val result = lines
            .map { it.split(" ") }
            .map { Shape.opponent(it[0]) to Shape.mine(it[1]) }
            .map {
                if (it.first == it.second) {
                    Victor.DRAW to it.second
                } else if (it.first.winAgainst(it.second)) {
                    Victor.OPPONENT to it.second
                } else {
                    Victor.ME to it.second
                }
            }
        println("Total : ${result.sumOf { it.first.score + it.second.score }}")

    }
}


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
        val lines = readInput("day2")
        val result = lines
            .map { it.split(" ") }
            .map { Shape.opponent(it[0]) to Outcome.of(it[1]) }
            .map {
                when (it.second) {
                    Outcome.DRAW -> it.first to Outcome.DRAW
                    Outcome.WIN -> it.first.losingShape() to Outcome.WIN
                    Outcome.LOSE -> it.first.winningShape() to Outcome.LOSE
                }
            }
        println("Total : ${result.sumOf { it.first.score + it.second.score }}")

    }

}

fun main() {
    Day2().result()
    Day2Part2().result()
}
