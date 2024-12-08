enum class Direction {

    UP {
        override fun turnRight(): Direction = RIGHT
    },
    RIGHT {
        override fun turnRight(): Direction = DOWN
    },
    DOWN {
        override fun turnRight(): Direction = LEFT
    },
    LEFT {
        override fun turnRight(): Direction = UP
    };

    abstract fun turnRight(): Direction

}
