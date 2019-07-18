package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Scene(private val position: Point, private val scene: List<Pair<Point, Double>>) {

    fun showScene(panelWidth: Int, panelHeight: Int, g: Graphics) {
        if (scene.isNotEmpty()) {
            val sceneW = MAZE_SIZE / 5.0
            val w = ceil((panelWidth.toDouble() / scene.size.toDouble())).toInt()

            scene.reversed().forEachIndexed { i, it ->
                val value = it.second

                val b = map(value * value, 0.0, sceneW * sceneW, 255.0, 0.0).toInt()
                val h = map(value, 0.0, sceneW, panelHeight.toDouble(), 0.0)
                val x = i * w
                val y = floor((panelHeight - h) / 2).toInt()

                g.color = Color(b, b, b)
                g.fillRect(x, y, w, h.toInt())
            }
        }

    }

    fun showRays(g: Graphics) {
        g.color = Color(250, 218, 94, 100)
        scene.forEach { g.drawLine(position.x, position.y, it.first.x, it.first.y) }
    }

    private fun map(value: Double, start1: Double, stop1: Double, start2: Double, stop2: Double): Double {
        return max(min((start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))), start2), stop2)
    }

}