sealed class Node

object Directory : Node()
data class File(val size: Int) : Node()

const val TOTAL = 70_000_000
const val NEEDED = 30_000_000

fun day7() {
    val lines = readInput("day07")
    var currentPath = ""
    val tree = mutableMapOf<String, Node>("/" to Directory)
    lines.forEach { line ->
        when {
            line.startsWith("$") -> currentPath = newPath(line, currentPath)
            line.startsWith("dir") -> tree.putIfAbsent("${currentPath}${line.arg()}", Directory)
            else -> tree.putIfAbsent("${currentPath}${line.arg()}", File(line.sizeOf()))
        }
    }
    println(tree.filterValues { it is Directory }.keys.map { tree.sizeOf(it) }.filter { it < 100_000 }.sum())
    println(tree.filterValues { it is Directory }.keys.map { tree.sizeOf(it) }.filter { it < 100_000 }.sum() == 1644735)
    val occupied = tree.sizeOf("/")
    val free = TOTAL - occupied
    val toFree = NEEDED - free
    println(tree.directoriesPath().map { tree.sizeOf(it) }.filter { it >= toFree }.min())
    println(tree.directoriesPath().map { tree.sizeOf(it) }.filter { it >= toFree }.min() == 1300850)
}

private fun <K, V> MutableMap<K, V>.directoriesPath(): Set<K> = this.filterValues { it is Directory }.keys

private fun newPath(line: String, currentPath: String): String {
    val command = line.removePrefix("$ ")
    return when {
        command.take(2) == "cd" -> {
            return when {
                command.arg() == ".." -> currentPath.dropLast(1).dropLastWhile { it != '/' }
                else -> currentPath + "${if (command.arg() == "/") "" else command.arg()}/"
            }
        }

        else -> currentPath
    }
}

private fun String.sizeOf(): Int = this.split(" ")[0].toInt()
private fun String.arg(): String = this.split(" ")[1]

private fun <K, V> MutableMap<K, V>.sizeOf(k: String): Int {
    return this.filterKeys { (it as String).startsWith(k, true) }
        .filterValues { it is File }
        .values
        .sumOf { (it as File).size }
}

fun main() {
    day7()
}
