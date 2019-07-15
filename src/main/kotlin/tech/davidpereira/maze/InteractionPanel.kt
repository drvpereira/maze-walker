package tech.davidpereira.maze

import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

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
            setBounds((panelWidth - 150) / 2, 80, 150, 40)
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
            setBounds((panelWidth - 200) / 2, 120, 200, 40)
        }
    }

    val labelChosenStartPosition = object : JLabel() {
        init {
            text = "(0, 0)"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 170, 200, 20)
        }
    }

    val labelGoalPosition = object : JLabel() {
        init {
            text = "Choose goal position:"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 200, 200, 40)
        }
    }

    val labelChosenGoalPosition = object : JLabel() {
        init {
            text = "(0, 0)"
            horizontalAlignment = SwingConstants.CENTER
            isVisible = false
            setBounds((panelWidth - 200) / 2, 240, 200, 20)
        }
    }

    val buttonFindPath = object : JButton() {
        init {
            text = "Find Path"
            setBounds((panelWidth - 150) / 2, 280, 150, 40)
            isVisible = false
            addActionListener {
                this.isEnabled = false
                mazeWindow.findPath()
            }
        }
    }

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
    }

}