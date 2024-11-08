package store.model

import java.io.BufferedReader
import java.io.InputStreamReader

class Stock {
    private val nowStock = "/products.md"
    private val stock = mutableListOf<MutableList<String>>()

    fun stock(): MutableList<MutableList<String>> {
        val stream = javaClass.getResourceAsStream(nowStock)?.let { InputStreamReader(it) }
        if (stream != null) {
            BufferedReader(stream).use { reader ->
                getStock(reader)
            }
        }
        return stock
    }

    private fun getStock(reader: BufferedReader) {
        reader.lines().forEach { line ->
            val lineStock = line.split(",").toMutableList()
            stock.add(lineStock)
        }
    }
}
