import java.io.File

fun divideIntoCompartment(wholeRucksack: String): Pair<String, String> =
    Pair(
        wholeRucksack.substring(0, wholeRucksack.length / 2),
        wholeRucksack.substring(wholeRucksack.length / 2, wholeRucksack.length)
    )

fun findCommonItem(compartment: Pair<String, String>): Char? =
    compartment.first.find { compartment.second.contains(it) }

fun day3Part1() {
    val lines = File("src/main/resources/day3.txt").readLines()
    val result = lines.map { divideIntoCompartment(it) }
        .map { findCommonItem(it) }
        .sumOf { priorities(it) }
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

fun findCommonItem(group: Triple<String, String, String>): Char? =
    group.first.find { group.second.contains(it) && group.third.contains(it) }


fun day3Part2() {
    val lines = File("src/main/resources/day3.txt").readLines()
    println(divideIntoGroups(lines)
        .map { findCommonItem(it) }
        .sumOf { priorities(it) })
}
