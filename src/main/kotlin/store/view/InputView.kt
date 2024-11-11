package store.view

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun readItem(): String {
        println(ASK_PURCHASE)
        val input = Console.readLine()
        return input
    }

    fun readAddPromotion(item: String, free: Int): String {
        println("${ASK_FREE_FIRST}${item}${ASK_FREE_SECOND}${free}${ASK_FREE_THIRD}")
        val isAdd = Console.readLine()
        return isAdd
    }

    fun readNoPromotion(item: String, noPromotion: Int): String {
        println("${ASK_NO_PROMOTION_FIRST}$item ${noPromotion}${ASK_NO_PROMOTION_SECOND}")
        val isPromotion = Console.readLine()
        return isPromotion
    }

    fun readMemberPromotion(): String {
        println(ASK_MEMBER)
        val isMember = Console.readLine()
        return isMember
    }

    fun keep(): String {
        println(ASK_KEEP)
        val isKeep = Console.readLine()
        return isKeep
    }

    companion object {
        private const val ASK_PURCHASE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"
        private const val ASK_FREE_FIRST = "\n현재 "
        private const val ASK_FREE_SECOND = "은(는) "
        private const val ASK_FREE_THIRD = "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\""
        private const val ASK_NO_PROMOTION_FIRST = "\n현재 "
        private const val ASK_NO_PROMOTION_SECOND = "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"
        private const val ASK_MEMBER = "\n멤버십 할인을 받으시겠습니까? (Y/N)"
        private const val ASK_KEEP = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"
    }
}
