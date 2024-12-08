private val labMap = readInput()
    .map { it.toCharArray() }
    .toTypedArray()

private val guardPosition = getGuardPosition(labMap)

private val distinctPositions = getGuardPath(labMap, guardPosition) { it.isObstacle() }
    .map { it.first }
    .distinct()
    .toList()
    .toTypedArray()

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    return distinctPositions.count()

}

fun part2(): Any {
    return distinctPositions.drop(1)
        .count {
            val labMap = labMap.clone(it)
            val visited = mutableSetOf<Pair<Position, Direction>>()
            getGuardPath(labMap, guardPosition) { it.isObstacle() || it.isObstruction() }
                .any { !visited.add(it) }
        }
}

private fun getGuardPath(
    labMap: Array<CharArray>,
    guard: Position,
    turnPredicate: (Char) -> Boolean
): Sequence<Pair<Position, Direction>> {
    return generateSequence(guard to Direction.UP) { (position, direction) ->
        val nextPosition = position.next(direction)
        val makeTurn = labMap.getOrNull(nextPosition)?.let(turnPredicate) ?: false
        if (makeTurn) position to direction.turnRight() else nextPosition to direction
    }.takeWhile { labMap.getOrNull(it.first) != null }
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day6.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun Char.isObstacle(): Boolean {
    return this == '#'
}

private fun Char.isObstruction(): Boolean {
    return this == 'O'
}

private fun Char.isGuard(): Boolean {
    return this == '^'
}

private fun Array<CharArray>.getOrNull(position: Position): Char? {
    return getOrNull(position.y)?.getOrNull(position.x)
}

private fun Array<CharArray>.clone(obstruction: Position): Array<CharArray> {
    return Array(size) { get(it).clone() }
        .also { it[obstruction.y][obstruction.x] = 'O' }
}

private fun getGuardPosition(labMap: Array<CharArray>): Position {
    return labMap.flatMapIndexed { y, row ->
        row.flatMapIndexed { x, c -> if (c.isGuard()) listOf(Position(x, y)) else listOf() }
    }.single()
}
