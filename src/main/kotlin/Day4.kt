import java.io.File

val lines = File("src/main/resources/day4.txt").readLines()

fun day4(check: (Collection<Any>, Collection<Any>, Collection<Any>) -> Boolean): Long = lines.sumOf { line ->
    val (firstTask, secondTask) = line.split(",")
        .map { it.split("-").map { limit -> limit.toInt() } }
        .map {
            val (begin, end) = it
            begin..end
        }
    val intersection = firstTask intersect secondTask
    if (check(intersection, firstTask.toSet(), secondTask.toSet())) 1L else 0L
}

fun main() {
    val containsAll: (Collection<Any>, Collection<Any>, Collection<Any>) -> Boolean =
        { a, b, c -> a.isNotEmpty() && a.containsAll(b) || a.containsAll(c) }
    println(day4(containsAll)) // 582
    val notEmpty: (Collection<Any>, Collection<Any>, Collection<Any>) -> Boolean =
        { a, _, _ -> a.isNotEmpty() }
    println(day4(notEmpty))
}
