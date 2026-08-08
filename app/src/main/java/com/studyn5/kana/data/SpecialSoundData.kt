package com.studyn5.kana.data

enum class SpecialSoundCategory(val label: String) {
    DAKUON("Âm đục"),
    HANDAKUON("Âm bán đục"),
    YOUON("Âm ghép"),
    CHOUON("Trường âm"),
    SOKUON("Âm ngắt"),
}

data class SpecialSoundEntry(
    val kana: String,
    val romaji: String,
    val formation: String,
    val audioKey: String,
)

data class SpecialSoundGroup(
    val title: String,
    val entries: List<SpecialSoundEntry>,
)

data class SpecialSoundLesson(
    val category: SpecialSoundCategory,
    val title: String,
    val explanation: String,
    val ruleBefore: String,
    val ruleAfter: String,
    val groups: List<SpecialSoundGroup>,
    val tip: String,
)

object SpecialSoundData {
    private fun entry(kana: String, romaji: String, formation: String, audioKey: String = romaji) =
        SpecialSoundEntry(kana, romaji, formation, audioKey)

    val lessons = listOf(
        SpecialSoundLesson(
            category = SpecialSoundCategory.DAKUON,
            title = "Thêm dấu ゛ để đổi âm",
            explanation = "K → G · S → Z · T → D · H → B",
            ruleBefore = "か・カ  ka",
            ruleAfter = "が・ガ  ga",
            groups = listOf(
                SpecialSoundGroup("Hàng K → G", listOf(
                    entry("が・ガ", "ga", "か・カ + ゛"), entry("ぎ・ギ", "gi", "き・キ + ゛"),
                    entry("ぐ・グ", "gu", "く・ク + ゛"), entry("げ・ゲ", "ge", "け・ケ + ゛"),
                    entry("ご・ゴ", "go", "こ・コ + ゛"),
                )),
                SpecialSoundGroup("Hàng S → Z", listOf(
                    entry("ざ・ザ", "za", "さ・サ + ゛"), entry("じ・ジ", "ji", "し・シ + ゛"),
                    entry("ず・ズ", "zu", "す・ス + ゛"), entry("ぜ・ゼ", "ze", "せ・セ + ゛"),
                    entry("ぞ・ゾ", "zo", "そ・ソ + ゛"),
                )),
                SpecialSoundGroup("Hàng T → D", listOf(
                    entry("だ・ダ", "da", "た・タ + ゛"), entry("ぢ・ヂ", "ji", "ち・チ + ゛"),
                    entry("づ・ヅ", "zu", "つ・ツ + ゛"), entry("で・デ", "de", "て・テ + ゛"),
                    entry("ど・ド", "do", "と・ト + ゛"),
                )),
                SpecialSoundGroup("Hàng H → B", listOf(
                    entry("ば・バ", "ba", "は・ハ + ゛"), entry("び・ビ", "bi", "ひ・ヒ + ゛"),
                    entry("ぶ・ブ", "bu", "ふ・フ + ゛"), entry("べ・ベ", "be", "へ・ヘ + ゛"),
                    entry("ぼ・ボ", "bo", "ほ・ホ + ゛"),
                )),
            ),
            tip = "じ và ぢ đều gần âm “ji”; ず và づ đều gần âm “zu”. Trong N5, じ và ず xuất hiện thường xuyên hơn.",
        ),
        SpecialSoundLesson(
            category = SpecialSoundCategory.HANDAKUON,
            title = "Thêm dấu ゜ vào hàng H",
            explanation = "H → P · bật hơi ngắn và rõ",
            ruleBefore = "は・ハ  ha",
            ruleAfter = "ぱ・パ  pa",
            groups = listOf(
                SpecialSoundGroup("Hàng H → P", listOf(
                    entry("ぱ・パ", "pa", "は・ハ + ゜"), entry("ぴ・ピ", "pi", "ひ・ヒ + ゜"),
                    entry("ぷ・プ", "pu", "ふ・フ + ゜"), entry("ぺ・ペ", "pe", "へ・ヘ + ゜"),
                    entry("ぽ・ポ", "po", "ほ・ホ + ゜"),
                )),
            ),
            tip = "Nhìn vòng tròn nhỏ ゜ để nhận biết: は ha → ぱ pa, ひ hi → ぴ pi. Mỗi chữ vẫn chỉ chiếm một nhịp.",
        ),
        SpecialSoundLesson(
            category = SpecialSoundCategory.YOUON,
            title = "Ghép với ゃ・ゅ・ょ viết nhỏ",
            explanation = "Đọc liền thành một nhịp, không tách thành hai âm",
            ruleBefore = "き + ゃ",
            ruleAfter = "きゃ・キャ  kya",
            groups = listOf(
                SpecialSoundGroup("Âm ghép cơ bản", listOf(
                    entry("きゃ・キャ", "kya", "き・キ + ゃ・ャ"), entry("きゅ・キュ", "kyu", "き・キ + ゅ・ュ"), entry("きょ・キョ", "kyo", "き・キ + ょ・ョ"),
                    entry("しゃ・シャ", "sha", "し・シ + ゃ・ャ"), entry("しゅ・シュ", "shu", "し・シ + ゅ・ュ"), entry("しょ・ショ", "sho", "し・シ + ょ・ョ"),
                    entry("ちゃ・チャ", "cha", "ち・チ + ゃ・ャ"), entry("ちゅ・チュ", "chu", "ち・チ + ゅ・ュ"), entry("ちょ・チョ", "cho", "ち・チ + ょ・ョ"),
                    entry("にゃ・ニャ", "nya", "に・ニ + ゃ・ャ"), entry("にゅ・ニュ", "nyu", "に・ニ + ゅ・ュ"), entry("にょ・ニョ", "nyo", "に・ニ + ょ・ョ"),
                    entry("ひゃ・ヒャ", "hya", "ひ・ヒ + ゃ・ャ"), entry("ひゅ・ヒュ", "hyu", "ひ・ヒ + ゅ・ュ"), entry("ひょ・ヒョ", "hyo", "ひ・ヒ + ょ・ョ"),
                    entry("みゃ・ミャ", "mya", "み・ミ + ゃ・ャ"), entry("みゅ・ミュ", "myu", "み・ミ + ゅ・ュ"), entry("みょ・ミョ", "myo", "み・ミ + ょ・ョ"),
                    entry("りゃ・リャ", "rya", "り・リ + ゃ・ャ"), entry("りゅ・リュ", "ryu", "り・リ + ゅ・ュ"), entry("りょ・リョ", "ryo", "り・リ + ょ・ョ"),
                )),
                SpecialSoundGroup("Âm đục và bán đục ghép", listOf(
                    entry("ぎゃ・ギャ", "gya", "ぎ・ギ + ゃ・ャ"), entry("ぎゅ・ギュ", "gyu", "ぎ・ギ + ゅ・ュ"), entry("ぎょ・ギョ", "gyo", "ぎ・ギ + ょ・ョ"),
                    entry("じゃ・ジャ", "ja", "じ・ジ + ゃ・ャ"), entry("じゅ・ジュ", "ju", "じ・ジ + ゅ・ュ"), entry("じょ・ジョ", "jo", "じ・ジ + ょ・ョ"),
                    entry("びゃ・ビャ", "bya", "び・ビ + ゃ・ャ"), entry("びゅ・ビュ", "byu", "び・ビ + ゅ・ュ"), entry("びょ・ビョ", "byo", "び・ビ + ょ・ョ"),
                    entry("ぴゃ・ピャ", "pya", "ぴ・ピ + ゃ・ャ"), entry("ぴゅ・ピュ", "pyu", "ぴ・ピ + ゅ・ュ"), entry("ぴょ・ピョ", "pyo", "ぴ・ピ + ょ・ョ"),
                )),
            ),
            tip = "きや là ki-ya (hai nhịp), còn きゃ là kya (một nhịp). Hãy nhìn kích thước nhỏ của ゃ・ゅ・ょ.",
        ),
        SpecialSoundLesson(
            category = SpecialSoundCategory.CHOUON,
            title = "Kéo dài nguyên âm thêm một nhịp",
            explanation = "Độ dài âm có thể làm thay đổi nghĩa của từ",
            ruleBefore = "おばさん  obasan",
            ruleAfter = "おばあさん  obaasan",
            groups = listOf(
                SpecialSoundGroup("Hiragana", listOf(
                    entry("おかあさん", "okaasan", "a + あ", "word_okaasan"),
                    entry("おにいさん", "oniisan", "i + い", "word_oniisan"),
                    entry("くうき", "kuuki", "u + う", "word_kuuki"),
                    entry("せんせい", "sensei", "e thường + い", "word_sensei"),
                    entry("がっこう", "gakkou", "o thường + う", "word_gakkou"),
                )),
                SpecialSoundGroup("Katakana dùng dấu ー", listOf(
                    entry("コーヒー", "koohii", "kéo dài o và i", "word_koohii"),
                    entry("ケーキ", "keeki", "kéo dài e", "word_keeki"),
                    entry("スーパー", "suupaa", "kéo dài u và a", "word_suupaa"),
                )),
            ),
            tip = "Hãy vỗ tay theo nhịp: お・ば・あ・さ・ん có năm nhịp. Trường âm là thêm đúng một nhịp, không kéo giọng tùy ý.",
        ),
        SpecialSoundLesson(
            category = SpecialSoundCategory.SOKUON,
            title = "っ nhỏ tạo một nhịp dừng",
            explanation = "Giữ lại phụ âm đứng ngay sau っ rồi mới bật âm",
            ruleBefore = "きて  kite",
            ruleAfter = "きって  kitte",
            groups = listOf(
                SpecialSoundGroup("Ngắt trước K · S · T · P", listOf(
                    entry("がっこう", "gakkou", "っ + k", "word_gakkou"),
                    entry("ざっし", "zasshi", "っ + s", "word_zasshi"),
                    entry("きって", "kitte", "っ + t", "word_kitte"),
                    entry("いっぱい", "ippai", "っ + p", "word_ippai"),
                )),
                SpecialSoundGroup("Ví dụ Katakana", listOf(
                    entry("ベッド", "beddo", "ッ + d", "word_beddo"),
                    entry("サッカー", "sakkaa", "ッ + k", "word_sakkaa"),
                )),
            ),
            tip = "Không đọc っ thành “tsu”. Hãy dừng đúng một nhịp rồi bật phụ âm kế tiếp: ki-(dừng)-te.",
        ),
    )
}
