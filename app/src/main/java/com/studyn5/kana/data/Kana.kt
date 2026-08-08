package com.studyn5.kana.data

/**
 * Một chữ Kana (Hiragana hoặc Katakana).
 * @param char     Ký tự hiển thị, ví dụ "あ"
 * @param romaji   Cách đọc latin, ví dụ "a"
 * @param group    Nhóm hàng, ví dụ "a", "ka", "sa"
 * @param type     Loại bảng: HIRAGANA hoặc KATAKANA
 * @param strokes  Số nét (dùng cho ghi chú)
 */
data class Kana(
    val char: String,
    val romaji: String,
    val group: String,
    val type: KanaType,
    val strokes: Int = 1,
)

enum class KanaType(val label: String) {
    HIRAGANA("Hiragana"),
    KATAKANA("Katakana"),
}
