package tech.davidpereira.maze

import java.awt.Color
import java.awt.Graphics

enum class CellType {

    NORMAL {
        override fun show(c: Cell, g: Graphics) {
            c.getBoundaries().forEach { it.show(g) }
        }
    },

    START {
        override fun show(c: Cell, g: Graphics) {
            var center = c.getCenter()
            g.color = Color.RED
            g.fillOval(center.x - CELL_SIZE / 4, center.y - CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2)
            c.getBoundaries().forEach { it.show(g) }
        }
    },

    GOAL {
        override fun show(c: Cell, g: Graphics) {
            repeat(5) { j ->
                repeat(5) { i ->
                    g.color = if ((i + j) % 2 == 0) Color.BLACK else Color.WHITE
                    g.fillRect(
                        c.x * CELL_SIZE + j * CELL_SIZE / 5,
                        c.y * CELL_SIZE + i * CELL_SIZE / 5,
                        CELL_SIZE / 5,
                        CELL_SIZE / 5
                    )
                }
            }
            c.getBoundaries().forEach { it.show(g) }
        }
    },

    OPEN {
        override fun show(c: Cell, g: Graphics) {
            var position = c.getPosition()
            g.color = Color.GREEN
            g.fillRect(position.x, position.y, CELL_SIZE, CELL_SIZE)
            c.getBoundaries().forEach { it.show(g) }
        }
    },

    CLOSED {
        override fun show(c: Cell, g: Graphics) {
            var position = c.getPosition()
            g.color = Color.RED
            g.fillRect(position.x, position.y, CELL_SIZE, CELL_SIZE)
            c.getBoundaries().forEach { it.show(g) }
        }
    };

    abstract fun show(c: Cell, g: Graphics)

}