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
                .find { it.promotion != "null" } // 해당 구매 아이템 중 프로모션 적용중인 상품
            val itemNoPromotion = stock
                .filter { it.name == name }
                .find { it.promotion == "null" } // 해당 구매 아이템 중 프로모션 미적용중인 상품
            val promotionValue = itemPromotion?.quantity
            if (promotionValue!! >= value) {
                itemPromotion.quantity -= value
            }
            if (promotionValue < value) {
                val promotion = promotions.find { it[0] == itemPromotion.promotion }
                val leftBuyPromotion = promotionValue % (promotion?.get(1)!!.toInt() + promotion[2].toInt())
                itemPromotion.quantity = 0
                itemNoPromotion!!.quantity += leftBuyPromotion
                itemNoPromotion!!.quantity -= value - (promotionValue - leftBuyPromotion)
            }
        }
    }
}
