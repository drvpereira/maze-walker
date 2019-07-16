package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import kotlin.math.PI
import kotlin.math.atan2

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

    private var currentCell: Cell? = null
    private var nextCell: Cell? = null

    private var x = 0
    private var y = 0
    private var vx = 0
    private var vy = 0

    var walking = false
    var angle: Int = 0
    var newAngle: Int = 0

    var scene = mutableListOf<Double>()

    init {
        lastCheckedNode = start
        openSet.add(start!!)
    }

    fun show(g: Graphics) {
        if (!walking) {
            path.show(g as Graphics2D)
            openSet.map { Cell(it.x, it.y, Cell.CellType.OPEN) }.forEach { it.show(g) }
            closedSet.map { Cell(it.x, it.y, Cell.CellType.CLOSED) }.forEach { it.show(g) }
        } else {
            lookAt(maze.getBoundaries(), g)
            g.color = Color(249, 166, 2)
            g.fillOval(x, y, CELL_SIZE / 2, CELL_SIZE / 2)
        }
    }

    private fun lookAt(walls: List<Boundary>, g: Graphics) {
        scene.clear()
        for (i in (angle - 35)..(angle + 35)) {
            val ray = Ray(Point(x + CELL_SIZE / 4, y + CELL_SIZE / 4), i.toDouble() * PI / 180.0)
            var minDistance = Double.MAX_VALUE
            var closest: Point? = null

            walls.forEach {
                val point = ray.cast(it)
                if (point != null) {
                    val dist = ray.pos.dist(point)
                    if (dist < minDistance) {
                        minDistance = dist
                        closest = point
                    }
                }
            }

            if (closest != null) {
                g.color = Color(250, 218, 94, 100)
                g.drawLine(x + CELL_SIZE / 4, y + CELL_SIZE / 4, closest!!.x, closest!!.y)
            }

            scene.add(minDistance)
        }

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
                    if ((g[openSet[i]] ?: 0) == (g[openSet[winner]] ?: 0) && (vh[openSet[i]]
                            ?: 0) < (vh[openSet[winner]] ?: 0)
                    ) {
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

    fun walk() {
        currentCell = path.initialPosition()

        x = currentCell!!.x * CELL_SIZE + CELL_SIZE / 4
        y = currentCell!!.y * CELL_SIZE + CELL_SIZE / 4

        nextCell = path.nextCell(x, y)

        val (nvx, nvy) = getVelocity()
        vx = nvx
        vy = nvy

        angle = ((atan2((nextCell!!.y - currentCell!!.y).toDouble(),
            (nextCell!!.x - currentCell!!.x).toDouble()) * 180.0 / PI).toInt() + 360) % 360
        newAngle = angle
        print(angle)

        walking = true
    }

    fun update() {
        var nextCellX = nextCell!!.x * CELL_SIZE + CELL_SIZE / 4
        var nextCellY = nextCell!!.y * CELL_SIZE + CELL_SIZE / 4

        if (x == nextCellX && y == nextCellY) {
            currentCell = nextCell
            nextCell = path.nextCell(x, y)

            val (nvx, nvy) = getVelocity()

            vx = 3 * nvx
            vy = 3 * nvy

            newAngle = ((atan2((path.nextCell(x, y).y - currentCell!!.y).toDouble(),
                (path.nextCell(x, y).x - currentCell!!.x).toDouble()) * 180.0 / PI).toInt() + 360) % 360

        }

        if (angle != newAngle) {

            if ((angle == 270 && newAngle == 0)) {
                newAngle = 360
            } else if ((angle == 0 && newAngle == 270)) {
                angle = 360
            }

            if (newAngle < angle)
                angle = angle - 5
            else
                angle = angle + 5

        } else {
            angle %= 360
            newAngle %= 360
            x += vx
            y += vy
        }

    }

    private fun getVelocity(): Pair<Int, Int> =
        Pair(nextCell!!.x - currentCell!!.x, nextCell!!.y - currentCell!!.y)

}