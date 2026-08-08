package com.studyn5.kana.data

/**
 * Dữ liệu 46 Hiragana + 46 Katakana cơ bản (không biến âm, không ghép).
 * Nhóm (group) dùng để lọc/học theo hàng.
 */
object KanaData {

    private val gridOrder = listOf(
        listOf("a", "i", "u", "e", "o"),
        listOf("ka", "ki", "ku", "ke", "ko"),
        listOf("sa", "shi", "su", "se", "so"),
        listOf("ta", "chi", "tsu", "te", "to"),
        listOf("na", "ni", "nu", "ne", "no"),
        listOf("ha", "hi", "fu", "he", "ho"),
        listOf("ma", "mi", "mu", "me", "mo"),
        listOf("ya", null, "yu", null, "yo"),
        listOf("ra", "ri", "ru", "re", "ro"),
        listOf("wa", null, null, null, "wo"),
        listOf("n", null, null, null, null),
    )

    private val hiraganaPairs = listOf(
        "あ" to "a", "い" to "i", "う" to "u", "え" to "e", "お" to "o",
        "か" to "ka", "き" to "ki", "く" to "ku", "け" to "ke", "こ" to "ko",
        "さ" to "sa", "し" to "shi", "す" to "su", "せ" to "se", "そ" to "so",
        "た" to "ta", "ち" to "chi", "つ" to "tsu", "て" to "te", "と" to "to",
        "な" to "na", "に" to "ni", "ぬ" to "nu", "ね" to "ne", "の" to "no",
        "は" to "ha", "ひ" to "hi", "ふ" to "fu", "へ" to "he", "ほ" to "ho",
        "ま" to "ma", "み" to "mi", "む" to "mu", "め" to "me", "も" to "mo",
        "や" to "ya", "ゆ" to "yu", "よ" to "yo",
        "ら" to "ra", "り" to "ri", "る" to "ru", "れ" to "re", "ろ" to "ro",
        "わ" to "wa", "を" to "wo", "ん" to "n",
    )

    private val katakanaPairs = listOf(
        "ア" to "a", "イ" to "i", "ウ" to "u", "エ" to "e", "オ" to "o",
        "カ" to "ka", "キ" to "ki", "ク" to "ku", "ケ" to "ke", "コ" to "ko",
        "サ" to "sa", "シ" to "shi", "ス" to "su", "セ" to "se", "ソ" to "so",
        "タ" to "ta", "チ" to "chi", "ツ" to "tsu", "テ" to "te", "ト" to "to",
        "ナ" to "na", "ニ" to "ni", "ヌ" to "nu", "ネ" to "ne", "ノ" to "no",
        "ハ" to "ha", "ヒ" to "hi", "フ" to "fu", "ヘ" to "he", "ホ" to "ho",
        "マ" to "ma", "ミ" to "mi", "ム" to "mu", "メ" to "me", "モ" to "mo",
        "ヤ" to "ya", "ユ" to "yu", "ヨ" to "yo",
        "ラ" to "ra", "リ" to "ri", "ル" to "ru", "レ" to "re", "ロ" to "ro",
        "ワ" to "wa", "ヲ" to "wo", "ン" to "n",
    )

    private fun groupOf(romaji: String): String {
        // "shi" -> "sh", "chi" -> "ch", "tsu" -> "ts", còn lại lấy phụ âm đầu
        return when {
            romaji.startsWith("sh") -> "sh"
            romaji.startsWith("ch") -> "ch"
            romaji.startsWith("ts") -> "ts"
            romaji == "n" || romaji == "a" || romaji == "i" || romaji == "u" ||
                romaji == "e" || romaji == "o" || romaji == "wa" || romaji == "wo" -> romaji
            else -> romaji.first().toString()
        }
    }

    val hiragana: List<Kana> = hiraganaPairs.map { (c, r) ->
        Kana(char = c, romaji = r, group = groupOf(r), type = KanaType.HIRAGANA)
    }

    val katakana: List<Kana> = katakanaPairs.map { (c, r) ->
        Kana(char = c, romaji = r, group = groupOf(r), type = KanaType.KATAKANA)
    }

    /** Bảng gojūon 5 cột, giữ ô trống ở các hàng ya/wa/n. */
    fun grid(type: KanaType): List<Kana?> {
        val byRomaji = (if (type == KanaType.HIRAGANA) hiragana else katakana)
            .associateBy(Kana::romaji)
        return gridOrder.flatten().map { romaji -> romaji?.let(byRomaji::get) }
    }
}
