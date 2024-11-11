package store.model

import store.utils.Item

class StockUpdate(
    private val stock: MutableList<Item>,
    private val order: MutableMap<String, Int>,
    private val promotions: MutableList<MutableList<String>>,
) {
    init {
        update()
    }

    private fun update() {
        order.forEach { (name, value) ->
            val itemPromotion = stock
                .filter { it.name == name }
                .find { it.promotion != NULL }
            val itemNoPromotion = stock
                .filter { it.name == name }
                .find { it.promotion == NULL }
            val promotionValue = itemPromotion?.quantity
            updateStock(promotionValue, value, itemPromotion, itemNoPromotion)
        }
    }

    private fun updateStock(promotionValue: Int?, value: Int, itemPromotion: Item?, itemNoPromotion: Item?) {
        if (promotionValue!! >= value) {
            itemPromotion!!.quantity -= value
        }
        if (promotionValue < value) {
            val promotion = promotions.find { it[ZERO] == itemPromotion!!.promotion }
            val leftBuyPromotion = promotionValue % (promotion?.get(ONE)!!.toInt() + promotion[TWO].toInt())
            itemPromotion!!.quantity = ZERO
            itemNoPromotion!!.quantity += leftBuyPromotion
            itemNoPromotion!!.quantity -= value - (promotionValue - leftBuyPromotion)
        }
    }

    companion object {
        private const val NULL = "null"
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
    }
}
