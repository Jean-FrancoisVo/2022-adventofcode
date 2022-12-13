fun main() {
    val line = readInput("day6").first()
    println(day6(line, 4)) // 1109
    println(day6(line, 14)) // 3965
}

private fun day6(input: String, size: Int): Int = input.windowed(size).indexOfFirst { it.toSet().size == size } + size
