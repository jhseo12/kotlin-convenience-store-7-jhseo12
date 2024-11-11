package store.model

import store.utils.ErrorMessages
import store.utils.Item

class Validator {
    fun invalidItem(order: MutableMap<String, Int>, stock: List<Item>) {
        order.forEach { (item) ->
            val findItem = stock.find { it.name == item }
            if (findItem == null) {
                throw IllegalArgumentException(ErrorMessages.INVALID_ITEM.message)
            }
        }
    }

    fun overStock(order: MutableMap<String, Int>, stock: List<Item>) {
        order.forEach { (name, value) ->
            var allStock = ZERO
            stock.filter { it.name == name }.forEach { item ->
                allStock += item.quantity
            }
            if (allStock < value) {
                throw IllegalArgumentException(ErrorMessages.OVER_STOCK.message)
            }
        }
    }

    fun validatePromotionInput(input: String): Boolean {
        var repeat = false
        try {
            if (!(input == YES || input == NO)) {
                throw IllegalArgumentException(ErrorMessages.WRONG_INPUT.message)
            }
        } catch (error: IllegalArgumentException) {
            println(error.message)
            repeat = true
        }
        return repeat
    }

    companion object {
        private const val ZERO = 0
        private const val YES = "Y"
        private const val NO = "N"
    }
}
