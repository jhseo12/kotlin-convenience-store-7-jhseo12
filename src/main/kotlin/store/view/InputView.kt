package store.view

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun readItem(): String {
        println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])")
        val input = Console.readLine()
        return input
    }

    fun readAddPromotion(item: String, free: Int): String {
        println("현재 ${item}은(는) ${free}개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)")
        val isAdd = Console.readLine()
        return isAdd
    }

    fun readNoPromotion(item: String, noPromotion: Int): String {
        println("현재 $item ${noPromotion}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)")
        val isPromotion = Console.readLine()
        return isPromotion
    }

    fun readMemberPromotion(): String {
        println("멤버십 할인을 받으시겠습니까? (Y/N)")
        val isMember = Console.readLine()
        return isMember
    }
}
