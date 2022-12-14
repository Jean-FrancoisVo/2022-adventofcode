fun <E> List<List<E>>.transpose2d(): List<List<E>> {
    val cols = this[0].size
    val rows = this.size
    return List(cols) { col ->
        List(rows) { row ->
            this[row][col]
        }
    }
}
