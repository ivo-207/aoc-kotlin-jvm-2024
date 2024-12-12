import kotlin.math.pow

private val equations = readInput()
    .map { it -> it.split(": ", " ").map { it.toLong() }.toLongArray() }
    .toTypedArray()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    var acc: Long = 0
    var i = 0
    while (i < equations.size) {
        val equation = equations[i]
        val testValue = equation[0]
        val numbers = equation.copyOfRange(1, equation.size)
        val partialResults = LongArray(numbers.size)
        val calculated = calculate(testValue, numbers, partialResults, 0, 2)
        acc += if (calculated) testValue else 0
        i += 1
    }
    return acc
}

private fun calculate(testValue: Long, numbers: LongArray, partialResults: LongArray, i: Int, operatorsSize: Int): Boolean {

    when {
        i == 0 -> {
            val number = numbers[i]
            partialResults[0] = number
            return calculate(testValue, numbers, partialResults, 1, operatorsSize)
        }

        i < numbers.size -> {
            val number = numbers[i]
            val previousResult = partialResults[i - 1]
            for (op in 0 until operatorsSize) {
                val result = runOperator(op, previousResult, number)
                partialResults[i] = result
                if (calculate(testValue, numbers, partialResults, i + 1, operatorsSize)) {
                    return true
                }
            }
        }

        i == numbers.size -> {
            return partialResults[i - 1] == testValue
        }
    }
    return false
}

fun part2(): Any {
    var acc: Long = 0
    var i = 0
    while (i < equations.size) {
        val equation = equations[i]
        val testValue = equation[0]
        val numbers = equation.copyOfRange(1, equation.size)
        val partialResults = LongArray(numbers.size)
        val calculated = calculate(testValue, numbers, partialResults, 0, 3)
        acc += if (calculated) testValue else 0
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

