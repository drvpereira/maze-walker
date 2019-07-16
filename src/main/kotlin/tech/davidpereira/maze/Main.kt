package tech.davidpereira.maze

import javax.swing.SwingUtilities
import kotlin.math.max
import kotlin.math.min

fun map(
    value: Double,
    start1: Double, stop1: Double,
    start2: Double, stop2: Double
): Double {
    return max(min((start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))), start2), stop2)
}

fun main() {
    SwingUtilities.invokeLater { MazeWindow() }
}