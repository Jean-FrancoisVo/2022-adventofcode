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
        val lines = File("src/main/resources/day2.txt").readLines()
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
