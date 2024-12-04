private val instructions = readInstructions()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    var sum = 0
    for (instruction in instructions) {
        if (instruction is Mul) {
            sum += instruction.product
        }
    }
    return sum
}

fun part2(): Any {
    var sum = 0
    var enabled = true
    for (instruction in instructions) {
        when (instruction) {
            is Do -> enabled = true
            is Dont -> enabled = false
            is Mul -> sum += if (enabled) instruction.product else 0
        }
    }
    return sum
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day3.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun readInstructions(): Array<Instruction> {
    val doAndDontPattern = "do(?:n't)?\\(\\)"
    val mulPattern = "mul\\((\\d{1,3}),(\\d{1,3})\\)"
    val regex = "${doAndDontPattern}|${mulPattern}".toRegex()
    return readInput()
        .flatMap { regex.findAll(it) }
        .map {
            when (it.value) {
                "do()" -> Do
                "don't()" -> Dont
                else -> Mul(it)
            }
        }
        .toList()
        .toTypedArray()
}
