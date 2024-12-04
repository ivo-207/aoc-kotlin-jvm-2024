import kotlin.math.absoluteValue

private val numbers = readNumbers()

private val leftList = IntArray(numbers.size / 2) { numbers[it * 2] }
    .sortedArray()

private val rightList = IntArray(numbers.size / 2) { numbers[it * 2 + 1] }
    .sortedArray()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    var distance = 0
    val limit = numbers.size / 2
    var i = 0
    while (i < limit) {
        distance += (leftList[i] - rightList[i]).absoluteValue
        i += 1
    }
    return distance
}

fun part2(): Any {
    var leftIndex = 0
    var rightIndex = 0
    var score = 0
    while (leftIndex < leftList.size && rightIndex < rightList.size) {
        val left = leftList[leftIndex]
        var leftCount = 1
        while (++leftIndex < leftList.size && left == leftList[leftIndex]) {
            leftCount += 1
        }
        var rightCount = 0
        while (rightIndex < rightList.size && rightList[rightIndex] <= left) {
            val right = rightList[rightIndex++]
            rightCount += if (left == right) 1 else 0
        }
        score += left * leftCount * rightCount
    }
    return score
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day1.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun readNumbers(): IntArray {
    return readInput()
        .flatMap { it.split("   ") }
        .map { it.toInt() }
        .toList()
        .toIntArray()
}
