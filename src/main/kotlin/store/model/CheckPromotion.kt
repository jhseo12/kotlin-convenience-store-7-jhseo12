package store.model

import store.utils.Item

class CheckPromotion {
    fun promotionItem(
        value: Int,
        orderStock: List<Item>,
        promotions: List<List<String>>
    ): List<Int> {
        val remain = mutableListOf<Int>()
        val promotionStockList = orderStock.filter { it.promotion != "null" }
        if (promotionStockList.isEmpty()) {
            remain.add(0)
            remain.add(2)
            return remain
        }

        val promotionStock = promotionStockList[0]
        val promotionNameList = promotions.filter { it[0] == promotionStock.promotion }

        if (promotionNameList.isEmpty()) {
            remain.add(0)
            remain.add(2)
            return remain
        }

        val promotionName = promotionNameList[0]

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
