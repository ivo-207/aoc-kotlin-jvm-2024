import kotlin.math.absoluteValue

private val numbers = readNumbers()
private val columnLength = numbers.size / 2

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    return getTotalDistance(numbers, columnLength)
}

fun part2(): Any {
    return getSimilarityScore(numbers, columnLength)
}

private fun getTotalDistance(numbers: IntArray, columnLength: Int): Int {
    var totalDistance = 0
    var i = 0
    while (i < columnLength) {
        val left = numbers[i]
        val right = numbers[i + columnLength]
        val distance = (left - right).absoluteValue
        totalDistance += distance
        i += 1
    }
    return totalDistance
}

private fun getSimilarityScore(numbers: IntArray, columnLength: Int): Int {
    val leftEndIndex = columnLength
    val rightEndIndex = leftEndIndex + columnLength
    var similarityScore = 0
    var leftIndex = 0
    var rightIndex = leftEndIndex
    while (leftIndex < leftEndIndex && rightIndex < rightEndIndex) {
        val left = numbers[leftIndex]
        val leftCount = countEq(numbers, leftIndex, leftEndIndex, left)
        leftIndex += leftCount
        rightIndex += countLt(numbers, rightIndex, rightEndIndex, left)
        val rightCount = countEq(numbers, rightIndex, rightEndIndex, left)
        rightIndex += rightCount
        similarityScore += left * leftCount * rightCount
    }
    return similarityScore
}

private fun countEq(numbers: IntArray, startIndex: Int, endIndex: Int, value: Int): Int {
    var count = 0
    var i = startIndex
    while (i < endIndex && numbers[i] == value) {
        count += 1
        i += 1
    }
    return count
}

private fun countLt(numbers: IntArray, startIndex: Int, endIndex: Int, value: Int): Int {
    var count = 0
    var i = startIndex
    while (i < endIndex && numbers[i] < value) {
        count += 1
        i += 1
    }
    return count
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day1.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun readNumbers(): IntArray {
    val input = readInput()
    val columnSize = input.size
    val numbers = IntArray(columnSize * 2)
    var i = 0
    while (i < columnSize) {
        val tokens = input[i].split("   ")
        numbers[i] = tokens[0].toInt()
        numbers[columnSize + i] = tokens[1].toInt()
        i += 1
    }
    numbers.sort(0, columnSize)
    numbers.sort(columnSize)
    return numbers
}
