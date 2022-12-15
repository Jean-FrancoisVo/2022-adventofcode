fun main() {
    val lines = readInput("day08")
    day8(lines)
}

fun day8(lines: List<String>) {
    val layout = lines.map { line -> line.map { it.digitToInt() } }
    val innerLayoutRowSize = layout.size - 1
    val innerLayoutColSize = layout[0].size - 1
    val innerTreesCoordinates = (1 until innerLayoutRowSize)
        .map { row -> (1 until innerLayoutColSize).zip(Array(innerLayoutColSize) { row }) }
        .flatten()
    val outerLayoutCount = layout.size * 2 + layout[0].size * 2 - 4
    val part1 = innerTreesCoordinates
            .count { isVisible(layout, it, List<Int>::part1).fold(false) { acc, v -> acc || v.isEmpty() } } + outerLayoutCount
    val part2 = innerTreesCoordinates
            .maxOfOrNull { isVisible(layout, it, List<Int>::part2).fold(1) { acc, v -> acc * v.count() } }
    println(part1)
    println(part1 == 1827)
    println(part2)
    println(part2 == 335580)
}

private fun isVisible(
    matrix: List<List<Int>>,
    position: Pair<Int, Int>,
    fn: List<Int>.(IntProgression, Int) -> List<Int>,
): List<List<Int>> {
    val (row, column) = position
    val rows = matrix.rowsAt(row)
    val columns = matrix.columnsAt(column)
    return listOf(
        rows.fn((column - 1 downTo 0), column), rows.fn((column + 1 until columns.size), column),
        columns.fn((row - 1 downTo 0), row), columns.fn((row + 1 until rows.size), row)
    )
}

private fun List<Int>.part1(range: IntProgression, position: Int): List<Int> =
    range.map { this[it] }.filter { it >= this[position] }

private fun List<Int>.part2(range: IntProgression, position: Int): List<Int> {
    val biggerTree = range.map { this[it] }.indexOfFirst { it >= this[position] } + 1
    return if (biggerTree == 0) {
        range.toList()
    } else {
        range.map { this[it] }.take(biggerTree)
    }
}

private fun <E> List<List<E>>.rowsAt(x: Int): List<E> = this[x]
private fun <E> List<List<E>>.columnsAt(y: Int): List<E> = this.transpose2d()[y]
