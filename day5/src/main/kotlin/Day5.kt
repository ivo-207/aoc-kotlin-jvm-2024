private val pageOrderingRuleRegex = "^(\\d+)\\|(\\d+)$".toRegex()
private val updateRegex = "^(\\d+)(?:,(\\d+))+$".toRegex()

private val comparator = readInput()
    .mapNotNull { pageOrderingRuleRegex.find(it) }
    .map {
        val predecessor = it.groups[1]!!.value.toInt()
        val successor = it.groups[2]!!.value.toInt()
        Comparator<Int> { a, b ->
            if (a == predecessor && b == successor) return@Comparator -1
            if (a == successor && b == predecessor) return@Comparator 1
            return@Comparator 0
        }
    }
    .reduce(Comparator<Int>::thenComparing)

private val updatesAndCorrectlyOrderedUpdates = readInput()
    .mapNotNull { updateRegex.find(it) }
    .map { it.value.split(",").map { it.toInt() }.toIntArray() }
    .map { arrayOf(it, it.correctlyOrdered()) }
    .toTypedArray()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    return updatesAndCorrectlyOrderedUpdates
        .filter { it[0].contentEquals(it[1]) }
        .sumOf { it[0].middle() }
}

fun part2(): Any {
    return updatesAndCorrectlyOrderedUpdates
        .filterNot { it[0].contentEquals(it[1]) }
        .sumOf { it[1].middle() }
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day5.input")
        ?.readText()
        ?.split("\n")
        ?: emptyList()
}

private fun IntArray.middle(): Int {
    return this[size / 2]
}

private fun IntArray.correctlyOrdered(): IntArray {
    return sortedWith(comparator).toIntArray()
}
