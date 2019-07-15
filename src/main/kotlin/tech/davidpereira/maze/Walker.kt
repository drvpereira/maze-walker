package tech.davidpereira.maze

import java.awt.Graphics
import java.awt.Graphics2D

class Walker(private val maze: Maze) {

    private val start = maze.start

    private var path = Path()

    private var openSet = mutableListOf<Cell>()
    private var closedSet = mutableListOf<Cell>()
    private var lastCheckedNode: Cell? = null

    private var f: MutableMap<Cell, Int> = maze.grid.flatten().map { it to 0 }.toMap().toMutableMap()
    private var g: MutableMap<Cell, Int> = maze.grid.flatten().map { it to 0 }.toMap().toMutableMap()
    private var h: MutableMap<Cell, Int> = maze.grid.flatten().map { it to 0 }.toMap().toMutableMap()
    private var vh: MutableMap<Cell, Int> = maze.grid.flatten().map { it to 0 }.toMap().toMutableMap()

    init {
        lastCheckedNode = start
        openSet.add(start!!)
    }

    fun show(g: Graphics) {
        path.show(g as Graphics2D)
        openSet.map { Cell(it.x, it.y, CellType.OPEN) }.forEach { it.show(g) }
        closedSet.map { Cell(it.x, it.y, CellType.CLOSED) }.forEach { it.show(g) }
    }

    fun findPath(): Boolean {

        if (openSet.isNotEmpty()) {

            // Best next option
            var winner = 0

            for (i in 1 until openSet.size) {
                if ((f[openSet[i]] ?: 0) < (f[openSet[winner]] ?: 0)) {
                    winner = i
                }

                //if we have a tie according to the standard manhattanDistance
                if (f[openSet[i]] == f[openSet[winner]]) {
                    //Prefer to explore options with longer known paths (closer to goal)
                    if ((g[openSet[i]] ?: 0) > (g[openSet[winner]] ?: 0)) {
                        winner = i
                    }

                    //if we're using Manhattan distances then also break ties
                    //of the known distance measure by using the visual manhattanDistance.
                    //This ensures that the search concentrates on routes that look
                    //more direct. This makes no difference to the actual path distance
                    //but improves the look for things like games or more closely
                    //approximates the real shortest path if using grid sampled data for
                    //planning natural paths.
                    if ((g[openSet[i]] ?: 0) == (g[openSet[winner]] ?: 0) && (vh[openSet[i]] ?: 0) < (vh[openSet[winner]] ?: 0)) {
                        winner = i
                    }
                }
            }

            val current = openSet[winner]
            lastCheckedNode = current

            // Did I finish?
            if (current == maze.end) {
                return true
            }

            // Best option moves from openSet to closedSet
            openSet.remove(current)
            closedSet.add(current)

            // Check all the neighbors
            val neighbors = current.getNeighbours()

            for (i in 0 until neighbors.size) {
                val neighbor = maze.grid[neighbors[i].y][neighbors[i].x]

                // Valid next spot?
                if (!closedSet.contains(neighbor)) {
                    // Is this a better path than before?
                    val tempG = (g[current] ?: 0) + neighbor.manhattanDistance(current)

                    // Is this a better path than before?
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor)
                    } else if (tempG >= (g[neighbor] ?: 0)) {
                        // No, it's not a better path
                        continue
                    }

                    g[neighbor] = tempG
                    h[neighbor] = neighbor.manhattanDistance(maze.end!!)
                    vh[neighbor] = neighbor.euclidianDistance(maze.end!!)
                    f[neighbor] = (g[neighbor] ?: 0) + (h[neighbor] ?: 0)
                    neighbor.previous = current
                }

            }
        }

        return false
    }

    fun createPath() {
        openSet.clear()
        closedSet.clear()

        if (path.isEmpty()) {
            var cell = maze.grid[maze.end!!.y][maze.end!!.x]
            path.add(cell)

            while (cell.previous != null && !path.contains(cell.previous!!)) {
                val previous = maze.grid[cell.previous!!.y][cell.previous!!.x]
                path.add(previous)
                cell = previous

            }
        }

    }

    fun reset() {
        openSet.clear()
        closedSet.clear()
        path.clear()
    }

}