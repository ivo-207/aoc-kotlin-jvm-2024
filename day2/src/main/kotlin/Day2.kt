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
        .count { isSafe(it) || isSafePart2(it) }
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
    val shifted = report.copyOfRange(1, report.size)
    val diffs = IntArray(shifted.size) { shifted[it] - report[it] }
    return diffs.all { it in -3..-1 } || diffs.all { it in 1..3 }
}

private fun isSafePart2(report: IntArray): Boolean {
    val newReport = report.copyOfRange(1, report.size)
    for (i in newReport.indices) {
        if (isSafe(newReport)) {
            return true
        }
        newReport[i] = report[i]
    }
    return isSafe(newReport)
}
