package store.model

class CheckPromotion {
    fun promotionItem(
        value: Int,
        orderStock: List<MutableList<String>>,
        promotions: MutableList<MutableList<String>>
    ): List<Int> {
        val remain = mutableListOf<Int>()
        val promotionStock = orderStock.filter { it[3] != "null" }[0]
        val promotionName = promotions.filter { it[0] == promotionStock[3] }[0]
        if (value <= promotionStock[2].toInt() - promotionName[2].toInt()) {
            if (value % (promotionName[1].toInt() + promotionName[2].toInt()) == promotionName[1].toInt()) {
                remain.add(promotionName[2].toInt())
                remain.add(0)
                return remain
            }
        }
        if (value > promotionStock[2].toInt() - promotionName[2].toInt() && value <= promotionStock[2].toInt()) {
            remain.add(value % (promotionName[1].toInt() + promotionName[2].toInt()))
            remain.add(1)
            return remain
        }
        if (value > promotionStock[2].toInt()) {
            remain.add(
                value % (promotionName[1].toInt() + promotionName[2].toInt()) + (promotionStock[2].toInt() - value)
            )
            remain.add(1)
            return remain
        }
        remain.add(0)
        remain.add(2)
        return remain
    }
}
