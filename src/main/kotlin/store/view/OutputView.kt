package store.view

import store.utils.Item
import java.text.DecimalFormat

class OutputView {
    fun printStockNotice() {
        println(START_MESSAGE)
    }

    fun printStock(infoStock: List<Item>) {
        val sb = StringBuilder()
        infoStock.forEach { item ->
            printEachStock(sb, item)
        }
        println(sb)
    }

    private fun printEachStock(sb: StringBuilder, item: Item) {
        sb.append(START_LINE)
        sb.append(item.name).append(SPACE)
        sb.append(DecimalFormat(DECIMAL_FORMAT).format(item.price)).append(WON)
        if (item.quantity == ZERO_QUANTITY) sb.append(EMPTY_STOCK)
        else sb.append(DecimalFormat(DECIMAL_FORMAT).format(item.quantity))
            .append(COUNT)
        if (item.promotion == NULL) sb.append(NEW_LINE)
        else sb.append(item.promotion).append(NEW_LINE)
    }

    fun printReceiptCategory(
        order: MutableMap<String, Int>,
        buyItem: List<Item>,
    ) {
        println(W_STORE)
        println(CATEGORY)
        order.forEach { item ->
            val name = item.key
            val count = item.value
            val total = buyItem.find { it.name == item.key }?.price?.times(item.value)
            println("${name}${TAP_FOUR}${count}${TAP_TWO}${DecimalFormat(DECIMAL_FORMAT).format(total)}")
        }
    }

    fun printReceiptPromotion(
        promotionCount: MutableMap<String, Int>,
    ) {
        println(PROMOTION_MESSAGE)
        promotionCount.forEach { (name, free) ->
            println("${name}${TAP_FOUR}${free}")
        }
    }

    fun printReceipt(allCount: Int, allPrice: Int, allPromotionPrice: Int, memberPromotion: Int) {
        println(DIVIDER)
        println("${ALL_PRICE}${allCount}${TAP_TWO} ${DecimalFormat(DECIMAL_FORMAT).format(allPrice)}")
        println("${PROMOTION}${DecimalFormat(DECIMAL_FORMAT).format(allPromotionPrice)}")
        println("${MEMBER_PROMOTION}${DecimalFormat(DECIMAL_FORMAT).format(memberPromotion)}")
        val resultPrice = allPrice - allPromotionPrice - memberPromotion
        println("${PAY}${DecimalFormat(DECIMAL_FORMAT).format(resultPrice)}")
    }

    companion object {
        private const val ALL_PRICE = "총구매액\t\t\t\t"
        private const val PROMOTION = "행사할인\t\t\t\t\t\t-"
        private const val MEMBER_PROMOTION = "멤버십할인\t\t\t\t\t\t-"
        private const val PAY = "내실돈\t\t\t\t\t\t "
        private const val START_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"
        private const val START_LINE = "- "
        private const val SPACE = " "
        private const val DECIMAL_FORMAT = "#,###"
        private const val WON = "원 "
        private const val EMPTY_STOCK = "재고 없음 "
        private const val COUNT = "개 "
        private const val NULL = "null"
        private const val NEW_LINE = "\n"
        private const val TAP_TWO = "\t\t"
        private const val TAP_FOUR = "\t\t\t\t"
        private const val W_STORE = "==============W 편의점================"
        private const val CATEGORY = "상품명\t\t\t\t수량\t\t금액"
        private const val PROMOTION_MESSAGE = "=============증\t\t정==============="
        private const val DIVIDER = "===================================="
        private const val ZERO_QUANTITY = 0
    }
}
