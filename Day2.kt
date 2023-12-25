import java.io.File
import java.io.InputStream

private const val RED_MAX = 12
private const val GREEN_MAX = 13
private const val BLUE_MAX = 14

fun day2_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input2.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val sum = lineList.sumOf {
        gameValue(it)
    }

    println(sum)
}

fun gameValue(game: String): Int {
    val parts = game.split(": ")
    val id = parts.first().split(" ").last().toInt()
    val picks = parts.last().split("; ")

    picks.forEach {
        val colors = it.split(", ")
        colors.forEach { colorString ->
            val colorParts = colorString.split(" ")
            val count = colorParts.first().toInt()
            val valid = when (colorParts.last()) {
                "blue" -> count <= BLUE_MAX
                "green" -> count <= GREEN_MAX
                else -> count <= RED_MAX
            }

            if (!valid) return 0
        }
    }

    return id
}

fun day2_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input2.txt").inputStream()

    val gameList = inputStream.bufferedReader().readLines()

    val sum = gameList.sumOf { game ->
        val parts = game.split(": ")
        val picks = parts.last().split("; ")

        var blue = 0
        var green = 0
        var red = 0
        picks.forEach {
            val colors = it.split(", ")
            colors.forEach { colorString ->
                val colorParts = colorString.split(" ")
                val count = colorParts.first().toInt()
                when (colorParts.last()) {
                    "blue" -> if (count > blue) blue = count
                    "green" -> if (count > green) green = count
                    else -> if (count > red) red = count
                }
            }
        }

        blue * green * red
    }

    println(sum)
}
