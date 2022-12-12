fun day5(lines: List<String>) {
    val stacks = lines.subList(0, lines.indexOf(""))
        .map { it.chunked(4) { s -> s.replace(Regex("""[\[\]\W]"""), "") } }
        .dropLast(1)
        .transpose2d()
        .map { stack -> ArrayDeque(stack.filter { it.isNotBlank() }.reversed()) }

    val moves = lines.subList(lines.indexOf("") + 1, lines.size)
        .map {
            Regex("""move (\d+) from (\d+) to (\d+)""").matchEntire(it)!!
                .groupValues
                .drop(1)
                .map { v -> v.toInt() }
        }

    moves.forEach { move ->
        val (quantity, from, to) = move
        repeat((0 until quantity).count()) {
            stacks[to - 1].push(stacks[from - 1].pop())
        }
    }
    println(stacks.joinToString("") { it.last() })
}

fun <E> ArrayDeque<E>.pop(): E = this.removeLast()

fun <E> ArrayDeque<E>.push(element: E) = this.addLast(element)

fun <E> List<List<E>>.transpose2d(): List<List<E>> {
    val cols = this[0].size
    val rows = this.size
    return List(cols) { col ->
        List(rows) { row ->
            this[row][col]
        }
    }
}


fun main() {
    val lines = readInput("day5")
    day5(lines)
}
