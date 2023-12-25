import java.io.File
import java.io.InputStream

fun day3_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input3.txt").inputStream()

    val grid = inputStream.bufferedReader().readLines().map { it.toCharArray().toList() }
    val safeGrid = grid.map {
        val newList = it.toMutableList()
        newList.add(0, ".".first())
        newList.add(".".first())
        newList.toList()
    }.toMutableList()
    safeGrid.add(safeGrid.first().map { ".".first() })
    safeGrid.add(0, safeGrid.first().map { ".".first() })

    val currentNum = mutableListOf<Char>()
    var currentIsPart = false
    var sum = 0L
    safeGrid.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, char ->
            if (char.isDigit()) {
                currentNum.add(char)
                if (hasSymbolNeighbor(rowIndex, columnIndex, safeGrid)) currentIsPart = true
                if (!row[columnIndex + 1].isDigit()) {
                    // We are at the end of the number, let's add it and reset the current
                    if (currentIsPart) {
                        val num = currentNum.toCharArray().concatToString().toInt()
                        sum += num
                    }
                    currentNum.clear()
                    currentIsPart = false
                }
            }
        }
    }

    println(sum)
}

fun day3_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input3.txt").inputStream()

    val grid = inputStream.bufferedReader().readLines().map { it.toCharArray().toList() }
    val safeGrid = grid.map {
        val newList = it.toMutableList()
        newList.add(0, ".".first())
        newList.add(".".first())
        newList.toList()
    }.toMutableList()
    safeGrid.add(safeGrid.first().map { ".".first() })
    safeGrid.add(0, safeGrid.first().map { ".".first() })

    val starToNumbersMap = mutableMapOf<String, MutableSet<Int>>()

    val currentNum = mutableListOf<Char>()
    val currentStarNeighbors = mutableSetOf<String>()
    safeGrid.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, char ->
            if (char.isDigit()) {
                currentNum.add(char)
                currentStarNeighbors.addAll(getStarNeighbors(rowIndex, columnIndex, safeGrid))
                if (!row[columnIndex + 1].isDigit()) {
                    val num = currentNum.toCharArray().concatToString().toInt()
                    currentNum.clear()

                    currentStarNeighbors.forEach { starCords ->
                        val neighbors = starToNumbersMap.getOrPut(starCords) {
                            mutableSetOf()
                        }
                        neighbors.add(num)
                    }

                    currentNum.clear()
                    currentStarNeighbors.clear()
                }
            }
        }
    }

    val result = starToNumbersMap.filter { it.value.size == 2 }
        .values.sumOf { it.first() * it.last() }

    println(result)
}

fun hasSymbolNeighbor(row: Int, column: Int, grid: List<List<Char>>): Boolean {
    return isSymbol(grid[row - 1][column - 1]) || isSymbol(grid[row - 1][column]) || isSymbol(grid[row - 1][column + 1]) ||
            isSymbol(grid[row][column - 1]) || isSymbol(grid[row][column + 1]) ||
            isSymbol(grid[row + 1][column - 1]) || isSymbol(grid[row + 1][column]) || isSymbol(grid[row + 1][column + 1])
}

fun getStarNeighbors(row: Int, column: Int, grid: List<List<Char>>): Set<String> {
    val stars = mutableSetOf<String>()
    for (i in row - 1 .. row + 1) {
        for (j in column - 1 .. column + 1) {
            if (grid[i][j].isStar()) {
                stars.add("$i,$j")
            }
        }
    }

    return stars
}

fun isSymbol(c: Char): Boolean {
    return c != ".".first() && !c.isDigit()
}

fun Char.isStar(): Boolean {
    return this == "*".first()
}