private val diskMap = readInput()
    .flatMap { it.toCharArray().map { it.digitToInt() } }
    .toIntArray()


fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    val disk = diskMap
        .mapIndexed { index, space -> IntArray(space) { if (index % 2 == 0) index / 2 else -1 }.toTypedArray() }
        .toTypedArray()
        .flatten()
        .toIntArray()
    val fileBlocks = disk.withIndex()
        .filter { (_, id) -> id != -1 }
        .map { (index, _) -> index }
        .toIntArray()
        .reversedArray()
    val freeSpaceBlocks = disk.withIndex()
        .filter { (_, id) -> id == -1 }
        .map { (index, _) -> index }
        .takeWhile { it < fileBlocks.size }
        .toIntArray()
    (freeSpaceBlocks zip fileBlocks)
        .forEach { (freeSpaceBlock, fileBlock) ->
            disk[freeSpaceBlock] = disk[fileBlock]
            disk[fileBlock] = -1
        }
    val result = disk
        .mapIndexed { index, id -> if (id != -1) index * id else 0 }
        .sumOf { it.toLong() }
    return result
}

fun part2(): Any {
    return Unit
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day9.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}
