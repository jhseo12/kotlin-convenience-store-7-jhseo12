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
    val validator = Validator()

    fun start() {
        val stock = showStock()
        val promotions = Promotion().promotions

        val order = purchase(stock)
        val buyItem = PurchaseStock().getOrderedStock(stock, order)
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

    private fun purchase(stock: List<Item>): MutableMap<String, Int> {
        while(true) {
            val readOrder = inputView.readItem()
            val order = Purchase(readOrder, stock).needs
            return order
        }
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
                addPromotion(order, item, promotion)
            }
        }
        if (promotion[1] == 1) {
            if (promotion[0] != 0) {
                noPromotion(order, item, promotion)
            }
        }
    }

    private fun addPromotion(order: MutableMap<String, Int>, item: String, promotion: List<Int>) {
        while(true){
            val isAdd = inputView.readAddPromotion(item, promotion[0])
            validator.validatePromotionInput(isAdd)
            if (isAdd == "Y") { // 무료 수량 추가
                order[item] = order[item]!! + promotion[0]
            }
        }
    }

    private fun noPromotion(order: MutableMap<String, Int>, item: String, promotion: List<Int>) {
        while(true) {
            val isPromotion = inputView.readNoPromotion(item, promotion[0])
            validator.validatePromotionInput(isPromotion)
            if (isPromotion == "N") { // 정가 결제
                order[item] = order[item]!! - promotion[0]
            }
        }
    }


    private fun memberPromotion(): Int {
        while(true) {
            val member = inputView.readMemberPromotion()
            validator.validatePromotionInput(member)
            return if (member == "Y") 1 else 0
        }
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
