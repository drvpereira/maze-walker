package tech.davidpereira.maze

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {

    fun dist(to: Point): Double {
        return sqrt( (x - to.x).toDouble().pow(2) + (y - to.y).toDouble().pow(2) )
    }

    fun manhattan(to: Point): Int {
        return abs(x - to.x) + abs(y - to.y)
    }

    fun toVector(): Vector {
        return Vector(x.toDouble(), y.toDouble())
    }

    operator fun plus(v: Vector): Point {
        return Point((x + v.x).toInt(), (y - v.y).toInt())
    }

}