import java.io.File
import java.io.InputStream

fun day8_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input8.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val rlDirections = lineList.first()

    val pointsToDirections = lineList.drop(2).associate { line ->
        val (point, directions) = line.split(" = ")
        val (left, right) = directions.drop(1).dropLast(1).split(", ")

        Pair(point, Pair(left, right))
    }

    var currentPoint = "AAA"
    var steps = 0
    var lrIndex = 0

    while (currentPoint != "ZZZ") {
        steps++
        val directions = pointsToDirections[currentPoint]!!
        val currentDirection = rlDirections[lrIndex]

        currentPoint = if (currentDirection.toString() == "L") {
            directions.first
        } else {
            directions.second
        }

        lrIndex++
        if (lrIndex == rlDirections.length) lrIndex = 0
    }

    println(steps)
}

fun day8_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input8.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val rlDirections = lineList.first()

    val pointsToDirections = lineList.drop(2).associate { line ->
        val (point, directions) = line.split(" = ")
        val (left, right) = directions.drop(1).dropLast(1).split(", ")

        Pair(point, Pair(left, right))
    }

    val startingPoints = pointsToDirections.keys.filter { it.last().toString() == "A" }

    val stepsPerPoint = startingPoints.map { startingPoint ->
        var steps = 0L
        var lrIndex = 0
        var currentPoint = startingPoint

        while (currentPoint.last().toString() != "Z") {
            steps++
            val directions = pointsToDirections[currentPoint]!!
            val currentDirection = rlDirections[lrIndex]

            currentPoint = if (currentDirection.toString() == "L") {
                directions.first
            } else {
                directions.second
            }

            lrIndex++
            if (lrIndex == rlDirections.length) lrIndex = 0
        }

        steps
    }

    println(findLCM(stepsPerPoint))
}

fun findGCD(a: Long, b: Long): Long {
    if (b == 0L) {
        return a
    }
    return findGCD(b, a % b)
}

fun findLCM(numbers: List<Long>): Long {
    fun findLCMOfTwo(a: Long, b: Long): Long {
        return (a * b) / findGCD(a, b)
    }

    var lcm = numbers.first()

    for (i in 1 until numbers.size) {
        lcm = findLCMOfTwo(lcm, numbers[i])
    }
    return lcm
}