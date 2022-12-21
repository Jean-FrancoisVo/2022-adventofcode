fun main() {
    val lines = readInput("day11")
    println(day11Part1(lines) == 119715L)
    println(day11Part2(lines) == 18085004878L)
}

fun day11Part1(lines: List<String>): Long {
    val monkeys = lines.chunked(7)
        .map { config ->
            val (items, operation) = itemsAndOperation(config)
            Monkey(items, operation, config[3].lastLong(), config[4].lastInt(), config[5].lastInt()) { a -> a / 3 }
        }
    repeat(20) {
        monkeys.forEach { monkey ->
            monkey.inspect().forEach { monkeys[it.first].items.add(it.second) }
        }
    }
    return monkeys.map { it.inspected }.sortedDescending().take(2).fold(1) { acc, i -> acc * i }
}


fun day11Part2(lines: List<String>): Long {
    val leastCommonMultiple = lines.chunked(7).map { it[3].lastInt().toLong() }.fold(1L) { acc, i -> acc * i }
    val monkeys = lines.chunked(7)
        .map { config ->
            val (items, operation) = itemsAndOperation(config)
            Monkey(items, operation, config[3].lastLong(), config[4].lastInt(), config[5].lastInt()) { a -> a % leastCommonMultiple}
        }
    repeat(10_000) {
        monkeys.forEach { monkey ->
            monkey.inspect().forEach { monkeys[it.first].items.add(it.second) }
        }
    }
    return monkeys.map { it.inspected }.sortedDescending().take(2).fold(1L) { acc, i -> acc * i }
}

private fun itemsAndOperation(config: List<String>): Pair<MutableList<Long>, (Long) -> Long> {
    val items = """(\d+)""".toRegex().findAll(config[1])
        .map { it.groupValues }.map { it.first().toLong() }
        .toMutableList()
    val operand = config[2].split(" ").last()
    val operator: (Long, Long) -> Long = if (config[2].contains("*")) Long::times else Long::plus
    val operation = { v: Long -> operator(v, if (operand == "old") v else operand.toLong()) }
    return Pair(items, operation)
}

typealias DivideBy = Long
typealias TrueMonkey = Int
typealias FalseMonkey = Int
typealias MonkeyIndex = Int

class Monkey(
    val items: MutableList<Long>,
    val operation: (v: Long) -> Long,
    private val test: DivideBy,
    private val trueMonkey: TrueMonkey,
    private val falseMonkey: FalseMonkey,
    private val loweringWorryLevel: (Long) -> Long
) {
    var inspected = 0

    fun inspect(): List<Pair<MonkeyIndex, Long>> {
        val result = items
            .map { loweringWorryLevel(operation(it)) }
            .map {
                if (it divisibleBy test) {
                    trueMonkey to it
                } else {
                    falseMonkey to it
                }
            }
        inspected += result.size
        items.empty()
        return result
    }

    override fun toString(): String {
        return "Monkey(inspected=$inspected)"
    }
}

private infix fun Long.divisibleBy(value: Long): Boolean = this % value == 0L

private fun <E> MutableList<E>.empty() {
    repeat(this.size) { this.removeFirst() }
}

private fun String.lastInt(): Int = this.split(" ").last().toInt()
private fun String.lastLong(): Long = this.split(" ").last().toLong()
