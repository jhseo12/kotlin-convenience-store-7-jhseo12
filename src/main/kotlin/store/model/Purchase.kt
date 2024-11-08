package store.model

class Purchase(
    private val input: String,
    private val stock: MutableList<MutableList<String>>
) {
    val needs = mutableMapOf<String, Int>()

    init {
        order()
    }

    private fun order() {
        try {
            val list = input.split(",")
            list.forEach() { eachItem ->
                val pattern = Regex("""\[(\D+)-(\d+)]""")
                val matchedPattern = pattern.matchEntire(eachItem)
                needs[matchedPattern!!.groupValues[1]] = matchedPattern.groupValues[2].toInt()
            }
        } catch(error: NumberFormatException) {
            throw IllegalArgumentException()
        }
    }
}