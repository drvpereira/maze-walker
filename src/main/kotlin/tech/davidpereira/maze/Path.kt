package tech.davidpereira.maze

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D

class Path {

    private val path = mutableListOf<Cell>()

    fun show(g: Graphics2D) {
        val zip = path.drop(1).zip(path.dropLast(1))
        g.stroke = BasicStroke(2.0f)
        g.color = Color.RED
        zip.forEach {
            g.drawLine(it.first.x * CELL_SIZE + CELL_SIZE / 2, it.first.y * CELL_SIZE + CELL_SIZE / 2,
                it.second.x * CELL_SIZE + CELL_SIZE / 2, it.second.y * CELL_SIZE + CELL_SIZE / 2)
        }

        g.stroke = BasicStroke(1.0f)
    }

    fun add(cell: Cell) = path.add(cell)

    fun contains(cell: Cell): Boolean = path.contains(cell)

    fun clear() = path.clear()

    fun isEmpty(): Boolean = path.isEmpty()

    fun initialPosition(): Cell = path.last()

    fun nextCell(x: Int, y: Int) : Cell {
        val currentCellIndex = currentCellIndex(x, y)
        val currentCell = path[currentCellIndex]
        return if (currentCellIndex > 0 && currentCellIndex < path.size) path[currentCellIndex - 1] else currentCell
    }

    private fun currentCellIndex(x: Int, y: Int) : Int = path.indexOf(Cell(x / CELL_SIZE, y / CELL_SIZE))

}
