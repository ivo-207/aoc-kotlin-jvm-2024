import kotlin.text.Charsets.US_ASCII

private val instructions = readInput("Day3.input", ::toInstructions)

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
            is Mul -> if (enabled) sum += instruction.product
        }
    }
    return sum
}

private fun <T> readInput(name: String, transform: (Sequence<String>) -> T): T {
    val bufferSize = 1024 * 1024
    return object {}.javaClass.getResourceAsStream(name).use { it ->
        it?.reader(US_ASCII).use { it ->
            it?.buffered(bufferSize).use {
                val lines = it?.lineSequence() ?: emptySequence()
                transform(lines)
            }
        }
    }
}

private fun toInstructions(lines: Sequence<String>): Array<Instruction> {
    val doAndDontPattern = "do(?:n't)?\\(\\)"
    val mulPattern = "mul\\((\\d{1,3}),(\\d{1,3})\\)"
    val regex = "${doAndDontPattern}|${mulPattern}".toRegex()
    return lines
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