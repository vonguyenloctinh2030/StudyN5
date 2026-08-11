package com.studyn5.kana.data

import android.content.Context

data class LessonDefinition(
    val id: Int,
    val title: String,
    val description: String,
    val symbol: String,
)

data class LessonItem(
    val lessonId: Int,
    val kana: String,
    val romaji: String,
    val meaning: String,
    val audioKey: String,
    val script: KanaType,
    val soundTypes: Set<String>,
)

class LessonData(context: Context) {
    val lessons = listOf(
        LessonDefinition(1, "Khởi đầu cùng Hiragana", "100 từ Hiragana cơ bản", "あ"),
        LessonDefinition(2, "Hiragana biến âm", "Âm đục và âm bán đục", "が"),
        LessonDefinition(3, "Nhịp điệu Hiragana", "Trường âm và âm ngắt", "っ"),
        LessonDefinition(4, "Khởi đầu cùng Katakana", "100 từ Katakana cơ bản", "ア"),
        LessonDefinition(5, "Katakana biến âm", "Âm đục và âm bán đục", "ガ"),
        LessonDefinition(6, "Nhịp điệu Katakana", "Trường âm và âm ngắt", "ッ"),
        LessonDefinition(7, "Thử thách Hiragana", "Tổng hợp năm loại âm đặc biệt", "きゃ"),
        LessonDefinition(8, "Thử thách Katakana", "Tổng hợp năm loại âm đặc biệt", "キャ"),
        LessonDefinition(9, "Đại luyện tập Kana", "Tổng hợp Hiragana và Katakana", "かな"),
    )

    private val itemsByLesson: Map<Int, List<LessonItem>> =
        context.applicationContext.assets.open("lesson_items.tsv").bufferedReader().useLines { lines ->
            lines.drop(1)
                .filter(String::isNotBlank)
                .mapNotNull { line ->
                    val columns = line.split('\t')
                    if (columns.size < 7) return@mapNotNull null
                    val lessonId = columns[0].toIntOrNull() ?: return@mapNotNull null
                    val script = runCatching { KanaType.valueOf(columns[5]) }.getOrNull()
                        ?: return@mapNotNull null
                    LessonItem(
                        lessonId = lessonId,
                        kana = columns[1],
                        romaji = columns[2],
                        meaning = columns[3],
                        audioKey = columns[4],
                        script = script,
                        soundTypes = columns[6].split(',').filter(String::isNotBlank).toSet(),
                    )
                }
                .toList()
                .groupBy(LessonItem::lessonId)
        }

    fun items(lessonId: Int): List<LessonItem> = itemsByLesson[lessonId].orEmpty()
}
