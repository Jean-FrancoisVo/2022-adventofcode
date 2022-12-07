import java.io.File

enum class Victor(val score: Int) {
    ME(6),
    OPPONENT(0),
    DRAW(3)
}

enum class Move(val score: Int) {
    ROCK(1) {
        override fun winAgainst(move: Move) = move == SCISSOR
    },
    PAPER(2) {
        override fun winAgainst(move: Move) = move == ROCK
    },
    SCISSOR(3) {
        override fun winAgainst(move: Move) = move == PAPER
    };

    companion object {
        fun mine(my: String): Move =
            when (my) {
                "X" -> ROCK
                "Y" -> PAPER
                else -> SCISSOR
            }

        fun opponent(opponent: String): Move =
            when (opponent) {
                "A" -> ROCK
                "B" -> PAPER
                else -> SCISSOR
            }
    }

    abstract fun winAgainst(move: Move): Boolean
}

fun day2() {
    val lines = File("src/main/resources/day2.txt").readLines()
    val result = lines
        .map { it.split(" ") }
        .map { Pair(Move.opponent(it[0]), Move.mine(it[1])) }
        .map {
            if (it.first == it.second) {
                Pair(Victor.DRAW, it.second)
            } else if (it.first.winAgainst(it.second)) {
                Pair(Victor.OPPONENT, it.second)
            } else {
                Pair(Victor.ME, it.second)
            }
        }
    println("Total : ${result.sumOf { it.first.score + it.second.score }}")

}
