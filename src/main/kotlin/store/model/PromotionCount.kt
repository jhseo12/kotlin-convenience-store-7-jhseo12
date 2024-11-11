package store.model

import store.utils.Item

class PromotionCount {
    fun promotionCount(
        order: MutableMap<String, Int>,
        buyItem: List<Item>,
        promotions: List<List<String>>
    ): MutableMap<String, Int> {
        val promotionValue = mutableMapOf<String, Int>()
        order.forEach { (key, value) ->
            val lookItem = buyItem.filter { it.name == key }
            val promotionItem = lookItem.find { it.promotion != NULL }
            if (promotionItem != null) {
                checkPromotionItem(promotions, promotionItem, value, promotionValue)
            }
        }
        return promotionValue
    }

    private fun checkPromotionItem(
        promotions: List<List<String>>,
        promotionItem: Item,
        value: Int,
        promotionValue: MutableMap<String, Int>
    ) {
        val getPromotion = promotions.find { it[PROMOTION_NAME].contains(promotionItem.promotion) }
        if (getPromotion != null) {
            val buy = getPromotion[PROMOTION_BUY].toInt()
            val get = getPromotion[PROMOTION_GET].toInt()
            if (promotionItem.quantity >= value) {
                val quantity = value / (buy + get)
                promotionValue[promotionItem.name] = quantity
            }
        }
    }

    companion object {
        private const val NULL = "null"
        private const val PROMOTION_NAME = 0
        private const val PROMOTION_BUY = 1
        private const val PROMOTION_GET = 2
    }
}
