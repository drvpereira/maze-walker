package tech.davidpereira.maze.gui

import tech.davidpereira.maze.Cell
import tech.davidpereira.maze.MAZE_SIZE
import java.awt.Graphics
import javax.swing.JPanel

class MazePanel(private val mazeWindow: MazeWindow) : JPanel() {

    var startPosition: Cell? = null
    var goalPosition: Cell? = null

    init {
        setBounds(0, 0, MAZE_SIZE + 1, MAZE_SIZE + 1)
        isFocusable = true
    }

    override fun paintComponent(g: Graphics) {
        startPosition?.show(g)
        goalPosition?.show(g)
        mazeWindow.showMaze(g)
    }

}