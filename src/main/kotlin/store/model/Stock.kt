package store.model

import store.utils.Item
import java.io.BufferedReader
import java.io.InputStreamReader

class Stock {
    val stock = mutableListOf<Item>()
    private val nowStock = GET_STOCKS

    init {
        makeStock()
    }

    private fun makeStock() {
        val stream = javaClass.getResourceAsStream(nowStock)?.let { InputStreamReader(it) }
        if (stream != null) {
            BufferedReader(stream).use { reader ->
                reader.readLine()
                val readFile = getStockName(reader)
                getStock(readFile)
            }
        }
    }

    private fun getStockName(reader: BufferedReader): MutableList<MutableList<String>> {
        val readFile = mutableListOf<MutableList<String>>()
        reader.lineSequence().forEach { line ->
            readFile.add(line.split(DIVIDER).map { it.trim() }.toMutableList())
        }
        return readFile
    }

    private fun getStock(readFile: MutableList<MutableList<String>>) {
        val items = readFile.map { it[ZERO] }.distinct()
        items.forEach { item ->
            val lookItem = readFile.filter { it[ZERO].contains(item) }
            checkPromotionStock(item, lookItem)
        }
    }

    private fun checkPromotionStock(item: String, lookItem: List<MutableList<String>>) {
        sizeOne(item, lookItem)
        sizeTwo(item, lookItem)
    }

    private fun sizeOne(item: String, lookItem: List<MutableList<String>>) {
        if (lookItem.size == ONE) {
            val singleItem = lookItem[ZERO]
            if (singleItem[THREE] == NULL) {
                addStock(item, singleItem[ONE].toInt(), singleItem[TWO].toInt(), NULL)
            }
            if (singleItem[THREE] != NULL) {
                addStock(item, singleItem[ONE].toInt(), singleItem[TWO].toInt(), singleItem[THREE])
                addStock(item, singleItem[ONE].toInt(), ZERO, NULL)
            }
        }
    }

    private fun sizeTwo(item: String, lookItem: List<MutableList<String>>) {
        if (lookItem.size == TWO) {
            val promotionItem = lookItem.find { it[THREE] != NULL }
            val normalItem = lookItem.find { it[THREE] == NULL }
            if (promotionItem != null) {
                addStock(item, promotionItem[ONE].toInt(), promotionItem[TWO].toInt(), promotionItem[THREE])
            }
            if (normalItem != null) {
                addStock(item, normalItem[ONE].toInt(), normalItem[TWO].toInt(), NULL)
            }
        }
    }

    private fun addStock(name: String, price: Int, quantity: Int, promotion: String) {
        stock.add(
            Item(name, price, quantity, promotion)
        )
    }

    companion object {
        private const val GET_STOCKS = "/products.md"
        private const val DIVIDER = ","
        private const val NULL = "null"
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
    }
}
