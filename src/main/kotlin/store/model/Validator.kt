package store.model

import store.utils.Item

class Validator {
    fun overStock(order: MutableMap<String, Int>, stock: List<Item>) {
        order.forEach { (name) ->
            var allStock = 0
            stock.filter { it.name == name }.forEach { item ->
                allStock += item.quantity
                throw (IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."))
            }
        }
    }
}