import java.io.File

val lines = File("src/main/resources/day4.txt").readLines()

fun day4Part1(): Long {
    return lines.sumOf { line ->
        val (firstTask, secondTask) = line.split(",")
            .map { it.split("-").map { limit -> limit.toInt() } }
            .map {
                val (begin, end) = it
                begin..end
            }
        val intersection = firstTask intersect secondTask
        if (intersection.isNotEmpty()
            && intersection.containsAll(firstTask.toSet())
            || intersection.containsAll(secondTask.toSet())
        ) 1L else 0L
    }

}

fun main() {
    println(day4Part1())
//    582
}
