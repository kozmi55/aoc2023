import java.io.File
import java.io.InputStream
import kotlin.math.min

fun day7_1() {
    calculateHandWinnings(11) { it.type }
}

fun day7_2() {
    calculateHandWinnings(1) { it.typePart2 }
}

private fun calculateHandWinnings(jValue: Int, typeCalculation: (Hand) -> HandType) {
    val inputStream: InputStream = File("${INPUT_PATH}input7.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val hands = lineList.map { line ->
        val parts = line.split(" ")
        val cards = parts.first().toCharArray().map {
            if (it.isDigit()) {
                it.digitToInt()
            } else {
                when (it.toString()) {
                    "A" -> 14
                    "K" -> 13
                    "Q" -> 12
                    "J" -> jValue
                    else -> 10
                }
            }
        }

        Hand(parts.first(), cards, parts.last().toInt())
    }

    val sortedHands = hands.sortedWith { o1, o2 ->
        val handTypeCompare = typeCalculation(o2).compareTo(typeCalculation(o1))
        if (handTypeCompare != 0) {
            handTypeCompare
        } else {
            compareLists(o1.cards, o2.cards)
        }
    }

    var sum = 0
    sortedHands.forEachIndexed { index, card ->
        sum += (index + 1) * card.bid
    }

    println(sum)
}

data class Hand(val handString: String, val cards: List<Int>, val bid: Int) {
    val type: HandType
        get() {
            val mapOfCards = cards.associateWith { cards.count { card -> card == it } }

            return when {
                mapOfCards.containsValue(5) -> HandType.FIVE
                mapOfCards.containsValue(4) -> HandType.FOUR
                mapOfCards.containsValue(3) && mapOfCards.containsValue(2) -> HandType.FULL_HOUSE
                mapOfCards.containsValue(3) -> HandType.DRILL
                mapOfCards.values.count { it == 2 } == 2 -> HandType.TWO_PAIR
                mapOfCards.containsValue(2) -> HandType.ONE_PAIR
                else -> HandType.HIGH_HAND
            }
        }

    val typePart2: HandType
        get() {
            val mapOfCards = cards.associateWith { cards.count { card -> card == it } }.toMutableMap()

            val jokers = mapOfCards[1] ?: 0
            if (jokers == 5) return HandType.FIVE

            mapOfCards.remove(1)
            val mostCountKey = mapOfCards.toList().maxByOrNull { it.second }!!.first

            mapOfCards.compute(mostCountKey) { _, value -> value!! + jokers }

            return when {
                mapOfCards.containsValue(5) -> HandType.FIVE
                mapOfCards.containsValue(4) -> HandType.FOUR
                mapOfCards.containsValue(3) && mapOfCards.containsValue(2) -> HandType.FULL_HOUSE
                mapOfCards.containsValue(3) -> HandType.DRILL
                mapOfCards.values.count { it == 2 } == 2 -> HandType.TWO_PAIR
                mapOfCards.containsValue(2) -> HandType.ONE_PAIR
                else -> HandType.HIGH_HAND
            }
        }
}

enum class HandType {
    FIVE,
    FOUR,
    FULL_HOUSE,
    DRILL,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_HAND
}

fun compareLists(list1: List<Comparable<*>>, list2: List<Comparable<*>>): Int {
    for (i in 0 until min(list1.size, list2.size)) {
        val elem1 = list1[i]
        val elem2 = list2[i]

        compareValues(elem1, elem2).let {
            if (it != 0) return it
        }
    }
    return compareValues(list1.size, list2.size)
}