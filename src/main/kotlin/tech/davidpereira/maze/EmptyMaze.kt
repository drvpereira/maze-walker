package tech.davidpereira.maze

import java.awt.Graphics

class EmptyMaze(width: Int, height: Int) : Maze(Array(height) { y -> Array(width) { x -> Cell(x, y) } }) {

    override fun show(g: Graphics) {
        grid.forEach { i -> i.forEach { j -> j.show(g) } }
    }

}