package store.model

import store.utils.Item

class Purchase(private val input: String, private val stock: List<Item>) {
    val needs = mutableMapOf<String, Int>()
    private val validator = Validator()

    init {
        orderPurchase()
    }

    private fun orderPurchase() {
        try {
            order()
            validator.overStock(needs, stock)
        } catch (error: IllegalArgumentException) {
            println(error.message)
        }
    }

    private fun order() {
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
