package store

import store.model.*
import store.utils.Item
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
        val member = memberPromotion()
        receipt(order, buyItem, member, promotions)
    }

    private fun showStock(): List<Item> {
        val infoStock = stock.stock
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
        buyItem: List<Item>,
        promotions: List<List<String>>
    ) {
        order.forEach { (item, value) ->
            val orderStock = buyItem.filter { it.name.contains(item) }
            if (orderStock.size == 2) {
                onlyPromotion(item, value, order, orderStock, promotions)
            }
        }
    }

    private fun onlyPromotion(
        item: String,
        value: Int,
        order: MutableMap<String, Int>,
        orderStock: List<Item>,
        promotions: List<List<String>>
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

    private fun memberPromotion(): Int {
        val member = inputView.readMemberPromotion()
        return if (member == "Y") 1 else 0
    }

    private fun receipt(
        order: MutableMap<String, Int>,
        buyItem: List<Item>,
        member: Int,
        promotions: List<List<String>>
    ) {
        outputView.printReceiptCategory(order, buyItem)
        val promotionCount = PromotionCount().promotionCount(order, buyItem, promotions)
        outputView.printReceiptPromotion(promotionCount)
        var allCount = 0
        order.forEach { (item, value) ->
            allCount += value
        }
        val allCalculate = CalculateAll(order, buyItem, promotionCount)
        val allPrice = allCalculate.allPrice
        val allPromotionPrice = allCalculate.allPromotionPrice
        if (member == 1){
            val noPromotion = allCalculate.allNoPromotionPrice
            val memberPromotion = (noPromotion) * 30 / 100
            outputView.printReceipt(allCount, allPrice, allPromotionPrice, memberPromotion)
        }
        else {
            val memberPromotion = 0
            outputView.printReceipt(allCount, allPrice, allPromotionPrice, memberPromotion)
        }
    }
}
