private const val EDGE_SIZE = 3

private val lettersTable = getLettersTableWithWidth(readInput())

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Any {
    val (letters, width) = lettersTable
    val lastIndex = letters.size - width * EDGE_SIZE - EDGE_SIZE
    var acc = 0
    var i = 0
    while (i < lastIndex) {
        acc += if (letters[i] == 'X' && letters[i + 1] == 'M' && letters[i + 2] == 'A' && letters[i + 3] == 'S') 1 else 0
        acc += if (letters[i] == 'X' && letters[i + width - 1] == 'M' && letters[i + width - 1 + width - 1] == 'A' && letters[i + width - 1 + width - 1 + width - 1] == 'S') 1 else 0
        acc += if (letters[i] == 'X' && letters[i + width] == 'M' && letters[i + width + width] == 'A' && letters[i + width + width + width] == 'S') 1 else 0
        acc += if (letters[i] == 'X' && letters[i + width + 1] == 'M' && letters[i + width + 1 + width + 1] == 'A' && letters[i + width + 1 + width + 1 + width + 1] == 'S') 1 else 0
        acc += if (letters[i] == 'S' && letters[i + 1] == 'A' && letters[i + 2] == 'M' && letters[i + 3] == 'X') 1 else 0
        acc += if (letters[i] == 'S' && letters[i + width - 1] == 'A' && letters[i + width - 1 + width - 1] == 'M' && letters[i + width - 1 + width - 1 + width - 1] == 'X') 1 else 0
        acc += if (letters[i] == 'S' && letters[i + width] == 'A' && letters[i + width + width] == 'M' && letters[i + width + width + width] == 'X') 1 else 0
        acc += if (letters[i] == 'S' && letters[i + width + 1] == 'A' && letters[i + width + 1 + width + 1] == 'M' && letters[i + width + 1 + width + 1 + width + 1] == 'X') 1 else 0
        i += 1
    }
    return acc
}

fun part2(): Any {
    val (letters, width) = lettersTable
    val lastIndex = letters.size - (width * EDGE_SIZE + 1) - (EDGE_SIZE + 1)
    var acc = 0
    var i = 0
    while (i < lastIndex) {
        if (letters[i] == 'M' && letters[i + 2] == 'M' && letters[i + width + 1] == 'A' && letters[i + width + width] == 'S' && letters[i + width + width + 2] == 'S') {
            acc += 1
        }
        if (letters[i] == 'S' && letters[i + 2] == 'S' && letters[i + width + 1] == 'A' && letters[i + width + width] == 'M' && letters[i + width + width + 2] == 'M') {
            acc += 1
        }
        if (letters[i] == 'M' && letters[i + 2] == 'S' && letters[i + width + 1] == 'A' && letters[i + width + width] == 'M' && letters[i + width + width + 2] == 'S') {
            acc += 1
        }
        if (letters[i] == 'S' && letters[i + 2] == 'M' && letters[i + width + 1] == 'A' && letters[i + width + width] == 'S' && letters[i + width + width + 2] == 'M') {
            acc += 1
        }
        i += 1
    }
    return acc
}

private fun readInput(): List<String> {
    return object {}.javaClass.getResource("Day4.input")
        ?.readText()
        ?.split("\n")
        ?.filter { it.isNotEmpty() }
        ?: emptyList()
}

private fun getLettersTableWithWidth(input: List<String>): Pair<CharArray, Int> {
    val width = input[0].length
    val height = input.size
    val lettersTable = CharArray((width + EDGE_SIZE) * (height + EDGE_SIZE))
    var y = 0
    while (y < height) {
        val offset = y * (width + EDGE_SIZE)
        val line = input[y]
        var x = 0
        while (x < width) {
            val i = offset + x
            lettersTable[i] = line[x]
            x += 1
        }
        y += 1
    }
    return lettersTable to width + EDGE_SIZE
}
