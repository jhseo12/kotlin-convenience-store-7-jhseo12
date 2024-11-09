package store.model

class PurchaseStock {
    private val buyItem = mutableListOf<MutableList<String>>()

    fun getOrderedStock(
        stock: MutableList<MutableList<String>>,
        order: MutableMap<String, Int>
    ): MutableList<MutableList<String>> {
        order.forEach { (key) ->
            addOrderedStock(stock, key)
        }
        return buyItem
    }

    private fun addOrderedStock(
        stock: MutableList<MutableList<String>>,
        key: String
    ) {
        stock.forEach { stockItem ->
            if (key == stockItem[0]) {
                buyItem.add(stockItem)
            }
        }
    }

}
