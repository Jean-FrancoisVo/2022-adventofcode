fun main() {
    val lines = readInput("day08")
    day8(lines)
}

fun day8(lines: List<String>) {
    val matrix = lines.map { line -> line.map { it.digitToInt() } }
    val part1 =
        (1 until matrix.size - 1)
            .map { row -> (1 until matrix[0].size - 1).zip(Array(matrix[0].size - 1) { row }) }
            .flatten()
            .count { isVisible(matrix, it) } + matrix.size * 2 + matrix[0].size * 2 - 4
    val part2 =
        (1 until matrix.size - 1)
            .map { row -> (1 until matrix[0].size - 1).zip(Array(matrix[0].size - 1) { row }) }
            .flatten().maxOfOrNull { isVisible2(matrix, it) }
    println(part1)
    println(part1 == 1827)
    println(part2)
    println(part2 == 335580)
}

private fun isVisible(matrix: List<List<Int>>, position: Pair<Int, Int>): Boolean {
    val (row, column) = position
    val rows = matrix.rowsAt(row)
    val columns = matrix.columnsAt(column)
    return rows.isVisibleWithin((0 until column), column) // LEFT
            || rows.isVisibleWithin((column + 1 until columns.size), column) // RIGHT
            || columns.isVisibleWithin((0 until row), row) // TOP
            || columns.isVisibleWithin((row + 1 until rows.size), row) // BOTTOM
}

private fun isVisible2(matrix: List<List<Int>>, position: Pair<Int, Int>): Int {
    val (row, column) = position
    val rows = matrix.rowsAt(row)
    val columns = matrix.columnsAt(column)
    return rows.toto((column - 1 downTo  0), column) *
            rows.toto((column + 1 until columns.size), column) *
            columns.toto((row - 1 downTo  0), row) *
            columns.toto((row + 1 until rows.size), row)
}

private fun List<Int>.isVisibleWithin(range: IntProgression, position: Int) =
    this.filterIndexed { index, i -> index in range && i >= this[position] }
        .isEmpty()

private fun List<Int>.toto(range: IntProgression, position: Int): Int {
    val biggerTree = range.map { this[it] }.indexOfFirst { it >= this[position] } + 1
    return if (biggerTree == 0) {
        range.count()
    } else {
        range.map{ this[it] }.take(biggerTree).count()
    }
}

private fun <E> List<List<E>>.rowsAt(x: Int): List<E> = this[x]
private fun <E> List<List<E>>.columnsAt(y: Int): List<E> = this.transpose2d()[y]
