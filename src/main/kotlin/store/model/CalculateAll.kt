package store.model

import store.utils.Item

class CalculateAll(
    private val order: MutableMap<String, Int>,
    private val buyItem: List<Item>,
    private val promotionCount: MutableMap<String, Int>
) {
    var allPrice = 0
    var allPromotionPrice = 0
    var allNoPromotionPrice = 0

    init {
        calculate()
        calculatePromotion()
    }

    private fun calculate() {
        order.forEach { (key, value) ->
            eachCalculate(key, value)
        }
    }

    private fun eachCalculate(key: String, value: Int) {
        val price = buyItem.find { it.name == key }?.price?.times(value)
        if (price != null) {
            allPrice += price
        }
    }


    private fun calculatePromotion() {
        promotionCount.forEach { (key, value) ->
            eachCalculatePromotion(key, value)
        }
    }

    private fun eachCalculatePromotion(key: String, value: Int) {
        val price = buyItem.find { it.name == key }?.price?.times(value)
        if (price != null) {
            allPromotionPrice += price
        }
    }

    private fun noPromotion() {
        order.forEach { (key, value) ->
            eachNoPromotion(key, value)
        }
    }

    private fun eachNoPromotion(key: String, value: Int) {
        if (buyItem.filter { it.name == key }.size == 1) {
            val price = buyItem.find { it.name == key }?.price?.times(value)
            if (price != null) {
                allNoPromotionPrice += price
            }
        }
    }
}
