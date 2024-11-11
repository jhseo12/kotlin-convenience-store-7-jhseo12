package store

import store.model.*
import store.utils.Item
import store.view.InputView
import store.view.OutputView

class Controller {
    private val inputView = InputView()
    private val outputView = OutputView()

    private val initStock = Stock()
    private val checkPromotion = CheckPromotion()
    private val validator = Validator()

    fun run() {
        val infoStock = initStock.stock
        while (true) {
            val stock = showStock(infoStock)
            val promotions = Promotion().promotions
            val order = pointOfSales(stock, promotions)
            if (keepShopping() == NO) break
            StockUpdate(stock, order, promotions)
        }
    }

    private fun pointOfSales(
        stock: List<Item>,
        promotions: MutableList<MutableList<String>>
    ): MutableMap<String, Int> {
        val order = purchase(stock)
        val buyItem = PurchaseStock().getOrderedStock(stock, order)
        findPromotion(order, buyItem, promotions)
        val member = memberPromotion()
        receipt(order, buyItem, member, promotions)
        return order
    }

    private fun showStock(stock: MutableList<Item>): MutableList<Item> {
        outputView.printStockNotice()
        outputView.printStock(stock)
        return stock
    }

    private fun purchase(stock: List<Item>): MutableMap<String, Int> {
        while (true) {
            val readOrder = inputView.readItem()
            val purchase = Purchase()
            val order = purchase.orderPurchase(readOrder, stock)
            if (purchase.repeat) continue
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
            if (orderStock.size == TWO) {
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
        if (promotion[ONE] == ZERO) {
            if (promotion[ZERO] != ZERO) {
                addPromotion(order, item, promotion)
            }
        }
        if (promotion[ONE] == ONE) {
            if (promotion[ZERO] != ZERO) {
                noPromotion(order, item, promotion)
            }
        }
    }

    private fun addPromotion(order: MutableMap<String, Int>, item: String, promotion: List<Int>) {
        while (true) {
            val isAdd = inputView.readAddPromotion(item, promotion[ZERO])
            val repeat = validator.validatePromotionInput(isAdd)
            if (repeat) continue
            if (isAdd == YES) {
                order[item] = order[item]!! + promotion[ZERO]
            }
            break
        }
    }

    private fun noPromotion(order: MutableMap<String, Int>, item: String, promotion: List<Int>) {
        while (true) {
            val isPromotion = inputView.readNoPromotion(item, promotion[ZERO])
            val repeat = validator.validatePromotionInput(isPromotion)
            if (repeat) continue
            if (isPromotion == NO) {
                order[item] = order[item]!! - promotion[ZERO]
            }
            break
        }
    }


    private fun memberPromotion(): Int {
        while (true) {
            val member = inputView.readMemberPromotion()
            val repeat = validator.validatePromotionInput(member)
            if (repeat) continue
            return if (member == YES) ONE else ZERO
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
        var allCount = ZERO
        order.forEach { (_, value) ->
            allCount += value
        }
        val allCalculate = CalculateAll(order, buyItem, promotionCount)
        memberReceipt(allCalculate, member, allCount)
    }

    private fun memberReceipt(allCalculate: CalculateAll, member: Int, allCount: Int) {
        val allPrice = allCalculate.allPrice
        val allPromotionPrice = allCalculate.allPromotionPrice
        if (member == ONE) {
            val noPromotion = allCalculate.allNoPromotionPrice
            val memberPromotion = (noPromotion) * THIRTY / HUNDRED
            outputView.printReceipt(allCount, allPrice, allPromotionPrice, memberPromotion)
        } else {
            val memberPromotion = ZERO
            outputView.printReceipt(allCount, allPrice, allPromotionPrice, memberPromotion)
        }
    }

    private fun keepShopping(): String {
        while (true) {
            val isKeep = inputView.keep()
            val repeat = validator.validatePromotionInput(isKeep)
            if (repeat) continue
            return isKeep
        }
    }

    companion object {
        private const val YES = "Y"
        private const val NO = "N"
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
        private const val THIRTY = 30
        private const val HUNDRED = 100
    }
}
