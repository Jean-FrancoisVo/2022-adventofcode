import kotlin.math.abs

fun main() {
    val lines = readInput("day09")
    println(day9(lines))
    println(day9(lines) == 6030)
}

fun day9(lines: List<String>): Int {
    val head = Point(0, 0)
    val tail = Point(0, 0)
    lines.forEach { line ->
        val times = line.split(" ")[1].toInt()
        when {
            line.startsWith("R") -> repeat(times) {
                head.move(Direction.RIGHT)
                tail.follow(head, Direction.RIGHT)
            }

            line.startsWith("U") -> repeat(times) {
                head.move(Direction.UP)
                tail.follow(head, Direction.UP)
            }

            line.startsWith("D") -> repeat(times) {
                head.move(Direction.DOWN)
                tail.follow(head, Direction.DOWN)
            }

            line.startsWith("L") -> repeat(times) {
                head.move(Direction.LEFT)
                tail.follow(head, Direction.LEFT)
            }
        }
    }
    return tail.visited.count()
}

class Point(var x: Int, var y: Int) {
    var visited = mutableSetOf(x to y)

    fun move(direction: Direction) {
        when (direction) {
            Direction.LEFT -> x--
            Direction.RIGHT -> x++
            Direction.UP -> y++
            Direction.DOWN -> y--
        }
    }

    fun follow(point: Point, direction: Direction) {
        if (manhattanDistance(point) == 2 && !onDiagonale(point)) {
            moveBehind(point, direction)
            visited.add(this.x to this.y)
        } else if (manhattanDistance(point) == 3) {
            moveBehind(point, direction)
            visited.add(this.x to this.y)
        }
    }

    private fun moveBehind(point: Point, direction: Direction) {
        when (direction.opposite()) {
            Direction.LEFT -> {
                x = point.x - 1
                y = point.y
            }
            Direction.RIGHT -> {
                x = point.x + 1
                y = point.y
            }
            Direction.UP -> {
                x = point.x
                y = point.y + 1
            }
            Direction.DOWN -> {
                x = point.x
                y = point.y - 1
            }
        }
    }

    private fun onDiagonale(point: Point): Boolean {
        return (this.x + 1 == point.x && this.y + 1 == point.y)
                || (this.x + 1 == point.x && this.y - 1 == point.y)
                || (this.x - 1 == point.x && this.y + 1 == point.y)
                || (this.x - 1 == point.x && this.y - 1 == point.y)
    }

    private fun manhattanDistance(point: Point): Int {
        return abs(this.x - point.x) + abs(this.y - point.y)
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

    fun opposite(): Direction {
        return when (this) {
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP -> DOWN
            DOWN -> UP
        }
    }
}
