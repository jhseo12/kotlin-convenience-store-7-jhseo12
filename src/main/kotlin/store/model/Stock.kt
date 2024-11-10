package store.model

import store.utils.Item
import java.io.BufferedReader
import java.io.InputStreamReader

class Stock {
    val stock = mutableListOf<Item>()
    private val nowStock = "/products.md"

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
            readFile.add(line.split(",").map { it.trim() }.toMutableList())
        }
        return readFile
    }

    private fun getStock(readFile: MutableList<MutableList<String>>) {
        val items = readFile.map { it[0] }.distinct()
        items.forEach { item ->
            val lookItem = readFile.filter { it[0].contains(item) }
            checkPromotionStock(item, lookItem)
        }
    }

    private fun checkPromotionStock(item: String, lookItem: List<MutableList<String>>) {
        if (lookItem.size == 2) {
            val promotionItem = lookItem.find { it[3] != "null" }
            val normalItem = lookItem.find { it[3] == "null" }
            if (promotionItem != null) {
                addStock(item, promotionItem[1].toInt(), promotionItem[2].toInt(), promotionItem[3])
            }
            if (normalItem != null) {
                addStock(item, normalItem[1].toInt(), normalItem[2].toInt(), "null")
            }
        }
        if (lookItem.size == 1) {
            val singleItem = lookItem[0]
            if (singleItem[3] == "null") {
                addStock(item, singleItem[1].toInt(), singleItem[2].toInt(), "null")
            } else {
                addStock(item, singleItem[1].toInt(), singleItem[2].toInt(), singleItem[3])
                addStock(item, singleItem[1].toInt(), 0, "null")
            }
        }
    }

    private fun addStock(name: String, price: Int, quantity: Int, promotion: String) {
        stock.add(
            Item(name, price, quantity, promotion)
        )
    }
}
