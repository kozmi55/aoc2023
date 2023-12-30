import java.io.File
import java.io.InputStream

fun day10_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input10.txt").inputStream()

    val grid = inputStream.bufferedReader().readLines().map { it.toCharArray().toList() }
    val safeGrid = grid.map {
        val newList = it.toMutableList()
        newList.add(0, ".".first())
        newList.add(".".first())
        newList.toList()
    }.toMutableList()
    safeGrid.add(safeGrid.first().map { ".".first() })
    safeGrid.add(0, safeGrid.first().map { ".".first() })

    var start = Pair(0, 0)
    outer@ for (i in 0 until safeGrid.size) {
        for (j in 0 until safeGrid[i].size) {
            val char = safeGrid[i][j]
            if (char.toString() == "S") {
                start = Pair(i, j)
                break@outer
            }
        }
    }

    var currentPoint = Pair(start.first + 1, start.second)
    var previousPoint = start
    var steps = 1

    while (currentPoint != start) {
        steps++
        val char = safeGrid[currentPoint.first][currentPoint.second].toString()
        val possibleDirections = charToModifierMap[char]!!
        val nextMove = possibleDirections.first {
            Pair(currentPoint.first + it.first, currentPoint.second + it.second) != previousPoint
        }
        previousPoint = currentPoint
        currentPoint = Pair(currentPoint.first + nextMove.first, currentPoint.second + nextMove.second)
    }

    println(steps / 2)
}

val charToModifierMap = mapOf(
    "|" to setOf(Pair(1, 0), Pair(-1, 0)),
    "L" to setOf(Pair(0, 1), Pair(-1, 0)),
    "-" to setOf(Pair(0, 1), Pair(0, -1)),
    "J" to setOf(Pair(-1, 0), Pair(0, -1)),
    "7" to setOf(Pair(1, 0), Pair(0, -1)),
    "F" to setOf(Pair(1, 0), Pair(0, 1)),
)