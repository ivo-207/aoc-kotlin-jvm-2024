import kotlin.math.abs
import kotlin.text.Charsets.US_ASCII

private val numbers = readInput("Day1.input", ::toIntArray)

private val leftList = IntArray(numbers.size / 2) { numbers[it * 2] }
    .sortedArray()

private val rightList = IntArray(numbers.size / 2) { numbers[it * 2 + 1] }
    .sortedArray()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    return (0..<numbers.size / 2)
        .sumOf { abs(leftList[it] - rightList[it]) }
}

fun part2(): Any {
    return (0..<numbers.size / 2)
        .sumOf {
            val firstValue = leftList[it]
            val score = rightList.count { it == firstValue }
            firstValue * score
        }
}


private fun <T> readInput(name: String, transform: (Sequence<String>) -> T): T {
    return object {}.javaClass.getResourceAsStream(name)
        .use { it ->
            it?.bufferedReader(US_ASCII)
                .use {
                    val sequence = it?.lineSequence() ?: emptySequence()
                    transform(sequence)
                }
        }
}

private fun toIntArray(lines: Sequence<String>): IntArray {
    val regex = "\\s+".toRegex()
    return lines
        .flatMap { it.splitToSequence(regex) }
        .map { it.toInt() }
        .toList()
        .toIntArray()
}
