package store

import store.view.InputView
import store.view.OutputView
import store.model.Stock
import store.model.Purchase

class Controller {
    private val inputView = InputView()
    private val outputView = OutputView()

    private val stock = Stock()

    fun start() {
        val stock = showStock()
        val order = purchase(stock)
        val checkPromotion = promotion(stock, order)
    }

    private fun showStock(): MutableList<MutableList<String>> {
        val infoStock = stock.stock()
        outputView.printStockNotice()
        outputView.printStock(infoStock)
        return infoStock
    }

    private fun purchase(stock: MutableList<MutableList<String>>): MutableMap<String, Int> {
        val buy = inputView.readItem()
        return Purchase(buy, stock).needs

    }

    private fun promotion(stock: MutableList<MutableList<String>>, order: MutableMap<String, Int>) {

    }
}
