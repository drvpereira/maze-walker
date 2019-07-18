package tech.davidpereira.maze

import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.atan2
import kotlin.math.round

data class Vector(val x: Double, val y: Double) {

    companion object {
        fun unitaryWithAngle(angle: Angle): Vector {
            return Vector(cos(angle.getRadians()), sin(angle.getRadians()))
        }
    }

    fun getAngle(): Angle {
        return Angle.withRadians(atan2(y, x))
    }

    fun scale(t: Double): Vector {
        return Vector((x * t), (y * t))
    }

    operator fun minus(toVector: Vector): Vector {
        return Vector(x - toVector.x, y - toVector.y)
    }

    fun size(): Double {
        return Point(x.toInt(), y.toInt()).dist(Point(0, 0))
    }

    fun toPoint(): Point {
        return Point(round(x).toInt(), round(y).toInt())
    }

}