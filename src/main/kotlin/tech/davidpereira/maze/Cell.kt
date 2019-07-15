package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

enum class CellType {
    NORMAL, START, GOAL, OPEN, CLOSED, PATH
}

class Cell(val x: Int, val y: Int, var type: CellType = CellType.NORMAL) {

    private val walls = mutableListOf(false, false, false, false)

    var previous: Cell? = null
    var visited = false

    init {
        if (type == CellType.NORMAL) {
            walls.replaceAll { true }
        }
    }

    fun show(g: Graphics) {
        when (type) {
            CellType.OPEN -> {
                g.color = Color.GREEN
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            }
            CellType.CLOSED -> {
                g.color = Color.RED
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            }
            CellType.PATH -> {
                g.color = Color.MAGENTA
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            }
            CellType.START -> {
                g.color = Color.RED
                g.fillOval(x * CELL_SIZE + CELL_SIZE / 4, y * CELL_SIZE + CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2)
            }
            CellType.GOAL -> repeat(5) { j ->
                repeat(5) { i ->
                    g.color = if ((i + j) % 2 == 0) Color.BLACK else Color.WHITE
                    g.fillRect(
                        x * CELL_SIZE + j * CELL_SIZE / 5,
                        y * CELL_SIZE + i * CELL_SIZE / 5,
                        CELL_SIZE / 5,
                        CELL_SIZE / 5
                    )
                }
            }
        }
        g.color = Color.BLACK

        if (walls[0]) g.drawLine(x * CELL_SIZE, y * CELL_SIZE, (x + 1) * CELL_SIZE, y * CELL_SIZE)
        if (walls[1]) g.drawLine((x + 1) * CELL_SIZE, y * CELL_SIZE, (x + 1) * CELL_SIZE, (y + 1) * CELL_SIZE)
        if (walls[2]) g.drawLine(x * CELL_SIZE, (y + 1) * CELL_SIZE, (x + 1) * CELL_SIZE, (y + 1) * CELL_SIZE)
        if (walls[3]) g.drawLine(x * CELL_SIZE, y * CELL_SIZE, x * CELL_SIZE, (y + 1) * CELL_SIZE)
    }

    fun removeWallTo(chosenCell: Cell) {
        when {
            this.y - chosenCell.y == 1 -> {
                this.walls[0] = false; chosenCell.walls[2] = false
            }
            this.y - chosenCell.y == -1 -> {
                this.walls[2] = false; chosenCell.walls[0] = false
            }
            this.x - chosenCell.x == 1 -> {
                this.walls[3] = false; chosenCell.walls[1] = false
            }
            this.x - chosenCell.x == -1 -> {
                this.walls[1] = false; chosenCell.walls[3] = false
            }
        }
    }

    fun getNeighbours(): List<Cell> {
        val result = mutableListOf<Cell>()
        if (!walls[0]) result.add(Cell(x, y - 1))
        if (!walls[1]) result.add(Cell(x + 1, y))
        if (!walls[2]) result.add(Cell(x, y + 1))
        if (!walls[3]) result.add(Cell(x - 1, y))
        return result
    }

    fun manhattanDistance(b: Cell): Int {
        return abs(x - b.x) + abs(y - b.y)
    }

    fun euclidianDistance(b: Cell): Int {
        return sqrt((x - b.x).toDouble().pow(2) + (y - b.y).toDouble().pow(2)).toInt()
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

}

