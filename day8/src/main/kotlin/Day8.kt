private val antenaMap = readInput()
    .map { it.toCharArray() }
    .toTypedArray()

private val chars = arrayOf(('0'..'9'), ('A'..'Z'), ('a'..'z'))
    .flatMap { it.toList() }
    .toCharArray()

private val frequencies = Array(chars.size) {
    val frequency = chars[it]
    antenaMap.flatMapIndexed { y, row ->
        row.flatMapIndexed { x, c ->
            if (c == frequency) listOf(Position(x, y))
            else emptyList()
        }
    }.toTypedArray()
}

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    val xRange = 0..<antenaMap.maxOf { it.size }
    val yRange = 0..<antenaMap.size
    val antiNodes = frequencies
        .flatMap { positions ->
            positions.flatMap { start -> positions.map { end -> Pair(start, end) } }
                .filter { (start, end) -> start != end }
                .flatMap { (start, end) ->
                    val (xDiff, yDiff) = end.x - start.x to end.y - start.y
                    listOf(Position(end.x + xDiff, end.y + yDiff), Position(start.x - xDiff, start.y - yDiff))
                }
        }
    return antiNodes
        .filter { (x, y) -> x in xRange && y in yRange }
        .distinct()
        .count()
}

fun part2(): Any {
    val xRange = 0..<antenaMap.maxOf { it.size }
    val yRange = 0..<antenaMap.size
    val antiNodes = frequencies
        .flatMap { positions ->
            positions.flatMap { start -> positions.map { end -> Pair(start, end) } }
                .filter { (start, end) -> start != end }
                .flatMap { (start, end) ->
                    val (xDiff, yDiff) = end.x - start.x to end.y - start.y
                    val first = generateSequence(start) { Position(it.x - xDiff, it.y - yDiff) }
                        .takeWhile { (x, y) -> x in xRange && y in yRange }
                    val second = generateSequence(end) { Position(it.x + xDiff, it.y + yDiff) }
                        .takeWhile { (x, y) -> x in xRange && y in yRange }
                    emptySequence<Position>() + first + second
                }
        }
    return antiNodes
        .distinct()
        .count()
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day8.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}
