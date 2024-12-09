private val equations = readInput()
    .map {
        val testValueAndNumbers = it.split(": ")
        val testValue = testValueAndNumbers[0].toLong()
        val numbers = testValueAndNumbers[1].split(" ").map { it.toLong() }.toLongArray()
        Equation(testValue, numbers)
    }
    .toTypedArray()

private val operatorsGroups = Array(equations.maxOf { it.numbers.size }) {
    val operators = arrayOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCATENATION)
    crossProduct(*operators, power = it)
}

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    val operatorsGroups = operatorsGroups
        .map { it.filterNot { it.contains(Operator.CONCATENATION) }.toTypedArray() }
        .toTypedArray()
    return getTotalCalibrationResult(*operatorsGroups)
}

fun part2(): Any {
    return getTotalCalibrationResult(*operatorsGroups)
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day7.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun getTotalCalibrationResult(vararg operatorsGroups: Array<Array<Operator>>): Long {
    return equations
        .filter { equation ->
            val operatorsGroupSize = equation.numbers.size - 1
            val operatorsGroup = operatorsGroups[operatorsGroupSize]
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
        { product, _ -> product.flatMap { el -> operators.map { t -> el + t } }.toTypedArray() }
}
