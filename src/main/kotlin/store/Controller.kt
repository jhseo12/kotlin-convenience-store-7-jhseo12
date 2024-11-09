package store

import store.model.*
import store.view.InputView
import store.view.OutputView

class Controller {
    private val inputView = InputView()
    private val outputView = OutputView()

    private val stock = Stock()
    private val checkPromotion = CheckPromotion()

    fun start() {
        val stock = showStock()
        val order = purchase()
        val promotions = Promotion().promotions
        val buyItem = PurchaseStock().getOrderedStock(stock, order) // 구매할 상품에 대한 재고 내역
        findPromotion(order, buyItem, promotions)
    }

    private fun showStock(): MutableList<MutableList<String>> {
        val infoStock = stock.stock()
        outputView.printStockNotice()
        outputView.printStock(infoStock)
        return infoStock
    }

    private fun purchase(): MutableMap<String, Int> {
        val readOrder = inputView.readItem()
        return Purchase(readOrder).needs
    }

    private fun findPromotion(
        order: MutableMap<String, Int>,
        buyItem: MutableList<MutableList<String>>,
        promotions: MutableList<MutableList<String>>
    ) {
        order.forEach { (item, value) ->
            val orderStock = buyItem.filter { it[0] == item }
            if (orderStock.size == 2) {
                onlyPromotion(item, value, order, orderStock, promotions)
            }
        }
    }

    private fun onlyPromotion(
        item: String,
        value: Int,
        order: MutableMap<String, Int>,
        orderStock: List<MutableList<String>>,
        promotions: MutableList<MutableList<String>>
    ) {
        val promotion = checkPromotion.promotionItem(value, orderStock, promotions)
        if (promotion[1] == 0) {
            if (promotion[0] != 0) {
                val isAdd = inputView.readAddPromotion(item, promotion[0])
                if (isAdd == "Y") { // 무료 수량 추가
                    order[item] = order[item]!! + promotion[0]
                }
            }
        }
        if (promotion[1] == 1) {
            if (promotion[0] != 0) {
                val isPromotion = inputView.readNoPromotion(item, promotion[0])
                if (isPromotion == "N") { // 정가 결제
                    order[item] = order[item]!! - promotion[0]
                }
            }
        }
    }

    private fun memberPromotion() {
        val member = inputView.readMemberPromotion()
    }

    private fun receipt() {

    }
}
