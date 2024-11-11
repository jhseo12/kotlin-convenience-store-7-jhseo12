package store.model

import store.utils.Item

class CheckPromotion {
    fun promotionItem(
        value: Int,
        orderStock: List<Item>,
        promotions: List<List<String>>
    ): List<Int> {
        val remain = mutableListOf<Int>()
        val promotionStockList = orderStock.filter { it.promotion != NULL }
        val promotionStock = promotionStockList[ZERO]
        val promotionNameList = promotions.filter { it[ZERO] == promotionStock.promotion }
        if (promotionStockList.isEmpty() || promotionNameList.isEmpty()) return addRemain(remain)
        val promotionName = promotionNameList[ZERO]
        checkCase(remain, value, promotionStock, promotionName)
        return addRemain(remain)
    }

    private fun addRemain(remain: MutableList<Int>): MutableList<Int> {
        remain.add(ZERO)
        remain.add(TWO)
        return remain
    }

    private fun checkCase(
        remain: MutableList<Int>,
        value: Int,
        promotionStock: Item,
        promotionName: List<String>
    ): List<Int> {
        firstCase(remain, value, promotionStock, promotionName)
        secondCase(remain, value, promotionStock, promotionName)
        thirdCase(remain, value, promotionStock, promotionName)
        return remain
    }

    private fun firstCase(
        remain: MutableList<Int>,
        value: Int,
        promotionStock: Item,
        promotionName: List<String>
    ): List<Int> {
        if (value <= promotionStock.quantity - promotionName[TWO].toInt()) {
            if (value % (promotionName[ONE].toInt() + promotionName[TWO].toInt()) == promotionName[ONE].toInt()) {
                remain.add(promotionName[TWO].toInt())
                remain.add(ZERO)
                return remain
            }
        }
        return remain
    }

    private fun secondCase(
        remain: MutableList<Int>,
        value: Int,
        promotionStock: Item,
        promotionName: List<String>
    ): List<Int> {
        if (value > promotionStock.quantity - promotionName[TWO].toInt() && value <= promotionStock.quantity) {
            remain.add(value % (promotionName[TWO].toInt() + promotionName[TWO].toInt()))
            remain.add(ONE)
            return remain
        }
        return remain
    }

    private fun thirdCase(
        remain: MutableList<Int>,
        value: Int,
        promotionStock: Item,
        promotionName: List<String>
    ): List<Int> {
        if (value > promotionStock.quantity) {
            remain.add(
                value % (promotionName[ONE].toInt() + promotionName[TWO].toInt()) + (promotionStock.quantity - value)
            )
            remain.add(ONE)
            return remain
        }
        return remain
    }

    companion object {
        private const val NULL = "null"
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
    }
}
