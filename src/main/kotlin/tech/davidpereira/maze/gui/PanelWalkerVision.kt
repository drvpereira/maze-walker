package tech.davidpereira.maze.gui

import tech.davidpereira.maze.Scene
import tech.davidpereira.maze.Walker
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class PanelWalkerVision(private val panelWidth: Int) : JPanel() {

    private val panelHeight = panelWidth * 3 / 4

    init {
        background = Color.BLACK
        setBounds(0, 280, panelWidth, panelHeight)
        isVisible = false
    }

    private var scene: Scene? = null

    fun updateWalker(scene: Scene) {
        this.scene = scene
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        scene?.show(panelWidth, panelHeight, g)
    }

}