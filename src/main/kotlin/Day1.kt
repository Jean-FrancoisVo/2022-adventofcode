import java.io.File

fun day1() {
    val lines = File("src/main/resources/day1.txt").readLines()
    val grouped = lines.fold(mutableListOf<MutableList<String>>(mutableListOf())) { acc, s ->
        if (s.isBlank()) {
            acc.add(mutableListOf())
            acc
        } else {
            acc.last().add(s)
            acc
        }
    }
    println("Max top 1 : ${grouped.maxOfOrNull { list -> list.sumOf { it.toInt() } }}")
    println("Sum top 3 : ${grouped.map { list -> list.sumOf { it.toInt() } }.sortedDescending().take(3).sum()}")

}
