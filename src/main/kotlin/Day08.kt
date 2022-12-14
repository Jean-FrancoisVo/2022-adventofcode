fun main() {
    val lines = readInput("day08")
    day8(lines)
}

fun day8(lines: List<String>) {
    val matrix = lines.map { line -> line.map { it.digitToInt() } }
    val result =
        (1 until matrix.size - 1)
            .map { row -> (1 until matrix[0].size - 1).zip(Array(matrix[0].size - 1) { row }) }
            .flatten()
            .count { isVisible(matrix, it) } + matrix.size * 2 + matrix[0].size * 2 - 4
    println(result)
    println(result == 1827)
}

private fun isVisible(matrix: List<List<Int>>, position: Pair<Int, Int>): Boolean {
    val (row, column) = position
    val rows = matrix.rowsAt(row)
    val columns = matrix.columnsAt(column)
    return rows.isVisibleWithin((0 until column), column)
            || rows.isVisibleWithin((column + 1 until columns.size), column)
            || columns.isVisibleWithin((0 until row), row)
            || columns.isVisibleWithin((row + 1 until rows.size), row)
}

private fun List<Int>.isVisibleWithin(range: IntRange, position: Int) =
    this.filterIndexed { index, i -> index in range && i >= this[position] }
        .isEmpty()
private fun <E> List<List<E>>.rowsAt(x: Int): List<E> = this[x]
private fun <E> List<List<E>>.columnsAt(y: Int): List<E> = this.transpose2d()[y]
