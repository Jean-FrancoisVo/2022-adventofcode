fun main() {
    val lines = readInput("day10")
    println(day10Part1(lines))
    println(day10Part1(lines) == 14320)
}

fun day10Part1(lines: List<String>): Int {
    val instructions = lines.fold(mutableListOf(1 to 1)) { acc, line ->
        when (line.take(4)) {
            "addx" -> {
                acc.add(acc.last().first + 1 to acc.last().second)
                acc.add(acc.last().first + 1 to acc.last().second + line.substringAfter(" ").toInt())
                acc
            }

            "noop" -> {
                acc.add(acc.last().first + 1 to acc.last().second)
                acc
            }

            else -> error("")
        }
    }
    return 20 * instructions.find { it.first == 20 }!!.second +
            60 * instructions.find { it.first == 60 }!!.second +
            100 * instructions.find { it.first == 100 }!!.second +
            140 * instructions.find { it.first == 140 }!!.second +
            180 * instructions.find { it.first == 180 }!!.second +
            220 * instructions.find { it.first == 220 }!!.second
}
