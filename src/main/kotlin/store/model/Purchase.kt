package store.model

import store.utils.Item

class Purchase {
    var repeat = false

    fun orderPurchase(input: String, stock: List<Item>): MutableMap<String, Int> {
        val needs = mutableMapOf<String, Int>()
        try {
            order(needs, input)
            val validator = Validator()
            validator.overStock(needs, stock)
            repeat = false
        } catch (error: IllegalArgumentException) {
            println(error.message)
            repeat = true
        }
        return needs
    }

    private fun order(needs: MutableMap<String, Int>, input: String) {
        try {
            val orders = input.split(",")
            orders.forEach() { eachItem ->
                val pattern = Regex("""\[(\D+)-(\d+)]""")
                val matchedPattern = pattern.matchEntire(eachItem)
                    ?: throw IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.")
                needs[matchedPattern.groupValues[1]] = matchedPattern.groupValues[2].toInt()
            }
        } catch (error: NumberFormatException) {
            throw IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.")
        }
    }
}
