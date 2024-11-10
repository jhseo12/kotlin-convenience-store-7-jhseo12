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

    fun printReceiptCategory() {
        println("==============W 편의점================")
        println("상품명\t\t수량\t금액")

    }

    fun printReceiptPromotion() {
        println("=============증\t정===============")
    }

    fun printReceipt() {
        println("====================================")
        println("총구매액\t\t10\t10,000")
        println("행사할인\t\t\t-2,000")
        println("멤버십할인\t\t\t-0")
        println("내실돈\t\t\t 8,000")
    }
}
