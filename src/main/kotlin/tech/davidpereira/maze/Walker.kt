package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics

class Walker(private val maze: Maze, val path: Path) {

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

        if (scene != null) {
            g.color = Color(250, 218, 94, 100)
            scene.scene.forEach { g.drawLine(position.x, position.y, it.x.toInt(), it.y.toInt()) }
        }
    }

    fun lookAt(): Scene {
        val walls = maze.getBoundaries()
        val scene = getListOfAngles().map { Ray(position, it) }
                .mapNotNull { ray -> walls.mapNotNull { ray.cast(it) }.minBy { ray.pos.dist(it) } }
            .map { it.toVector() }.distinct()

        return Scene(position, scene)
    }

    private fun getListOfAngles(): List<Angle> {
        val resolution = Angle.withRadians(0.005)
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