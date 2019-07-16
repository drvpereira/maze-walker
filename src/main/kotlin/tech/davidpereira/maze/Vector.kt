package tech.davidpereira.maze

import java.lang.Math.cos
import java.lang.Math.sin


data class Vector(val x: Double, val y: Double) {

    companion object {
        fun fromAngle(angle: Double): Vector {
            return Vector(cos(angle), sin(angle))
        }
    }

}