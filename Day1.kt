import java.io.File
import java.io.InputStream

fun day1_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input1.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val sum = lineList.sumOf { line ->
        val chars = line.toCharArray()
        val first = chars.first { it.isDigit() }
        val last = chars.last { it.isDigit() }
        "$first$last".toInt()
    }

    println(sum)
}

fun day1_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input1.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val textPattern = "one|two|three|four|five|six|seven|eight|nine"
    val numberPattern = "[0-9]"
    val regexFirst = Regex( "($textPattern|$numberPattern)")
    val regexLast = Regex("(${textPattern.reversed()}|$numberPattern)")

    val sum = lineList.sumOf { line ->
        val firstMatch = regexFirst.find(line)?.value!!
        val lastMatch = regexLast.find(line.reversed())?.value!!

        val first = if (firstMatch.length > 1) {
            wordMap[firstMatch]
        } else {
            firstMatch.toInt()
        }
        val last = if (lastMatch.length > 1) {
            wordMap[lastMatch.reversed()]
        } else {
            lastMatch.toInt()
        }
        "$first$last".toInt()
    }

    println(sum)
}

val wordMap = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)