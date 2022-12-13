fun day1() {
    val lines = readInput("day1")
    val grouped = lines.fold(mutableListOf<MutableList<String>>(mutableListOf())) { acc, s ->
        if (s.isBlank()) {
            acc.add(mutableListOf())
            acc
        } else {
            acc.last().add(s)
            acc
        }
    }.map { list -> list.sumOf { it.toInt() } }

    println("Max top 1 : ${grouped.max()}")
    println("Sum top 3 : ${grouped.sortedDescending().take(3).sum()}")

}

fun main() {
    day1()
}
