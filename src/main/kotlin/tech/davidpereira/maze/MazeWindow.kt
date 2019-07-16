package tech.davidpereira.maze

import java.awt.Graphics
import java.awt.event.*
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.Timer
import javax.swing.WindowConstants

const val MAZE_SIZE = 600
const val WINDOW_WIDTH = 950
const val TITLE_BAR_HEIGHT = 22
const val WINDOW_HEIGHT = MAZE_SIZE
const val CELL_SIZE = 30
const val PANEL_PADDING = 10
const val FPS = 30

class MazeWindow : JFrame(), ActionListener, MouseListener, MouseMotionListener {

    private val mazePanel = MazePanel(this)
    private val interactionPanel = InteractionPanel(this)

    private val mazeGenerator = MazeGenerator(MAZE_SIZE / CELL_SIZE, MAZE_SIZE / CELL_SIZE)
    private var maze: Maze = EmptyMaze(MAZE_SIZE / CELL_SIZE, MAZE_SIZE / CELL_SIZE)
    private var walker: Walker? = null

    private var choosingStartPosition = false
    private var choosingGoalPosition = false
    private var findingPath = false
    private var pathFound = false

    init {
        title = "Maze Walker"
        isVisible = true
        isResizable = false
        layout = null
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT + TITLE_BAR_HEIGHT + 1)

        Timer(1000 / FPS, this).start()
        addMouseListener(this)
        addMouseMotionListener(this)

        add(mazePanel)
        add(interactionPanel)
    }

    fun generateMaze() {
        findingPath = false
        pathFound = false
        mazePanel.startPosition = null
        mazePanel.goalPosition = null
        maze = mazeGenerator.generate()
        walker?.reset()
    }

    fun chooseStartPosition() {
        choosingStartPosition = true
        interactionPanel.labelStartingPosition.isVisible = true
        interactionPanel.labelChosenStartPosition.isVisible = true
    }

    fun showMaze(g: Graphics) {
        walker?.show(g)
        interactionPanel.showScreen(walker)
        maze.show(g)
    }

    fun findPath() {
        walker = Walker(maze)
        findingPath = true
    }

    private fun highlight(x: Int, y: Int) {

        if (choosingStartPosition) {
            val currentCell = Cell(x, y, Cell.CellType.START)
            mazePanel.startPosition = currentCell
            interactionPanel.labelChosenStartPosition.text = currentCell.toString()
        } else if (choosingGoalPosition) {
            val currentCell = Cell(x, y, Cell.CellType.GOAL)
            mazePanel.goalPosition = currentCell
            interactionPanel.labelChosenGoalPosition.text = currentCell.toString()
        }

    }

    override fun actionPerformed(e: ActionEvent) {
        if (findingPath) {
            if (!pathFound) {
                if (walker?.findPath() == true) {
                    pathFound = true
                }
            } else {
                walker?.createPath()
                interactionPanel.buttonWalk.isVisible = true
                interactionPanel.panelWalkerVision.isVisible = true
            }
        }

        if (walker?.walking == true) {
            walker?.update()
        }

        repaint()
    }

    override fun mouseReleased(e: MouseEvent) {

    }

    override fun mouseEntered(e: MouseEvent) {

    }

    override fun mouseClicked(e: MouseEvent) {
        if (choosingGoalPosition) {
            maze.setGoalPosition(mazePanel.goalPosition!!.x, mazePanel.goalPosition!!.y)
            interactionPanel.buttonFindPath.isVisible = true
            interactionPanel.buttonFindPath.isEnabled = true
            choosingStartPosition = false
            choosingGoalPosition = false
        }

        if (choosingStartPosition) {
            maze.setStartPosition(mazePanel.startPosition!!.x, mazePanel.startPosition!!.y)
            interactionPanel.labelGoalPosition.isVisible = true
            interactionPanel.labelChosenGoalPosition.isVisible = true
            choosingStartPosition = false
            choosingGoalPosition = true
        }
    }

    override fun mouseExited(e: MouseEvent) {

    }

    override fun mousePressed(e: MouseEvent) {

    }

    override fun mouseDragged(e: MouseEvent) {

    }

    override fun mouseMoved(e: MouseEvent) {
        if (e.x <= MAZE_SIZE)
            highlight(e.x / CELL_SIZE, (e.y - TITLE_BAR_HEIGHT) / CELL_SIZE)
    }

    fun walk() {
        walker?.walk()
    }

}
