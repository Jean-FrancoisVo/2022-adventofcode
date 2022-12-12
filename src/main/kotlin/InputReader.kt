import java.io.File

fun readInput(day: String): List<String> = File("src/main/resources/${day}.txt").readLines()
