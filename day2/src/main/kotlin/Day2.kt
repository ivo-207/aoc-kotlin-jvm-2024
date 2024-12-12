private val reports = readReports()

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

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day2.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun readReports(): Array<IntArray> {
    return readInput()
        .map { it -> it.split(" ").map { it.toInt() }.toIntArray() }
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
