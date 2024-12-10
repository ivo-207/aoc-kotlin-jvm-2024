private const val DIRECTION_UP = 1 shl 0
private const val DIRECTION_RIGHT = 1 shl 1
private const val DIRECTION_DOWN = 1 shl 2
private const val DIRECTION_LEFT = 1 shl 3
private const val GUARD = 1 shl 4
private const val OBSTACLE = 1 shl 5
private const val OBSTRUCTION = 1 shl 6
private const val EDGE = 1 shl 7

private val labTileMap = getLabTileMap()
private val guardIndex = getGuardIndex(labTileMap.first)

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    val (labTileMap, labTileWidth, _) = labTileMap
    var i = guardIndex
    var direction = DIRECTION_UP
    while (true) {
        val tile = labTileMap[i]
        if (tile and EDGE != 0) {
            break
        }
        labTileMap[i] = tile or direction
        val offset = when (direction) {
            DIRECTION_UP -> -labTileWidth
            DIRECTION_RIGHT -> 1
            DIRECTION_DOWN -> labTileWidth
            DIRECTION_LEFT -> -1
            else -> 0
        }
        val nextTile = labTileMap[i + offset]
        if (nextTile and OBSTACLE != 0 || nextTile and OBSTRUCTION != 0) {
            direction = when (direction) {
                DIRECTION_UP -> DIRECTION_RIGHT
                DIRECTION_RIGHT -> DIRECTION_DOWN
                DIRECTION_DOWN -> DIRECTION_LEFT
                DIRECTION_LEFT -> DIRECTION_UP
                else -> 0
            }
            continue
        }
        i += offset
    }
    val result = labTileMap.count {
        val visitedMask = DIRECTION_UP or DIRECTION_LEFT or DIRECTION_DOWN or DIRECTION_RIGHT
        it and visitedMask != 0
    }
    return result
}

fun part2(): Any {
    val (labTileMap, labTileWidth, _) = labTileMap
    val visitedIndices = labTileMap.indices
        .filter {
            val visitedMask = DIRECTION_UP or DIRECTION_LEFT or DIRECTION_DOWN or DIRECTION_RIGHT
            labTileMap[it] and visitedMask != 0
        }
        .drop(1)
        .toIntArray()
    val result = visitedIndices.count { obstructionIndex ->
        labTileMap.indices
            .forEach {
                labTileMap[it] =
                    labTileMap[it] and (DIRECTION_UP or DIRECTION_LEFT or DIRECTION_DOWN or DIRECTION_RIGHT or OBSTRUCTION).inv()
            }
        labTileMap[obstructionIndex] = labTileMap[obstructionIndex] or OBSTRUCTION
        var i = guardIndex
        var direction = DIRECTION_UP
        while (true) {
            val tile = labTileMap[i]
            if (tile and EDGE != 0) {
                break
            }
            if (tile and direction != 0) {
                return@count true
            }
            labTileMap[i] = tile or direction
            val offset = when (direction) {
                DIRECTION_UP -> -labTileWidth
                DIRECTION_RIGHT -> 1
                DIRECTION_DOWN -> labTileWidth
                DIRECTION_LEFT -> -1
                else -> 0
            }
            val nextTile = labTileMap[i + offset]
            if (nextTile and OBSTACLE != 0 || nextTile and OBSTRUCTION != 0) {
                direction = when (direction) {
                    DIRECTION_UP -> DIRECTION_RIGHT
                    DIRECTION_RIGHT -> DIRECTION_DOWN
                    DIRECTION_DOWN -> DIRECTION_LEFT
                    DIRECTION_LEFT -> DIRECTION_UP
                    else -> 0
                }
                continue
            }
            i += offset
        }
        false
    }
    return result
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day6.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun Char.isGuard(): Boolean {
    return this == '^'
}

private fun Char.isObstacle(): Boolean {
    return this == '#'
}

private fun getLabTileMap(): Triple<IntArray, Int, Int> {
    val input = readInput()
        .map { it.toCharArray() }
        .toTypedArray()
    val inputWidth = input.map { it.size }
        .distinct()
        .single()
    val inputHeight = input.size
    val labTileWidth = inputWidth + 2
    val labTileHeight = inputHeight + 2
    val labTileMap = IntArray(labTileWidth * labTileHeight) {
        val x = it % labTileWidth
        val y = it / labTileWidth
        if (x == 0 || x == labTileWidth - 1 || y == 0 || y == labTileHeight - 1) {
            EDGE
        } else {
            val tile = input[y - 1][x - 1]
            when {
                tile.isGuard() -> GUARD
                tile.isObstacle() -> OBSTACLE
                else -> 0
            }
        }
    }
    return Triple(labTileMap, labTileWidth, labTileHeight)
}

private fun getGuardIndex(labTileMap: IntArray): Int {
    return labTileMap.indices
        .single { labTileMap[it] and GUARD != 0 }
}
