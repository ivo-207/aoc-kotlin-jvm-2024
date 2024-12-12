private const val OPERATOR_0_MASK: Long = 1 shl 0
private const val OPERATOR_1_MASK: Long = 1 shl 1
private const val OPERATOR_2_MASK: Long = 1 shl 2

private const val PART_1_MASK = OPERATOR_0_MASK or OPERATOR_1_MASK
private const val PART_1_MASK_NEG = PART_1_MASK.inv()
private const val PART_2_MASK = OPERATOR_0_MASK or OPERATOR_1_MASK or OPERATOR_2_MASK

private const val TEST_VALUE_INDEX = 0
private const val NUMBERS_START_INDEX = 1

private val answers = getAnswers(readInput())

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    var acc: Long = 0
    var i = 0
    while (i < answers.size) {
        acc += if (answers[i].isPart1Answer()) answers[i + 1] else 0
        i += 2
    }
    return acc
}

fun part2(): Any {
    var acc: Long = 0
    var i = 0
    while (i < answers.size) {
        acc += if (answers[i].isPart2Answer()) answers[i + 1] else 0
        i += 2
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

private fun getAnswers(input: List<String>): LongArray {
    val answers = LongArray(input.size * 2)
    var i = 0
    while (i < input.size) {
        val line = input[i]
        fillAnswers(answers, line, i)
        i += 1
    }
    return answers
}

private fun fillAnswers(answers: LongArray, line: String, lineIndex: Int) {
    val equation = line.split(": ", " ").map { it.toLong() }.toLongArray()
    val testValue = equation[TEST_VALUE_INDEX]
    val answerMask = getAnswerMask(equation, NUMBERS_START_INDEX + 1, equation.size - 1)
    answers[lineIndex * 2] = answerMask.toLong()
    answers[lineIndex * 2 + 1] = testValue
}

private fun getAnswerMask(equation: LongArray, numberIndex: Int, numerLastIndex: Int): Int {
    val testValue = equation[TEST_VALUE_INDEX]
    val previousAcc = equation[numberIndex - 1]
    val number = equation[numberIndex]
    var operatorIndex = 0
    while (operatorIndex < 3) {
        val operatorMask = 1 shl operatorIndex
        val acc = runOperator(operatorIndex, previousAcc, number)
        if (numberIndex != numerLastIndex) {
            if (acc > testValue) {
                return 0
            }
            equation[numberIndex] = acc
            val checkResult = getAnswerMask(equation, numberIndex + 1, numerLastIndex)
            equation[numberIndex] = number
            if (checkResult != 0) {
                return checkResult or operatorMask
            }
        } else if (acc == testValue) {
            return operatorMask
        }
        operatorIndex += 1
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
            acc += second
            acc
        }

        else -> 0
    }
}

private fun Long.isPart1Answer(): Boolean {
    return this and PART_1_MASK != 0L && this and PART_1_MASK_NEG == 0L
}

private fun Long.isPart2Answer(): Boolean {
    return this and PART_2_MASK != 0L
}