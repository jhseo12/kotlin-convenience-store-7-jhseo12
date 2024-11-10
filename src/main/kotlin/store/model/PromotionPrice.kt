package store.model

import store.utils.Item

class PromotionPrice {
    fun promotionPrice(
        order: MutableMap<String, Int>,
        buyItem: List<Item>,
        promotions: List<List<String>>
    ): MutableMap<String, Int> {
        val promotionValue = mutableMapOf<String, Int>()
        order.forEach { (key, value) ->
            val lookItem = buyItem.filter { it.name == key }
            val promotionItem = lookItem.find { it.promotion != "null" }
            if (promotionItem != null) {
                val getPromotion = promotions.find { it[0].contains(promotionItem.promotion) }
                if (getPromotion != null) {
                    val buy = getPromotion[1].toInt()
                    val get = getPromotion[2].toInt()
                    if (promotionItem.quantity >= value) {
                        val quantity = value / (buy + get)
                        promotionValue[promotionItem.name] = quantity
                    }
                }
            }
        }
        return promotionValue
    }
}