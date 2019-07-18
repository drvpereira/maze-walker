package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics

data class Boundary(val from: Point, val to: Point) {

    fun show(g: Graphics) {
        g.color = Color.BLACK
        g.drawLine(from.x, from.y, to.x, to.y)
    }

}