package tech.davidpereira.maze

import java.awt.Graphics
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


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
        type.show(this, g)
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

    fun getBoundaries(): List<Boundary> {
        val result = mutableListOf<Boundary>()
        if (walls[0]) result.add(Boundary(Point(x * CELL_SIZE, y * CELL_SIZE), Point((x + 1) * CELL_SIZE, y * CELL_SIZE)))
        if (walls[1]) result.add(Boundary(Point((x + 1) * CELL_SIZE, y * CELL_SIZE), Point((x + 1) * CELL_SIZE, (y + 1) * CELL_SIZE)))
        if (walls[2]) result.add(Boundary(Point(x * CELL_SIZE, (y + 1) * CELL_SIZE), Point((x + 1) * CELL_SIZE, (y + 1) * CELL_SIZE)))
        if (walls[3]) result.add(Boundary(Point(x * CELL_SIZE, y * CELL_SIZE), Point(x * CELL_SIZE, (y + 1) * CELL_SIZE)))
        return result
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

    fun getCenter(): Point {
        return Point(x * CELL_SIZE + CELL_SIZE / 2, y * CELL_SIZE + CELL_SIZE / 2)
    }

    fun velocityTo(nextCell: Cell): Vector {
        return Vector((nextCell.x - x).toDouble(), (y - nextCell.y).toDouble())
    }

    fun getPosition(): Point {
        return Point(x * CELL_SIZE, y * CELL_SIZE)
    }

}

