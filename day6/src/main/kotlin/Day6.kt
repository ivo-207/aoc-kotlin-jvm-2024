private val labMapMask = LabMapMask(readInput())

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    return labMapMask.getPatrolIndicesSize()
}

fun part2(): Any {
    return labMapMask.getPatrolLoopCount()
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day6.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}
