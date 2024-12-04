import kotlin.text.Charsets.US_ASCII

private val table = readInput("Day4.input", ::toTable)

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    var sum = 0
    for (y in table.indices) {
        for (x in table[y].indices) {
            if (isLetter(table, 'X', x, y)) {
                if (isLetter(table, 'M', x + 1, y) &&
                    isLetter(table, 'A', x + 2, y) &&
                    isLetter(table, 'S', x + 3, y)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x + 1, y + 1) &&
                    isLetter(table, 'A', x + 2, y + 2) &&
                    isLetter(table, 'S', x + 3, y + 3)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x, y + 1) &&
                    isLetter(table, 'A', x, y + 2) &&
                    isLetter(table, 'S', x, y + 3)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x - 1, y + 1) &&
                    isLetter(table, 'A', x - 2, y + 2) &&
                    isLetter(table, 'S', x - 3, y + 3)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x - 1, y) &&
                    isLetter(table, 'A', x - 2, y) &&
                    isLetter(table, 'S', x - 3, y)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x - 1, y - 1) &&
                    isLetter(table, 'A', x - 2, y - 2) &&
                    isLetter(table, 'S', x - 3, y - 3)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x, y - 1) &&
                    isLetter(table, 'A', x, y - 2) &&
                    isLetter(table, 'S', x, y - 3)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x + 1, y - 1) &&
                    isLetter(table, 'A', x + 2, y - 2) &&
                    isLetter(table, 'S', x + 3, y - 3)
                ) {
                    sum += 1
                }
            }
        }
    }
    return sum
}

fun part2(): Any {
    var sum = 0
    for (y in table.indices) {
        for (x in table[y].indices) {
            if (isLetter(table, 'A', x, y)) {
                if (isLetter(table, 'M', x - 1, y - 1) &&
                    isLetter(table, 'M', x + 1, y - 1) &&
                    isLetter(table, 'S', x - 1, y + 1) &&
                    isLetter(table, 'S', x + 1, y + 1)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x + 1, y - 1) &&
                    isLetter(table, 'M', x + 1, y + 1) &&
                    isLetter(table, 'S', x - 1, y - 1) &&
                    isLetter(table, 'S', x - 1, y + 1)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x - 1, y + 1) &&
                    isLetter(table, 'M', x + 1, y + 1) &&
                    isLetter(table, 'S', x - 1, y - 1) &&
                    isLetter(table, 'S', x + 1, y - 1)
                ) {
                    sum += 1
                }
                if (isLetter(table, 'M', x - 1, y - 1) &&
                    isLetter(table, 'M', x - 1, y + 1) &&
                    isLetter(table, 'S', x + 1, y - 1) &&
                    isLetter(table, 'S', x + 1, y + 1)
                ) {
                    sum += 1
                }
            }
        }
    }
    return sum
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

private fun toTable(lines: Sequence<String>): Array<CharArray> {
    return lines
        .map { it.toCharArray() }
        .toList()
        .toTypedArray()
}

private fun isLetter(table: Array<CharArray>, letter: Char, x: Int, y: Int): Boolean {
    return table.getOrNull(y)?.getOrNull(x).let { it == letter }
}