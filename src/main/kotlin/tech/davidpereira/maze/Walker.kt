package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics
import kotlin.math.cos

class Walker(private val maze: Maze, private val path: Path) {

    private var currentCell: Cell
    private var nextCell: Cell

    private var position: Point
    private var velocity: Vector

    private val fov = Angle.withDegrees(70)
    private var angle: Angle
    private var newAngle: Angle

    init {
        currentCell = path.initialPosition()
        position = currentCell.getCenter()

        nextCell = path.nextCell(position.x, position.y)

        velocity = currentCell.velocityTo(nextCell).scale(3.0)
        angle = velocity.getAngle()
        newAngle = angle
    }

    fun show(scene: Scene?, g: Graphics) {
        g.color = Color(249, 166, 2)
        g.fillOval(position.x - CELL_SIZE / 4, position.y - CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2)
        scene?.showRays(g)
    }

    fun lookAt(): Scene {
        val walls = maze.getBoundaries()
        return getListOfAngles().map { Ray(position, it) }
                .mapNotNull { ray -> walls.mapNotNull { ray.cast(it) }
                    .map {
                        val a = ray.angle.getRadians() - this.angle.getRadians()
                        var d = position.dist(it) * cos(a)
                        Pair(it, d)
                    }
                    .minBy { it.second }

                }
            .distinct().let { Scene(position, it) }
    }

    private fun getListOfAngles(): List<Angle> {
        val numberOfStripes = VIEWER_PANEL_WIDTH / VIEWER_RESOLUTION
        val resolution = Angle.withRadians(fov.getRadians() / numberOfStripes)
        var result = mutableListOf<Angle>()

        var current = angle.getRadians() - fov.getRadians() / 2
        while (current <= angle.getRadians() + fov.getRadians() / 2) {
            current += resolution.getRadians()
            result.add(Angle.withRadians(current))
        }

        return result
    }

    fun walk() {
        val nextCellCenter = nextCell.getCenter()

        if (nextCellCenter == position) {
            currentCell = nextCell
            nextCell = path.nextCell(position.x, position.y)

            velocity = currentCell.velocityTo(nextCell).scale(3.0)
            newAngle = velocity.getAngle()
        } else {
            if (angle != newAngle) {
                angle = angle.moveTowards(newAngle, Angle.withDegrees(5))
            } else {
                angle.normalize()
                position += velocity
            }
        }

    }

}