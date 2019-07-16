package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel
import kotlin.math.*

class PanelWalkerVision(private val panelWidth: Int) : JPanel() {

    private val panelHeight = panelWidth * 3 / 4

    init {
        background = Color.BLACK
        setBounds(0, 330, panelWidth, panelHeight)
        isVisible = false
    }

    private var scene = listOf<Double>()

    fun updateWalker(walker: Walker?) {
        if (walker != null && walker?.scene != null) {
            scene = walker?.scene!!
            repaint()
        }
    }

    override fun paintComponent(g: Graphics) {
        if (scene.isNotEmpty()) {
            val sceneW = MAZE_SIZE / 5.0
            val w = ceil((panelWidth.toDouble() / scene.size.toDouble())).toInt()

            for (i in 0 until scene?.size) {
                val value = abs(scene[i] * cos(((i - scene.size / 2)) * PI / 180))

                val b = map(value*value, 0.0, sceneW*sceneW, 255.0, 0.0).toInt()
                val h = map(value, 0.0, sceneW, panelHeight.toDouble(),0.0)
                val x = i * w
                val y = floor((panelHeight - h) / 2).toInt()

                g.color = Color(b, b, b)
                g.fillRect(x, y, w, h.toInt())
            }
        }
    }

}