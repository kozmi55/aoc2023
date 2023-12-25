import java.io.File
import java.io.InputStream

fun day5_1() {
    val inputStream: InputStream = File("${INPUT_PATH}input5.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val seeds = lineList.first().split(": ")
        .last()
        .split(" ")
        .map { it.toLong() }
        .toMutableSet()

    val resultSet = mutableSetOf<Long>()

    println(seeds)

    lineList.drop(2).forEach { line ->
        if (line.contains("map")) {
            seeds.addAll(resultSet)
            resultSet.clear()
        } else if (line.isNotEmpty()) {
            val (destination, source, length) = line.split(" ").map { it.toLong() }
            val range = LongRange(source, source + length - 1)
            val modifier = source - destination

            val iterator = seeds.iterator()
            while (iterator.hasNext()) {
                val seed = iterator.next()
                if (range.contains(seed)) {
                    val newValue = seed - modifier
                    iterator.remove()
                    resultSet.add(newValue)
                }
            }
        }
    }

    seeds.addAll(resultSet)
    val lowestLocation = seeds.min()

    println(lowestLocation)
}

fun day5_2() {
    val inputStream: InputStream = File("${INPUT_PATH}input5.txt").inputStream()

    val lineList = inputStream.bufferedReader().readLines()

    val seedsInput = lineList.first().split(": ")
        .last()
        .split(" ")
        .map { it.toLong() }
        .toMutableList()

    val seedRanges = mutableSetOf<LongRange>()
    for (i in 0 until seedsInput.size step 2) {
        val startIndex = seedsInput[i]
        val endIndex = startIndex + seedsInput[i + 1]
        seedRanges.add(LongRange(startIndex, endIndex - 1))
    }

    val mappedSets = mutableSetOf<LongRange>()
    val remainingSets = mutableSetOf<LongRange>()

    lineList.drop(2).forEachIndexed { _, line ->

        if (line.contains("map")) {
            // start mapping
            seedRanges.addAll(mappedSets)
            mappedSets.clear()
        } else if (line.isNotEmpty()) {
            // Numbers
            remainingSets.clear()
            val (destination, source, length) = line.split(" ").map { it.toLong() }
            val range = LongRange(source, source + length - 1)
            val modifier = source - destination

            val iterator = seedRanges.iterator()
            while (iterator.hasNext()) {
                val seedRange = iterator.next()
                val seedRangeStart = seedRange.first
                val seedRangeEnd = seedRange.last

                if (seedRangeStart >= range.first && seedRangeEnd <= range.last) {
                    // Map the whole range
                    val newRange = LongRange(seedRangeStart - modifier, seedRangeEnd - modifier)
                    iterator.remove()
                    mappedSets.add(newRange)
                } else if (seedRangeStart <= range.first && seedRangeEnd >= range.last) {
                    val newMappedRange = LongRange(range.first - modifier, range.last - modifier)
                    iterator.remove()
                    mappedSets.add(newMappedRange)

                    val firstSplit = LongRange(seedRangeStart, range.first - 1)
                    val secondSplit = LongRange(range.last + 1, seedRangeEnd)
                    remainingSets.add(firstSplit)
                    remainingSets.add(secondSplit)
                } else if (seedRangeStart >= range.first && seedRangeStart <= range.last) {
                    val newMappedRange = LongRange(seedRangeStart - modifier, range.last - modifier)
                    iterator.remove()
                    mappedSets.add(newMappedRange)

                    val secondSplit = LongRange(range.last + 1, seedRangeEnd)
                    remainingSets.add(secondSplit)
                } else if (seedRangeEnd <= range.last && seedRangeEnd >= range.first) {
                    val newMappedRange = LongRange(range.first - modifier, seedRangeEnd - modifier)
                    iterator.remove()
                    mappedSets.add(newMappedRange)

                    val firstSplit = LongRange(seedRangeStart, range.first - 1)
                    remainingSets.add(firstSplit)
                }
            }

            seedRanges.addAll(remainingSets)
        }
    }

    seedRanges.addAll(mappedSets)
    val lowestLocation = seedRanges.minOf { it.first }

    println(lowestLocation)
}