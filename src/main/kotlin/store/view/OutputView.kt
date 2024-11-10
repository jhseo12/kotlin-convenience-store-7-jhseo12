package store.view

import store.utils.Item
import java.text.DecimalFormat

class OutputView {
    fun printStockNotice() {
        println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n")
    }

    fun printStock(infoStock: List<Item>) {
        val sb = StringBuilder()
        infoStock.forEach { item ->
            printEachStock(sb, item)
        }
        println(sb)
    }

    private fun printEachStock(sb: StringBuilder, item: Item) {
        sb.append("- ")
        sb.append(item.name).append(" ")
        sb.append(DecimalFormat("#,###").format(item.price)).append("원 ")
        if (item.quantity == 0) sb.append("재고 없음 ")
        else sb.append(DecimalFormat("#,###").format(item.quantity)).append("개 ")
        if (item.promotion == "null") sb.append("\n")
        else sb.append(item.promotion).append("\n")
    }

    fun printReceiptCategory(
        order: MutableMap<String, Int>,
        buyItem: List<Item>,
    ) {
        println("==============W 편의점================")
        println("상품명\t\t\t\t수량\t\t금액")
        order.forEach { item ->
            val name = item.key
            val count = item.value
            val total = buyItem.find { it.name == item.key }?.price?.times(item.value)
            println("${name}\t\t\t\t${count}\t\t${DecimalFormat("#,###").format(total)}")
        }
    }

    fun printReceiptPromotion(
        promotionCount: MutableMap<String, Int>,
    ) {
        println("=============증\t\t정===============")
        promotionCount.forEach { (name, free) ->
            println("${name}\t\t\t\t${free}")
        }
    }

    fun printReceipt(allCount: Int, allPrice: Int, allPromotionPrice: Int, memberPromotion: Int) {
        println("====================================")
        println("총구매액\t\t\t\t${allCount}\t\t ${DecimalFormat("#,###").format(allPrice)}")
        println("행사할인\t\t\t\t\t\t-${DecimalFormat("#,###").format(allPromotionPrice)}")
        println("멤버십할인\t\t\t\t\t\t-${DecimalFormat("#,###").format(memberPromotion)}")
        val resultPrice = allPrice - allPromotionPrice - memberPromotion
        println("내실돈\t\t\t\t\t\t ${DecimalFormat("#,###").format(resultPrice)}")
    }
}
