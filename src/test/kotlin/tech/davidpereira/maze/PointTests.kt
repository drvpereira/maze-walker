package tech.davidpereira.maze

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointTests {

    @Test
    fun testToVector() {
        assertEquals(Vector(0.0, 0.0), Point(0, 0).toVector())
        assertEquals(Vector(1.0, 1.0), Point(1, 1).toVector())
        assertEquals(Vector(2.0, 3.0), Point(2, 3).toVector())
        assertEquals(Vector(0.0, -5.0), Point(0, -5).toVector())
    }

    @Test
    fun testPlusVelocity() {
        assertEquals(Point(0, 0), Point(0, 0) + Vector(0.0, 0.0))
        assertEquals(Point(1, 1), Point(1, 1) + Vector(0.0, 0.0))
        assertEquals(Point(0, 0), Point(-1, -1) + Vector(1.0, 1.0))
        assertEquals(Point(2, 3), Point(2, 3) + Vector(0.5, 0.5))
        assertEquals(Point(-2, -2), Point(2, 3) + Vector(-4.0, -5.0))
    }

    @Test
    fun testEuclidianDistance() {
        assertEquals(0.0, Point(0, 0).dist(Point(0, 0)))
    }

    @Test
    fun testManhattanDistance() {
        assertEquals(0.0, Point(0, 0).manhattan(Point(0, 0)))
    }

}