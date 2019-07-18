package tech.davidpereira.maze.gui

import tech.davidpereira.maze.*
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JFrame
import javax.swing.Timer
import javax.swing.WindowConstants

class MazeWindow : JFrame(), ActionListener, MouseListener, MouseMotionListener {

    private val mazePanel = MazePanel(this)
    private val interactionPanel = InteractionPanel(this)

    private val mazeGenerator = MazeGenerator(MAZE_SIZE / CELL_SIZE, MAZE_SIZE / CELL_SIZE)
    private var maze: Maze = EmptyMaze(MAZE_SIZE / CELL_SIZE,MAZE_SIZE / CELL_SIZE)

    private var pathFinder: PathFinder? = null
    private var walker: Walker? = null

    private var choosingStartPosition = false
    private var choosingGoalPosition = false
    private var findingPath = false
    private var walking = false

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
        mazePanel.startPosition = null
        mazePanel.goalPosition = null
        maze = mazeGenerator.generate()

        choosingStartPosition = false
        choosingGoalPosition = false
        findingPath = false
        walking = false

        pathFinder = null
        walker = null
    }

    fun chooseStartPosition() {
        choosingStartPosition = true
    }

    fun showMaze(g: Graphics) {
        pathFinder?.show(g)
        maze.show(g)

        val scene = walker?.lookAt()
        walker?.show(scene, g)
        if (scene != null) {
            interactionPanel.showScreen(scene)
        }
    }

    fun findPath() {
        pathFinder = PathFinder(maze)
        findingPath = true
    }

    private fun highlight(x: Int, y: Int) {

        if (choosingStartPosition) {
            val currentCell = Cell(x, y, CellType.START)
            mazePanel.startPosition = currentCell
            interactionPanel.setLabelChosenStartPosition(currentCell.toString())
        } else if (choosingGoalPosition) {
            val currentCell = Cell(x, y, CellType.GOAL)
            mazePanel.goalPosition = currentCell
            interactionPanel.setLabelChosenGoalPosition(currentCell.toString())
        }

    }

    override fun actionPerformed(e: ActionEvent) {
        if (findingPath) {
            pathFinder?.findPath()
            findingPath = !(pathFinder?.foundPath ?: false)
        } else if (!walking){
            if (pathFinder?.foundPath == true && walker == null) {
                interactionPanel.enableWalkingPanel()
                walker = Walker(maze, pathFinder?.path!!)
            }
        } else {
            walker?.walk()
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
            interactionPanel.enableButtonFindPath()
            choosingStartPosition = false
            choosingGoalPosition = false
        }

        if (choosingStartPosition) {
            maze.setStartPosition(mazePanel.startPosition!!.x, mazePanel.startPosition!!.y)
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
        walking = true
    }

}
