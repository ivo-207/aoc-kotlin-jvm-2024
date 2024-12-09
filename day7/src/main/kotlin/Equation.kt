class Equation(val testValue: Long, val numbers: LongArray) {

    fun testEvaluation(vararg operators: Operator): Boolean {
        var acc = numbers[0]
        var index = 0
        while (index < operators.size) {
            val number = numbers[index + 1]
            when (operators[index]) {
                Operator.ADD -> acc += number
                Operator.MULTIPLY -> acc *= number
                Operator.CONCATENATION -> {
                    var dividend = number
                    do {
                        dividend /= 10
                        acc *= 10
                    } while (dividend > 0)
                    acc += number
                }
            }
            if (acc > testValue) {
                return false
            }
            index += 1
        }
        return acc == testValue
    }

}
