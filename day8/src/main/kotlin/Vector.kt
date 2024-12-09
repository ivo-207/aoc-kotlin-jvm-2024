data class Vector(val x: Int, val y: Int)

fun Vector.unaryMinus(): Vector = Vector(-x, -y)
