import kotlin.math.pow

private val equations = readInput()
    .map {
        val testValueAndNumbers = it.split(": ")
        val testValue = testValueAndNumbers[0].toLong()
        val numbers = testValueAndNumbers[1].split(" ").map { it.toLong() }.toLongArray()
        longArrayOf(testValue, *numbers)
    }
    .toTypedArray()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    var acc: Long = 0
    var i = 0
    while (i < equations.size) {
        acc += getCalibrationResult(equations[i], 2)
        i += 1
    }
    return acc
}

fun part2(): Any {
    var acc: Long = 0
    var i = 0
    while (i < equations.size) {
        acc += getCalibrationResult(equations[i], 3)
        i += 1
    }
    return acc
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day7.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun Int.pow(n: Int): Int {
    return this.toDouble().pow(n).toInt()
}

private fun getCalibrationResult(numbers: LongArray, operatorsSize: Int): Long {
    val operatorsGroupsSize = operatorsSize.pow(numbers.size - 2)
    val testValue = numbers[0]
    var operatorsGroup = 0
    while (operatorsGroup < operatorsGroupsSize) {
        var operatorsMask = operatorsGroup
        var acc = numbers[1]
        var i = 2
        acc@ while (i < numbers.size) {
            acc = runOperator(operatorsMask % operatorsSize, acc, numbers[i])
            if (acc > testValue) {
                break@acc
            }
            operatorsMask /= operatorsSize
            i += 1
        }
        if (acc == testValue) {
            return testValue
        }
        operatorsGroup += 1
    }
    return 0
}

private fun runOperator(operator: Int, first: Long, second: Long): Long {
    return when (operator) {
        0 -> first + second
        1 -> first * second
        2 -> {
            var acc = first
            var dividend = second
            do {
                dividend /= 10
                acc *= 10
            } while (dividend > 0)
            acc + second
        }

        else -> 0
    }
}

