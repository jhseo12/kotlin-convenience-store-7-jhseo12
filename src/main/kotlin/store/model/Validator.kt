package store.model

import store.utils.Item

class Validator {
    fun overStock(order: MutableMap<String, Int>, stock: List<Item>) {
        order.forEach { (name, value) ->
            var allStock = 0
            stock.filter { it.name == name }.forEach { item ->
                allStock += item.quantity
            }
            if (allStock < value) {
                throw IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.")
            }
        }
    }

    fun validatePromotionInput(input: String): Boolean {
        var repeat = false
        try {
            if (!(input == "Y" || input == "N")) {
                throw IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.")
            }
        } catch (error: IllegalArgumentException) {
            println(error.message)
            repeat = true
        }
        return repeat
    }
}
