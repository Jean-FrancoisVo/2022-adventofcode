fun main() {
    val lines = readInput("day08")
    day8(lines)
}

fun day8(lines: List<String>) {
    val matrix = lines.map { line -> line.map { it.digitToInt() } }
    println((1 until matrix.size - 1).map { row -> (1 until matrix[0].size - 1).zip(Array(matrix[0].size - 1) { row }) }
        .flatten()
        .count { isVisible(matrix, it) } + matrix.size * 2 + matrix[0].size * 2 - 4) // 1827
}

private fun isVisible(matrix: List<List<Int>>, position: Pair<Int, Int>): Boolean {
    val (row, column) = position
    val rows = matrix.rowsAt(row)
    val columns = matrix.columnsAt(column)
    val a = rows.filterIndexed { index, i -> index in (0 until column) && i >= rows[column] }
    val b = rows.filterIndexed { index, i -> index in (column + 1..columns.size) && i >= rows[column] }
    val c = columns.filterIndexed { index, i -> index in (0 until row) && i >= columns[row] }
    val d =
        columns.filterIndexed { index, i -> index in (row + 1..rows.size) && i >= columns[row] }
    return a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()
}

private fun <E> List<List<E>>.columnsAt(y: Int): List<E> = this.transpose2d()[y]
private fun <E> List<List<E>>.rowsAt(x: Int): List<E> = this[x]
