package tech.davidpereira.maze

import tech.davidpereira.maze.gui.MazeWindow
import javax.swing.SwingUtilities

const val MAZE_SIZE = 600
const val WINDOW_WIDTH = 1020
const val TITLE_BAR_HEIGHT = 22
const val WINDOW_HEIGHT = MAZE_SIZE
const val CELL_SIZE = 30
const val PANEL_PADDING = 10
const val VIEWER_PANEL_WIDTH = WINDOW_WIDTH - MAZE_SIZE - 2 * PANEL_PADDING
const val VIEWER_RESOLUTION = 2
const val FPS = 30

fun main() {
    SwingUtilities.invokeLater { MazeWindow() }
}