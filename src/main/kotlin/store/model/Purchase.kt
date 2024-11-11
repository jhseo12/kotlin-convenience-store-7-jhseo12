package store.model

import store.utils.ErrorMessages
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
            val orders = input.split(DIVIDER)
            orders.forEach() { eachItem ->
                val pattern = Regex(PATTERN)
                val matchedPattern = pattern.matchEntire(eachItem)
                    ?: throw IllegalArgumentException(ErrorMessages.WRONG_FORM.message)
                needs[matchedPattern.groupValues[FIRST_GROUP]] = matchedPattern.groupValues[SECOND_GROUP].toInt()
            }
        } catch (error: NumberFormatException) {
            throw IllegalArgumentException(ErrorMessages.WRONG_INPUT.message)
        }
    }

    companion object {
        private const val DIVIDER = ","
        private const val PATTERN = """\[(\D+)-(\d+)]"""
        private const val FIRST_GROUP = 1
        private const val SECOND_GROUP = 2
    }
}
