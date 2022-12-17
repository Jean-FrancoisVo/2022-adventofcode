import kotlin.math.abs

fun main() {
    val lines = readInput("day09")
    println(day9Part1(lines))
    println(day9Part1(lines) == 6030)
    println(day9Part2(lines))
    println(day9Part2(lines) == 2545)
}

fun day9Part1(lines: List<String>): Int {
    val head = Point(0, 0)
    val tail = Point(0, 0)
    lines.forEach { line ->
        val times = line.substringAfter(" ").toInt()
        val direction = when (line.substringBefore(" ")) {
            "R" -> Direction.RIGHT
            "U" -> Direction.UP
            "D" -> Direction.DOWN
            "L" -> Direction.LEFT
            else -> error("")
        }
        repeat(times) {
            head.move(direction)
            tail.follow(head)
        }
    }
    return tail.visited.count()
}

fun day9Part2(lines: List<String>): Int {
    val rope = MutableList(10) { Point(0, 0) }
    lines.forEach { line ->
        val times = line.substringAfter(" ").toInt()
        val direction = when (line.substringBefore(" ")) {
            "R" -> Direction.RIGHT
            "U" -> Direction.UP
            "D" -> Direction.DOWN
            "L" -> Direction.LEFT
            else -> error("")
        }
        repeat(times) {
            rope.head().move(direction)
            rope.drop(1).indices.forEach { i ->
                rope.tail(i + 1).follow(rope.tail(i))
            }
        }
    }
    return rope.tail(rope.size - 1).visited.count()
}

private fun <E> MutableList<E>.tail(i: Int): E = this[i]

private fun <E> MutableList<E>.head() = this[0]

data class Point(private var x: Int, private var y: Int) {
    var visited = mutableSetOf(x to y)

    fun move(direction: Direction) {
        when (direction) {
            Direction.LEFT -> x--
            Direction.RIGHT -> x++
            Direction.UP -> y++
            Direction.DOWN -> y--
        }
    }

    fun follow(point: Point) {
        if (manhattanDistance(point) == 2 && !onDiagonale(point)) {
            moveSameDirectionAs(point)
        } else if (manhattanDistance(point) > 2) {
            moveDiagonale(point)
        }
        visited.add(this.x to this.y)
    }

    private fun moveSameDirectionAs(point: Point) {
        when {
            point.x == x && point.y > y -> y++
            point.x == x && point.y < y -> y--
            point.y == y && point.x > x -> x++
            point.y == y && point.x < x -> x--
        }
    }

    private fun moveDiagonale(point: Point) {
        when {
            point.x - x > 0 && point.y - y > 0 -> {
                x++
                y++
            }

            point.x - x > 0 && point.y - y < 0 -> {
                x++
                y--
            }

            point.x - x < 0 && point.y - y > 0 -> {
                x--
                y++
            }

            point.x - x < 0 && point.y - y < 0 -> {
                x--
                y--
            }
        }
    }

    private fun onDiagonale(point: Point): Boolean {
        return (x + 1 == point.x && y + 1 == point.y)
                || (x + 1 == point.x && y - 1 == point.y)
                || (x - 1 == point.x && y + 1 == point.y)
                || (x - 1 == point.x && y - 1 == point.y)
    }

    private fun manhattanDistance(point: Point): Int {
        return abs(x - point.x) + abs(y - point.y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}

enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;
}
