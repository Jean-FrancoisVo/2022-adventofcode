import java.io.File

fun Char.toPriority(): Int = if (this in 'a'..'z') this.code - 'a'.code - 1 else this.code - 'A'.code - 1

fun day3Part1() {
    val lines = readInput("day3")
    val result = lines
        .sumOf { rucksack ->
            val (first, second) = rucksack.chunked(rucksack.length / 2)
            first.find { it in second }?.toPriority() ?: 0
        }
    println(result)
}

fun priorities(it: Char?) = when {
    it == null -> 0
    it.isLowerCase() -> it.code - 96
    else -> it.code - 38
}

fun divideIntoGroups(wholeList: List<String>): List<Triple<String, String, String>> =
    wholeList.chunked(3)
        .map { Triple(it[0], it[1], it[2]) }

fun findCommonItem(group: Triple<String, String, String>): Char? {
    val (first, second, third) = group
    return first.find { second.contains(it) && third.contains(it) }
}


fun day3Part2() {
    val lines = readInput("day3")
    println(divideIntoGroups(lines)
        .map { findCommonItem(it) }
        .sumOf { priorities(it) })
}

fun main() {
    day3Part1()
    day3Part2()
}
