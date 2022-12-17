import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

private val head = Point(-2, -2)

class PointTest {
    @ParameterizedTest
    @MethodSource("directions")
    fun `tail follow in all direction`(tail: Point, expected: Point) {
        tail.follow(head)

        assertThat(tail).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("lShapes")
    fun `tail follow in all L shape direction`(tail: Point, expected: Point) {
        tail.follow(head)

        assertThat(tail).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("diagonales")
    fun `tail follow head in all diagonale`(tail: Point, expected: Point) {
        tail.follow(head)

        assertThat(tail).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun directions(): List<Arguments> = listOf(
            Arguments.of(Point(0, -2), Point(-1, -2)),
            Arguments.of(Point(-2, 0), Point(-2, -1)),
            Arguments.of(Point(-4, -2), Point(-3, -2)),
            Arguments.of(Point(-2, -4), Point(-2, -3)),
        )

        @JvmStatic
        fun lShapes(): List<Arguments> = listOf(
            Arguments.of(Point(-1, 0), Point(-2, -1)),
            Arguments.of(Point(0, -1), Point(-1, -2)),
            Arguments.of(Point(0, -3), Point(-1, -2)),
            Arguments.of(Point(-1, -4), Point(-2, -3)),
            Arguments.of(Point(-3, -4), Point(-2, -3)),
            Arguments.of(Point(-4, -3), Point(-3, -2)),
            Arguments.of(Point(-4, -1), Point(-3, -2)),
            Arguments.of(Point(-3, 0), Point(-2, -1)),
        )

        @JvmStatic
        fun diagonales(): List<Arguments> = listOf(
            Arguments.of(Point(0, 0), Point(-1, -1)),
            Arguments.of(Point(0, -4), Point(-1, -3)),
            Arguments.of(Point(-4, -4), Point(-3, -3)),
            Arguments.of(Point(-4, 0), Point(-3, -1)),
        )
    }
}
