data class Position(val x: Int, val y: Int) {

    fun next(direction: Direction): Position {
        return when (direction) {
            Direction.LEFT -> copy(x = x - 1)
            Direction.RIGHT -> copy(x = x + 1)
            Direction.UP -> copy(y = y - 1)
            Direction.DOWN -> copy(y = y + 1)
        }
    }

}
