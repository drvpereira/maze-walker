package tech.davidpereira.maze

import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {

    fun dist(point: Point): Double {
        return sqrt( (x - point.x).toDouble().pow(2) + (y - point.y).toDouble().pow(2) )
    }

}