package store.model

import store.utils.Item

class PurchaseStock {
    private val buyItem = mutableListOf<Item>()

    fun getOrderedStock(
        stock: List<Item>,
        order: MutableMap<String, Int>
    ): List<Item> {
        order.forEach { (key) ->
            addOrderedStock(stock, key)
        }
        return buyItem
    }

    private fun addOrderedStock(
        stock: List<Item>,
        key: String
    ) {
        stock.forEach { stockItem ->
            if (key == stockItem.name) {
                buyItem.add(stockItem)
            }
        }
    }
}
