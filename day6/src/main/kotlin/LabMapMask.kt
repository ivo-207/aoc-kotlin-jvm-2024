private const val DIRECTION_UP_MASK = 1 shl 0
private const val DIRECTION_RIGHT_MASK = 1 shl 1
private const val DIRECTION_DOWN_MASK = 1 shl 2
private const val DIRECTION_LEFT_MASK = 1 shl 3
private const val OBSTACLE_MASK = 1 shl 4
private const val OBSTRUCTION_MASK = 1 shl 5
private const val EDGE_MASK = 1 shl 6

private const val VISITED_MASK = DIRECTION_UP_MASK or DIRECTION_LEFT_MASK or DIRECTION_DOWN_MASK or DIRECTION_RIGHT_MASK
private const val MOVE_BLOCKED_MASK = OBSTACLE_MASK or OBSTRUCTION_MASK
private const val CLEAR_MASK = (VISITED_MASK or OBSTRUCTION_MASK).inv()

class LabMapMask(inputLines: List<String>) {

    private val mapMask: IntArray
    private val labWidth: Int
    private val guard: Int
    private val patrolIndices: IntArray

    init {
        val inputWidth = inputLines[0].length
        val inputHeight = inputLines.size
        val labWidth = inputWidth + 2
        val labHeight = inputHeight + 2
        val mapMask = IntArray(labWidth * labHeight)
        fillEdges(labWidth, mapMask)
        var guard = -1
        for (y in 1 until labHeight - 1) {
            for (x in 1 until labWidth - 1) {
                val tile = inputLines[y - 1][x - 1]
                val i = y * labWidth + x
                when {
                    tile.isObstacle() -> mapMask[i] = OBSTACLE_MASK
                    tile.isGuard() -> guard = i
                }
            }
        }
        val patrolIndices = computePatrolIndices(mapMask, labWidth, guard)
        this.mapMask = mapMask
        this.labWidth = labWidth
        this.guard = guard
        this.patrolIndices = patrolIndices
    }

    fun getPatrolIndicesSize(): Int {
        return patrolIndices.size
    }

    fun getPatrolLoopCount(): Int {
        return computePatrolLoopCount(mapMask, labWidth, guard, patrolIndices)
    }

}

private fun fillEdges(labWidth: Int, mapMask: IntArray) {
    for (i in 0 until labWidth) {
        mapMask[i] = EDGE_MASK
    }
    for (i in labWidth until (mapMask.size - labWidth) step labWidth) {
        mapMask[i] = EDGE_MASK
        mapMask[i + labWidth - 1] = EDGE_MASK
    }
    for (i in (mapMask.size - labWidth) until mapMask.size) {
        mapMask[i] = EDGE_MASK
    }
}

private fun computePatrolIndices(mapMask: IntArray, labWidth: Int, guard: Int): IntArray {
    runPatrol(mapMask, labWidth, guard)
    return mapMask.indices
        .filter { mapMask[it].isVisited() }
        .toIntArray()
}

private fun runPatrol(mapMask: IntArray, labWidth: Int, guard: Int): Boolean {
    var i = guard
    var direction = DIRECTION_UP_MASK
    while (true) {
        val tileMask = mapMask[i]
        if (tileMask.isEdge()) {
            break
        }
        if (tileMask.hasMask(direction)) {
            return true
        }
        mapMask[i] = tileMask.withMask(direction)
        val offset = getOffsetAfterTurn(direction, labWidth)
        i += offset
        val nextTileMask = mapMask[i]
        if (nextTileMask.isMoveBlocked()) {
            i -= offset
            direction = turn(direction)
        }
    }
    return false
}

private fun Int.hasMask(mask: Int): Boolean {
    return this and mask != 0
}

private fun Int.isEdge(): Boolean {
    return this.hasMask(EDGE_MASK)
}

private fun Int.isVisited(): Boolean {
    return this.hasMask(VISITED_MASK)
}

private fun Int.withMask(mask: Int): Int {
    return this or mask
}

private fun Int.isMoveBlocked(): Boolean {
    return this.hasMask(MOVE_BLOCKED_MASK)
}

private fun computePatrolLoopCount(mapMask: IntArray, labWidth: Int, guard: Int, patrolIndices: IntArray): Int {
    var loopCount = 0
    for (patrol in 1 until patrolIndices.size) {
        clearVisitedAndObstructionMask(mapMask)
        val obstruction = patrolIndices[patrol]
        mapMask[obstruction] = mapMask[obstruction].withMask(OBSTRUCTION_MASK)
        loopCount += if (runPatrol(mapMask, labWidth, guard)) 1 else 0
    }
    return loopCount
}

private fun clearVisitedAndObstructionMask(mapMask: IntArray) {
    mapMask.indices
        .forEach { mapMask[it] = mapMask[it] and CLEAR_MASK }
}

private fun getOffsetAfterTurn(direction: Int, labWidth: Int): Int {
    return when (direction) {
        DIRECTION_UP_MASK -> -labWidth
        DIRECTION_RIGHT_MASK -> 1
        DIRECTION_DOWN_MASK -> labWidth
        DIRECTION_LEFT_MASK -> -1
        else -> 0
    }
}

private fun turn(direction: Int): Int {
    return when (direction) {
        DIRECTION_UP_MASK -> DIRECTION_RIGHT_MASK
        DIRECTION_RIGHT_MASK -> DIRECTION_DOWN_MASK
        DIRECTION_DOWN_MASK -> DIRECTION_LEFT_MASK
        DIRECTION_LEFT_MASK -> DIRECTION_UP_MASK
        else -> 0
    }
}

private fun Char.isGuard(): Boolean {
    return this == '^'
}

private fun Char.isObstacle(): Boolean {
    return this == '#'
}
