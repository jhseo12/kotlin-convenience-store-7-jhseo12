package store.utils

enum class ErrorMessages(val message: String) {
    WRONG_FORM("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_ITEM("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    WRONG_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    OVER_STOCK("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
}
