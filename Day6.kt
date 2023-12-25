import java.io.File
import java.io.InputStream

fun day6_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input6.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()
    val times = lineList.first().split(" ").mapNotNull { it.toIntOrNull() }
    val distances = lineList.last().split(" ").mapNotNull { it.toIntOrNull() }

    val result = times.mapIndexed { index, time ->
        var winCount = 0
        for (speed in 0..time) {
            if (speed * (time - speed) > distances[index]) {
                winCount++
            }
        }
        winCount
    }.reduce { acc, item -> acc*item }

    println(result)
}

fun day6_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input6.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()
    val time = lineList.first().split(" ")
        .mapNotNull { it.toIntOrNull() }.joinToString("").toLong()
    val distance = lineList.last().split(" ")
        .mapNotNull { it.toIntOrNull() }.joinToString("").toLong()

    // Optimized this for part 2, but runs reasonably fast with the whole loop as well.
    var loseCount = 0
    for (speed in 0..time) {
        if (speed * (time - speed) < distance) {
            loseCount++
        } else {
            break
        }
    }

    val winCount = time - (loseCount * 2)
    println(winCount)
}