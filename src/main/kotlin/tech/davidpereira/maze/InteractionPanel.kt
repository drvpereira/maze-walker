package tech.davidpereira.maze

import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import kotlin.math.max
import kotlin.math.min

class InteractionPanel(mazeWindow: MazeWindow) : JPanel() {

    private val panelX = MAZE_SIZE + PANEL_PADDING
    private val panelY = PANEL_PADDING
    private val panelWidth = WINDOW_WIDTH - MAZE_SIZE - 2 * PANEL_PADDING
    private val panelHeight = MAZE_SIZE - 2 * PANEL_PADDING

    val buttonGenerateMaze = object : JButton() {
        init {
            text = "Generate Maze"
            setBounds((panelWidth - 150) / 2, 20, 150, 40)
            addActionListener {
                reset()
                mazeWindow.generateMaze()
            }
        }
    }

    val buttonAddWalker = object : JButton() {
        init {
            text = "Add Walker"
            setBounds((panelWidth - 150) / 2, 65, 150, 40)
            isVisible = false
            addActionListener {
                mazeWindow.chooseStartPosition()
                this.isEnabled = false
            }
        }
    }

    val labelStartingPosition = object : JLabel() {
        init {
            text = "Choose starting position:"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 110, 200, 30)
        }
    }

    val labelChosenStartPosition = object : JLabel() {
        init {
            text = "(0, 0)"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 140, 200, 20)
        }
    }

    val labelGoalPosition = object : JLabel() {
        init {
            text = "Choose goal position:"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 160, 200, 30)
        }
    }

    val labelChosenGoalPosition = object : JLabel() {
        init {
            text = "(0, 0)"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 190, 200, 20)
        }
    }

    val buttonFindPath = object : JButton() {
        init {
            text = "Find Path"
            setBounds((panelWidth - 150) / 2, 225, 150, 40)
            isVisible = false
            addActionListener {
                this.isEnabled = false
                mazeWindow.findPath()
            }
        }
    }

    val buttonWalk = object : JButton() {
        init {
            text = "Walk"
            setBounds((panelWidth - 150) / 2, 270, 150, 40)
            isVisible = false
            addActionListener {
                this.isEnabled = false
                mazeWindow.walk()
            }
        }
    }

    val panelWalkerVision = PanelWalkerVision(panelWidth)

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

    private fun reset() {
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
    }

    fun showScreen(walker: Walker?) {
        panelWalkerVision.updateWalker(walker)
    }

}

