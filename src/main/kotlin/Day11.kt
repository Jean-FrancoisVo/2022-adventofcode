fun main() {
    val lines = readInput("day11")
    println(day11Part1(lines))
}

fun day11Part1(lines: List<String>): Int {
    val monkeys = lines.chunked(7)
        .map { config ->
            val items = """(\d+)""".toRegex().findAll(config[1])
                .map { it.groupValues }.map { it.first().toInt() }
                .toMutableList()
            val operand = config[2].split(" ").last()
            val operator: (Int, Int) -> Int = if (config[2].contains("*")) Int::times else Int::plus
            val operation = { v: Int -> operator(v, if (operand == "old") v else operand.toInt()) }
            Monkey(items, operation, Triple(config[3].lastInt(), config[4].lastInt(), config[5].lastInt()))
        }
    repeat(20) {
        monkeys.forEach { monkey ->
            monkey.inspect().forEach { monkeys[it.first].items.add(it.second) }
        }
    }
    return monkeys.map { it.inspected }.sortedDescending().take(2).fold(1) { acc, i -> acc * i }
}

class Monkey(
    val items: MutableList<Int>,
    val operation: (v: Int) -> Int,
    private val test: Triple<Int, Int, Int>
) {
    var inspected = 0

    fun inspect(): List<Pair<Int, Int>> {
        val result = items.map { operation(it) }
            .map {
                val worry = (it / 3)
                if (worry % test.first == 0) {
                    test.second to worry
                } else {
                    test.third to worry
                }
            }
        inspected += result.size
        items.empty()
        return result
    }
    override fun toString(): String {
        return "Monkey(items=$items,inspected=$inspected)"
    }
}

private fun <E> MutableList<E>.empty() {
    repeat(this.size) { this.removeFirst() }
}

private fun String.lastInt(): Int = this.split(" ").last().toInt()
