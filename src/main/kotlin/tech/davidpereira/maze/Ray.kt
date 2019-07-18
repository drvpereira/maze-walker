package tech.davidpereira.maze

import kotlin.math.round

class Ray(val pos: Point, val angle: Angle) {

    private var dir = Vector.unitaryWithAngle(angle)

    fun cast(boundary: Boundary): Point? {
        val x1 = boundary.from.x.toDouble()
        val y1 = boundary.from.y.toDouble()
        val x2 = boundary.to.x.toDouble()
        val y2 = boundary.to.y.toDouble()

        val x3 = pos.x.toDouble()
        val y3 = pos.y.toDouble()
        val x4 = pos.x + dir.x
        val y4 = pos.y - dir.y

        val den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)

        if (den == 0.0) return null

        val t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den
        val u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den

        if (t > 0.0 && t < 1.0 && u > 0.0) {
            return Point(round(x1 + t * (x2 - x1)).toInt(), round(y1 + t * (y2 - y1)).toInt())
        } else {
            return null
        }
    }

}