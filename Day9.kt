import java.io.File
import java.io.InputStream

fun day9_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input9.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val result = lineList.sumOf { line ->
        val lastNumbers = calculateNextNumberForSequence(line.split(" ").map { it.toLong() }, emptyList())
        lastNumbers.sum()
    }

    println(result)
}

fun calculateNextNumberForSequence(sequence: List<Long>, lastNumbers: List<Long>): List<Long> {
    val lowerSequence = mutableListOf<Long>()
    val additionalLastNumbers = mutableListOf<Long>()

    if (sequence.size == 1) return lastNumbers.plus(sequence.first())

    for (i in sequence.size - 1 downTo 1) {
        val newValue = sequence[i] - sequence[i - 1]
        lowerSequence.add(0, newValue)
    }

    additionalLastNumbers.addAll(calculateNextNumberForSequence(lowerSequence, lastNumbers))

    return lastNumbers.plus(sequence.last()).plus(additionalLastNumbers)
}

fun day9_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input9.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val result = lineList.sumOf { line ->
        val sequence = line.split(" ").map { it.toLong() }
        val firstNumber = calculateFirstNumberForSequence(sequence, sequence.first())
        firstNumber
    }

    println(result)
}

fun calculateFirstNumberForSequence(sequence: List<Long>, previousSequenceFirst: Long): Long {
    val lowerSequence = mutableListOf<Long>()

    if (sequence.size == 1 || sequence.all { it == 0L }) return 0

    for (i in sequence.size - 1 downTo 1) {
        val newValue = sequence[i] - sequence[i - 1]
        lowerSequence.add(0, newValue)
    }

    return previousSequenceFirst - calculateFirstNumberForSequence(lowerSequence, lowerSequence.first())
}