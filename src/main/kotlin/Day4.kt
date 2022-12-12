val lines = readInput("day4")

fun day4(check: (Collection<Any>, Collection<Any>, Collection<Any>) -> Boolean): Int = lines.count { line ->
    val (firstTask, secondTask) = line.split(",", "-", limit = 4)
        .map { it.toInt() }
        .chunked(2)
        .map { (a, b) -> a..b}
    val intersection = firstTask intersect secondTask
    check(intersection, (firstTask).toSet(), (secondTask).toSet())
}

fun main() {
    val containsAll: (Collection<Any>, Collection<Any>, Collection<Any>) -> Boolean =
        { a, b, c -> a.isNotEmpty() && a.containsAll(b) || a.containsAll(c) }
    println(day4(containsAll)) // 582
    val notEmpty: (Collection<Any>, Collection<Any>, Collection<Any>) -> Boolean =
        { a, _, _ -> a.isNotEmpty() }
    println(day4(notEmpty)) // 893
}
