package tech.davidpereira.maze

import java.awt.Graphics

open class Maze(val grid: Array<Array<Cell>>) {

    var start: Cell? = null
    var end: Cell? = null

    open fun show(g: Graphics) {
        grid.forEach { i -> i.forEach { j -> j.show(g) } }
    }

    fun setStartPosition(x: Int, y: Int) {
        start = grid[y][x]
        start?.type = CellType.START
    }

    fun setGoalPosition(x: Int, y: Int) {
        end = grid[y][x]
        end?.type = CellType.GOAL
    }

    fun getBoundaries(): List<Boundary> {
        return grid.flatten().map { it.getBoundaries() } .flatten()
    }

}
