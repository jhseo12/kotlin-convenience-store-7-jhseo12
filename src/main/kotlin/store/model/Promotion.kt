package store.model

import camp.nextstep.edu.missionutils.DateTimes
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat

class Promotion() {
    private val nowPromotion = GET_PROMOTION
    private val readPromotion = mutableListOf<MutableList<String>>()
    val promotions = mutableListOf<MutableList<String>>()

    init {
        promotion()
    }

    private fun promotion() {
        val stream = javaClass.getResourceAsStream(nowPromotion)?.let { InputStreamReader(it) }
        if (stream != null) {
            BufferedReader(stream).use { reader ->
                reader.readLine()
                getPromotion(reader)
            }
        }
        checkTime()
    }

    private fun getPromotion(reader: BufferedReader) {
        reader.lineSequence().forEach { line ->
            val linePromotion = line.split(DIVIDER).toMutableList()
            readPromotion.add(linePromotion)
        }
    }

    private fun checkTime() {
        val dateTimes = DateTimes.now().toString().split(DATE_DIVIDER)
        val dateFormat = SimpleDateFormat(DATE_FORM)
        readPromotion(dateFormat, dateTimes)
    }

    private fun readPromotion(dateFormat: SimpleDateFormat, dateTimes: List<String>) {
        readPromotion.forEach { eachPromotion ->
            val startDate = dateFormat.parse(eachPromotion[THREE]).time
            val endDate = dateFormat.parse(eachPromotion[FOUR]).time
            val date = dateFormat.parse(dateTimes[ZERO]).time

            val promotion = mutableListOf<String>()
            setPromotion(startDate, endDate, date, eachPromotion, promotion)
            if (promotion.size == THREE) promotions.add(promotion)
        }
    }

    private fun setPromotion(
        startDate: Long,
        endDate: Long,
        date: Long,
        eachPromotion: MutableList<String>,
        promotion: MutableList<String>
    ) {
        if (date in startDate..endDate) {
            promotion.add(eachPromotion[ZERO])
            promotion.add(eachPromotion[ONE])
            promotion.add(eachPromotion[TWO])
        }
    }

    companion object {
        private const val GET_PROMOTION = "/promotions.md"
        private const val DIVIDER = ","
        private const val DATE_DIVIDER = "T"
        private const val DATE_FORM = "yyyy-MM-dd"
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
        private const val FOUR = 4
    }
}
