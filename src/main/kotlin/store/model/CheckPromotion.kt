package store.model

import store.utils.Item

class CheckPromotion {
    fun promotionItem(
        value: Int,
        orderStock: List<Item>,
        promotions: MutableList<MutableList<String>>
    ): List<Int> {
        val remain = mutableListOf<Int>()
        val promotionStock = orderStock.filter { it.promotion != "null" }[0]
        val promotionName = promotions.filter { it[0] == promotionStock.promotion }[0]
        if (value <= promotionStock.quantity - promotionName[2].toInt()) {
            if (value % (promotionName[1].toInt() + promotionName[2].toInt()) == promotionName[1].toInt()) {
                remain.add(promotionName[2].toInt())
                remain.add(0)
                return remain
            }
        }
        if (value > promotionStock.quantity - promotionName[2].toInt() && value <= promotionStock.quantity) {
            remain.add(value % (promotionName[1].toInt() + promotionName[2].toInt()))
            remain.add(1)
            return remain
        }
        if (value > promotionStock.quantity) {
            remain.add(
                value % (promotionName[1].toInt() + promotionName[2].toInt()) + (promotionStock.quantity - value)
            )
            remain.add(1)
            return remain
        }
        remain.add(0)
        remain.add(2)
        return remain
    }
}
