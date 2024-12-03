sealed interface Instruction

data object Do : Instruction
data object Dont : Instruction
data class Mul(val x: Int, val y: Int) : Instruction {
    val product get() = this.x * this.y
    constructor(matchResult: MatchResult) : this(
        x = matchResult.groups[1]?.value?.toInt() ?: 0,
        y = matchResult.groups[2]?.value?.toInt() ?: 0
    )
}
