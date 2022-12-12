fun main() {
    val lines = readInput("day6")
    val line = lines[0]
    day6(line, 4) // 1109
    day6(line, 14) // 3965
}

private fun day6(input: String, window: Int) {
    var i = 0
    var found = false
    while (i < input.length && !found) {
        found = !input.drop(i++).take(window).haveDuplicate()
    }
    println(i + window - 1)
}

private fun String.haveDuplicate(): Boolean {
    return this.groupingBy { it }.eachCount()
        .count { it.value > 1 } > 0
}
