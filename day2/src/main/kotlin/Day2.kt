import kotlin.text.Charsets.US_ASCII

private val reports = readInput("Day2.input", ::toReport)

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    return reports
        .count { isSafe(it) }
}

fun part2(): Any {
    return reports
        .count { isSafe(it) || removeLevels(it).any { isSafe(it) } }
}

private fun <T> readInput(name: String, transform: (Sequence<String>) -> T): T {
    val bufferSize = 1024 * 1024
    return object {}.javaClass.getResourceAsStream(name).use { inputStream ->
        inputStream?.reader(US_ASCII).use { reader ->
            reader?.buffered(bufferSize).use {
                val lines = it?.lineSequence() ?: emptySequence()
                transform(lines)
            }
        }
    }
}

private fun toReport(lines: Sequence<String>): Array<IntArray> {
    return lines
        .map { it.splitToSequence(" ") }
        .map { it -> it.map { it.toInt() } }
        .map { it.toList() }
        .map { it.toIntArray() }
        .toList()
        .toTypedArray()
}

private fun isSafe(report: IntArray): Boolean {
    val diffs = report.toList().windowed(2).map { it[1] - it[0] }.toIntArray()
    return diffs.all { it in -3..-1 } || diffs.all { it in 1..3 }
}

private fun removeLevels(report: IntArray): Array<IntArray> {
    return report.indices
        .map { indexToSkip -> report.filterIndexed { index, _ -> index != indexToSkip }.toIntArray() }
        .toTypedArray()
}