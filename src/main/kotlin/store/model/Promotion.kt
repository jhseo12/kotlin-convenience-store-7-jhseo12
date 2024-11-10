package store.model

import camp.nextstep.edu.missionutils.DateTimes
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat

class Promotion() {
    private val nowPromotion = "/promotions.md"
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
            val linePromotion = line.split(",").toMutableList()
            readPromotion.add(linePromotion)
        }
    }

    private fun checkTime() {
        val dateTimes = DateTimes.now().toString().split("T")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        readPromotion.forEach { eachPromotion ->
            val startDate = dateFormat.parse(eachPromotion[3]).time
            val endDate = dateFormat.parse(eachPromotion[4]).time
            val date = dateFormat.parse(dateTimes[0]).time

            val promotion = mutableListOf<String>()
            setPromotion(startDate, endDate, date, eachPromotion, promotion)
            if(promotion.size == 3) {
                promotions.add(promotion)
            }
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
            promotion.add(eachPromotion[0])
            promotion.add(eachPromotion[1])
            promotion.add(eachPromotion[2])
        }
    }
}
