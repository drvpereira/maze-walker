package tech.davidpereira.maze

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.round

class Angle private constructor(private var radians: Double) {

    fun getRadians(): Double = radians

    fun getDegrees(): Int = round(radians * 180.0 / PI + 360).toInt() % 360

    fun moveTowards(to: Angle, amount: Angle): Angle {
        if ((getDegrees() == 270 && to.getDegrees() == 0)) {
            to.radians = 2 * PI
        } else if ((getDegrees() == 0 && to.getDegrees() == 270)) {
            radians = 2 * PI
        } else if (getDegrees() == 180 && to.getDegrees() == 270) {
            to.radians = 3 * PI / 2
        } else if (getDegrees() == 270 && to.getDegrees() == 180) {
            radians = 3 * PI / 2
        }

        return Angle(radians + if (to.radians < radians) -amount.radians else amount.radians)
    }

    fun normalize() {
        if (abs(radians - 2 * PI) < 0.01) {
            radians = 0.0
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Angle

        return getDegrees() == other.getDegrees()
    }

    override fun hashCode(): Int {
        return getDegrees().hashCode()
    }

    companion object {

        fun withRadians(radians: Double): Angle {
            return Angle(radians)
        }

        fun withDegrees(degrees: Int): Angle {
            return Angle(degrees * PI / 180.0)
        }

    }

}