import java.io.File
import java.io.InputStream

fun day4_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input4.txt").inputStream()

    val games = inputStream.bufferedReader().readLines().map { it.split(": ").last() }

    val totalValue = games.sumOf { game ->
        val (winningNumbersString, elfNumbersString) = game.split(" | ")
        val winningNumbers = winningNumbersString.split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }.toSet()
        val elfNumbers = elfNumbersString.split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }.toSet()

        val gameScore = elfNumbers.intersect(winningNumbers).size
        val gameValue = if (gameScore == 0) 0 else {
            var value = 1
            for (i in 1 until gameScore) {
                value *= 2
            }
            value
        }

        gameValue
    }

    println(totalValue)
}

fun day4_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input4.txt").inputStream()

    val games = inputStream.bufferedReader().readLines().map { it.split(": ").last() }

    val copiesMap = List(games.size) { index -> Pair(index, 1) }
        .associate { it }
        .toMutableMap()

    games.forEachIndexed { index, game ->
        val (winningNumbersString, elfNumbersString) = game.split(" | ")
        val winningNumbers = winningNumbersString.split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }.toSet()
        val elfNumbers = elfNumbersString.split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }.toSet()

        val gameScore = elfNumbers.intersect(winningNumbers).size

        val currentCopies = copiesMap[index]!!
        val minIndex = minOf(index + gameScore, games.size - 1)
        for (i in index + 1 ..minIndex) {
            copiesMap.compute(i) { _, value -> value!! + currentCopies }
        }
    }

    val result = copiesMap.values.sum()
    println(result)
}