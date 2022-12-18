fun main() {
    val lines = readInput("day10")
    println(day10Part1(lines))
    println(day10Part1(lines) == 14320)
    println(day10Part2(lines).joinToString(" \n"))
}

fun day10Part1(lines: List<String>): Int {
    val states = cpuStates(lines)
    println(states)
    return 20 * states.find { it.first == 20 }!!.second +
            60 * states.find { it.first == 60 }!!.second +
            100 * states.find { it.first == 100 }!!.second +
            140 * states.find { it.first == 140 }!!.second +
            180 * states.find { it.first == 180 }!!.second +
            220 * states.find { it.first == 220 }!!.second
}

fun day10Part2(lines: List<String>): MutableList<String> {
    val crtLines = MutableList(8) { "" }
    var crtIndex = 0
    cpuStates(lines).forEach { state ->
        val (cycle, x) = state
        crtLines[crtIndex] += if (crtLines[crtIndex].length % 40 in x - 1..x + 1) {
            "#"
        } else {
            "."
        }
        if (cycle % 40 == 0) {
            crtIndex++
        }
    }
    return crtLines
}

private fun cpuStates(lines: List<String>): List<Pair<Int, Int>> {
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
    return instructions.dropLast(1)
}
