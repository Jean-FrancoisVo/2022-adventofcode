fun day5(
    lines: List<String>,
    mover: (stacks: List<ArrayDeque<String>>, to: Int, from: Int, quantity: Int) -> Unit
) {
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
        val (quantity, from, to) = move.mapIndexed { index, i -> if (index != 0) i - 1 else i }
        mover(stacks, to, from, quantity)
    }
    println(stacks.joinToString("") { it.last() })
}

fun <E> ArrayDeque<E>.pop(): E = this.removeLast()

fun <E> ArrayDeque<E>.push(element: E) = this.addLast(element)


fun main() {
    val lines = readInput("day5")
    day5(lines) { stacks, to, from, quantity ->
        repeat((0 until quantity).count()) {
            stacks[to].push(stacks[from].pop())
        }
    } // TDCHVHJTG
    day5(lines) { stacks, to, from, quantity ->
        stacks[to].addAll(stacks[to].size, stacks[from].takeLast(quantity))
        repeat(quantity) { stacks[from].pop() }
    } // NGCMPJLHV
}
