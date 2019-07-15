package tech.davidpereira.maze

import java.util.*

class MazeGenerator(private val width: Int, private val height: Int) {

    private lateinit var grid: Array<Array<Cell>>
    private lateinit var stack: Deque<Cell>
    private lateinit var currentCell: Cell

    init {
        reset()
    }

    fun reset() {
        grid = Array(height) { y -> Array(width) { x -> Cell(x, y) } }
        stack = ArrayDeque()
        currentCell = grid[0][0]
    }

    fun generate(): Maze {
        reset()

        currentCell.visited = true

        while (grid.any { it.any { !it.visited } }) {

            val chosenNeighbour = chooseNeighbour()

            if (chosenNeighbour != null) {

                chosenNeighbour.visited = true
                stack.push(currentCell)
                currentCell.removeWallTo(chosenNeighbour)
                currentCell = chosenNeighbour

            } else if (!stack.isEmpty()) {
                currentCell = stack.pop()
            }
        }

        return Maze(grid)
    }

    private fun chooseNeighbour(): Cell? {
        val x = currentCell.x
        val y = currentCell.y

        return listOf(Pair(x + 1, y), Pair(x - 1, y), Pair(x, y - 1), Pair(x, y + 1))
                    .filter { it.first in 0..19 && it.second in 0..19 }
                    .map { grid[it.second][it.first] }
                    .filter { !it.visited }
                    .shuffled().firstOrNull()
    }

}