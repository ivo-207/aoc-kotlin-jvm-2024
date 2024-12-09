private val equations = readInput()
    .map {
        val testValueAndNumbers = it.split(": ")
        val testValue = testValueAndNumbers[0].toLong()
        val numbers = testValueAndNumbers[1].split(" ").map { it.toLong() }.toLongArray()
        Equation(testValue, numbers)
    }
    .toTypedArray()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    val operators = arrayOf(Operator.ADD, Operator.MULTIPLY)
    return getTotalCalibrationResult(*operators)
}

fun part2(): Any {
    val operators = arrayOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCATENATION)
    return getTotalCalibrationResult(*operators)
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day7.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun getTotalCalibrationResult(vararg operators: Operator): Long {
    val operatorsGroupCache = mutableMapOf<Int, Array<Array<Operator>>>()
    return equations
        .filter { equation ->
            val operatorsGroupSize = equation.numbers.size - 1
            val operatorsGroup = operatorsGroupCache.getOrPut(operatorsGroupSize)
            { crossProduct(*operators, power = operatorsGroupSize) }
            operatorsGroup.any { equation.testEvaluation(*it) }
        }
        .sumOf { it.testValue }
}

private fun crossProduct(vararg operators: Operator, power: Int): Array<Array<Operator>> {
    if (power < 0) {
        return emptyArray()
    }
    return (0..<power)
        .fold(arrayOf(emptyArray<Operator>()))
        { product, index -> product.flatMap { el -> operators.map { t -> el + t } }.toTypedArray() }
}
