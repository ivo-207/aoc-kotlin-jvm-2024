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
    val disk = diskMap
        .mapIndexed { index, space -> IntArray(space) { if (index % 2 == 0) index / 2 else -1 }.toTypedArray() }
        .toTypedArray()
        .flatten()
        .toIntArray()
    var startIndex = 0
    var endIndex = disk.size
    while (startIndex < endIndex) {
        val freeSpaceBlockIndex = disk.indexOfFirstBlock(startIndex, endIndex, -1)
        if (freeSpaceBlockIndex > 0) {
            val freeSpaceBlockSize = disk.sizeOfBlock(freeSpaceBlockIndex, endIndex)
            val fileBlockIndex = disk.startIndexOfFileBlock(freeSpaceBlockIndex + freeSpaceBlockSize)
            if (fileBlockIndex > 0) {
                val fileBlockSize = disk.sizeOfBlock(fileBlockIndex, endIndex)
                disk.copyInto(disk, freeSpaceBlockIndex, fileBlockIndex, fileBlockIndex + fileBlockSize)
                disk.fill(-1, fileBlockIndex, fileBlockIndex + fileBlockSize)
                endIndex -= fileBlockSize
            }
            startIndex = freeSpaceBlockIndex
        }
        startIndex += 1
    }
    return Unit
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day9.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun IntArray.indexOfFirstBlock(startIndex: Int, endIndex: Int, id: Int): Int {
    return IntRange(startIndex, endIndex - 1)
        .firstOrNull { this[it] == id }
        ?: -1
}

private fun IntArray.startIndexOfFileBlock(startIndex: Int): Int {
    return IntRange(startIndex, lastIndex)
        .reversed()
        .dropWhile { this[it] < 0 }
        .takeWhile { this[it] >= 0 }
        .maxWithOrNull(compareBy<Int> { this[it] }.thenByDescending { it })
        ?: -1
}


private fun IntArray.indexOfLastFileBlock(startIndex: Int, endIndex: Int): Int {
    val fileBlockEndIndex = IntRange(startIndex, endIndex - 1)
        .reversed()
        .firstOrNull { this[it] > -1 }
        ?: -1
    return IntRange(startIndex, endIndex - 1)
        .reversed()
        .dropWhile { this[it] < 0 }
        .takeWhile { this[it] >= 0 }
        .maxWithOrNull(compareBy<Int> { this[it] }.thenByDescending { it })
        ?: -1
}

private fun IntArray.sizeOfBlock(startIndex: Int, endIndex: Int): Int {
    val id = this[startIndex]
    return IntRange(startIndex, endIndex - 1)
        .takeWhile { this[it] == id }
        .size
}
