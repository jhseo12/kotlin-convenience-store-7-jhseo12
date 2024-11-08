package store.view

import java.text.DecimalFormat

class OutputView {
    fun printStockNotice() {
        println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n")
    }

    fun printStock(infoStock: MutableList<MutableList<String>>) {
        val sb = StringBuilder()
        infoStock.subList(1, infoStock.size).forEach { category ->
            printEachStock(sb, category)
        }
        println(sb)
    }

    private fun printEachStock(sb: StringBuilder, category: MutableList<String>) {
        sb.append("- ")
        sb.append(category[0]).append(" ")
        sb.append(DecimalFormat("#,###").format(category[1].toInt())).append("원 ")
        if (category[2] == "0") sb.append("재고없음 ")
        else sb.append(DecimalFormat("#,###").format(category[2].toInt())).append("개 ")
        if (category[3] == "null") sb.append("\n")
        else sb.append(category[3]).append("\n")
    }

}
