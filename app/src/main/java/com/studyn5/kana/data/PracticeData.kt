package com.studyn5.kana.data

import android.content.Context

enum class PracticeScript(val label: String) {
    HIRAGANA("Hiragana"),
    KATAKANA("Katakana"),
    BOTH("Cả hai"),
}

enum class PracticeCategory(val label: String) {
    BASIC("Cơ bản"),
    YOUON("Âm ghép"),
    CHOUON("Trường âm"),
    SOKUON("Âm ngắt"),
}

data class PracticeItem(
    val id: String,
    val text: String,
    val romaji: String,
    val meaning: String? = null,
    val audioKey: String = romaji,
    val script: KanaType,
)

private data class PracticeVocabulary(
    val category: PracticeCategory,
    val reading: String,
    val romaji: String,
    val meaning: String,
    val audioKey: String,
)

class PracticeData(context: Context) {
    private val vocabulary: Map<PracticeCategory, List<PracticeVocabulary>> =
        context.applicationContext.assets.open("practice_vocabulary.tsv").bufferedReader().useLines { lines ->
            lines.drop(1)
                .filter(String::isNotBlank)
                .mapNotNull { line ->
                    val columns = line.split('\t')
                    if (columns.size < 5) return@mapNotNull null
                    val category = runCatching { PracticeCategory.valueOf(columns[0]) }.getOrNull()
                        ?: return@mapNotNull null
                    PracticeVocabulary(category, columns[1], columns[2], columns[3], columns[4])
                }
                .toList()
                .groupBy(PracticeVocabulary::category)
        }

    fun items(script: PracticeScript, category: PracticeCategory): List<PracticeItem> {
        if (category == PracticeCategory.BASIC) return basicItems(script)
        val source = vocabulary[category].orEmpty()
        return buildList {
            if (script != PracticeScript.KATAKANA) {
                source.forEachIndexed { index, word ->
                    add(word.toItem(index, KanaType.HIRAGANA, word.reading))
                }
            }
            if (script != PracticeScript.HIRAGANA) {
                source.forEachIndexed { index, word ->
                    add(word.toItem(index, KanaType.KATAKANA, word.reading.toKatakana()))
                }
            }
        }
    }

    fun expandBasic(selected: List<PracticeItem>): List<PracticeItem> = buildList {
        selected.forEach { item ->
            add(item)
            voicedVariants[item.text].orEmpty().forEach { variant ->
                add(
                    PracticeItem(
                        id = "basic-${item.script.name}-${variant.text}",
                        text = variant.text,
                        romaji = variant.romaji,
                        audioKey = variant.audioKey,
                        script = item.script,
                    ),
                )
            }
        }
    }.distinctBy(PracticeItem::id)

    private fun basicItems(script: PracticeScript): List<PracticeItem> = buildList {
        if (script != PracticeScript.KATAKANA) addAll(KanaData.hiragana.map(Kana::toPracticeItem))
        if (script != PracticeScript.HIRAGANA) addAll(KanaData.katakana.map(Kana::toPracticeItem))
    }

    private fun PracticeVocabulary.toItem(index: Int, type: KanaType, display: String) = PracticeItem(
        id = "${category.name}-$index-${type.name}",
        text = display,
        romaji = romaji,
        meaning = meaning,
        audioKey = audioKey,
        script = type,
    )

    private fun Kana.toPracticeItem() = PracticeItem(
        id = "basic-${type.name}-$char",
        text = char,
        romaji = romaji,
        audioKey = romaji,
        script = type,
    )

    private fun String.toKatakana(): String = buildString(length) {
        this@toKatakana.forEach { char ->
            append(if (char.code in 0x3041..0x3096) (char.code + 0x60).toChar() else char)
        }
    }

    private data class Variant(val text: String, val romaji: String, val audioKey: String = romaji)

    private companion object {
        private val voicedVariants = mapOf(
            "か" to listOf(Variant("が", "ga")), "き" to listOf(Variant("ぎ", "gi")),
            "く" to listOf(Variant("ぐ", "gu")), "け" to listOf(Variant("げ", "ge")), "こ" to listOf(Variant("ご", "go")),
            "さ" to listOf(Variant("ざ", "za")), "し" to listOf(Variant("じ", "ji")),
            "す" to listOf(Variant("ず", "zu")), "せ" to listOf(Variant("ぜ", "ze")), "そ" to listOf(Variant("ぞ", "zo")),
            "た" to listOf(Variant("だ", "da")), "ち" to listOf(Variant("ぢ", "ji")),
            "つ" to listOf(Variant("づ", "zu")), "て" to listOf(Variant("で", "de")), "と" to listOf(Variant("ど", "do")),
            "は" to listOf(Variant("ば", "ba"), Variant("ぱ", "pa")),
            "ひ" to listOf(Variant("び", "bi"), Variant("ぴ", "pi")),
            "ふ" to listOf(Variant("ぶ", "bu"), Variant("ぷ", "pu")),
            "へ" to listOf(Variant("べ", "be"), Variant("ぺ", "pe")),
            "ほ" to listOf(Variant("ぼ", "bo"), Variant("ぽ", "po")),
            "カ" to listOf(Variant("ガ", "ga")), "キ" to listOf(Variant("ギ", "gi")),
            "ク" to listOf(Variant("グ", "gu")), "ケ" to listOf(Variant("ゲ", "ge")), "コ" to listOf(Variant("ゴ", "go")),
            "サ" to listOf(Variant("ザ", "za")), "シ" to listOf(Variant("ジ", "ji")),
            "ス" to listOf(Variant("ズ", "zu")), "セ" to listOf(Variant("ゼ", "ze")), "ソ" to listOf(Variant("ゾ", "zo")),
            "タ" to listOf(Variant("ダ", "da")), "チ" to listOf(Variant("ヂ", "ji")),
            "ツ" to listOf(Variant("ヅ", "zu")), "テ" to listOf(Variant("デ", "de")), "ト" to listOf(Variant("ド", "do")),
            "ハ" to listOf(Variant("バ", "ba"), Variant("パ", "pa")),
            "ヒ" to listOf(Variant("ビ", "bi"), Variant("ピ", "pi")),
            "フ" to listOf(Variant("ブ", "bu"), Variant("プ", "pu")),
            "ヘ" to listOf(Variant("ベ", "be"), Variant("ペ", "pe")),
            "ホ" to listOf(Variant("ボ", "bo"), Variant("ポ", "po")),
        )
    }
}
