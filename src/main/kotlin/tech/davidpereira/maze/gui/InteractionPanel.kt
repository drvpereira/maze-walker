package tech.davidpereira.maze.gui

import tech.davidpereira.maze.*
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

class InteractionPanel(private val mazeWindow: MazeWindow) : JPanel() {

    private val panelX = MAZE_SIZE + PANEL_PADDING
    private val panelY = PANEL_PADDING
    private val panelWidth = WINDOW_WIDTH - MAZE_SIZE - 2 * PANEL_PADDING
    private val panelHeight = MAZE_SIZE - 2 * PANEL_PADDING

    private val buttonGenerateMaze = object : JButton() {
        init {
            text = "Generate Maze"
            setBounds((panelWidth - 150) / 2, 20, 150, 40)
            addActionListener {
                generateMaze()
            }
        }
    }

    private val buttonAddWalker = object : JButton() {
        init {
            text = "Add Walker"
            setBounds((panelWidth - 150) / 2, 65, 150, 40)
            isVisible = false
            addActionListener {
                addWalker()
            }
        }
    }

    private val labelStartingPosition = object : JLabel() {
        init {
            text = "Start:"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 110, 100, 30)
        }
    }

    private val labelGoalPosition = object : JLabel() {
        init {
            text = "Goal:"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds(panelWidth / 2, 110, 100, 30)
        }
    }

    private val labelChosenStartPosition = object : JLabel() {
        init {
            text = "(0, 0)"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 140, 100, 20)
        }
    }

    private val labelChosenGoalPosition = object : JLabel() {
        init {
            text = "(0, 0)"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds(panelWidth / 2, 140, 100, 20)
        }
    }

    private val buttonFindPath = object : JButton() {
        init {
            text = "Find Path"
            setBounds((panelWidth - 150) / 2, 175, 150, 40)
            isVisible = false
            addActionListener {
                this.isEnabled = false
                mazeWindow.findPath()
            }
        }
    }

    private val buttonWalk = object : JButton() {
        init {
            text = "Walk"
            setBounds((panelWidth - 150) / 2, 220, 150, 40)
            isVisible = false
            addActionListener {
                this.isEnabled = false
                mazeWindow.walk()
            }
        }
    }

    private val panelWalkerVision = PanelWalkerVision(panelWidth)

    init {
        layout = null
        setBounds(panelX, panelY, panelWidth, panelHeight)

        add(buttonGenerateMaze)
        add(buttonAddWalker)
        add(labelStartingPosition)
        add(labelGoalPosition)
        add(labelChosenStartPosition)
        add(labelChosenGoalPosition)
        add(buttonFindPath)
        add(buttonWalk)
        add(panelWalkerVision)
    }

    private fun generateMaze() {
        buttonAddWalker.isVisible = true
        buttonAddWalker.isVisible = true
        buttonAddWalker.isEnabled = true
        buttonFindPath.isVisible = false
        labelStartingPosition.isVisible = false
        labelChosenStartPosition.isVisible = false
        labelGoalPosition.isVisible = false
        labelChosenGoalPosition.isVisible = false
        buttonWalk.isVisible = false
        panelWalkerVision.isVisible = false

        mazeWindow.generateMaze()
    }

    private fun addWalker() {
        mazeWindow.chooseStartPosition()
        labelStartingPosition.isVisible = true
        labelChosenStartPosition.isVisible = true
        labelGoalPosition.isVisible = true
        labelChosenGoalPosition.isVisible = true
        buttonAddWalker.isEnabled = false
    }

    fun showScreen(scene: Scene) {
        panelWalkerVision.updateWalker(scene)
    }

    fun enableButtonFindPath() {
        buttonFindPath.isVisible = true
        buttonFindPath.isEnabled = true
    }

    fun enableWalkingPanel() {
        buttonWalk.isVisible = true
        buttonWalk.isEnabled = true
        panelWalkerVision.isVisible = true
    }

    fun setLabelChosenGoalPosition(text: String) {
        labelChosenGoalPosition.text = text
    }

    fun setLabelChosenStartPosition(text: String) {
        labelChosenStartPosition.text = text
    }

}

