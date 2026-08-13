package com.studyn5.kana.data

data class ForeignSound(
    val kana: String,
    val romaji: String,
    val formation: String,
    val audioKey: String,
)

data class ForeignWord(
    val kana: String,
    val romaji: String,
    val meaning: String,
    val focus: String,
    val audioKey: String,
)

data class ForeignSoundGroup(
    val id: String,
    val shortLabel: String,
    val title: String,
    val rule: String,
    val sounds: List<ForeignSound>,
    val words: List<ForeignWord>,
)

object ForeignSoundData {
    private fun sound(kana: String, romaji: String, formation: String) =
        ForeignSound(kana, romaji, formation, "foreign_$romaji")

    private fun word(kana: String, romaji: String, meaning: String, focus: String) =
        ForeignWord(kana, romaji, meaning, focus, "foreign_word_$romaji")

    val groups = listOf(
        ForeignSoundGroup(
            id = "f",
            shortLabel = "F",
            title = "Nhóm F · ファ行",
            rule = "フ + nguyên âm nhỏ · đọc liền, không tách fu-a",
            sounds = listOf(
                sound("ファ", "fa", "フ + ァ"), sound("フィ", "fi", "フ + ィ"),
                sound("フェ", "fe", "フ + ェ"), sound("フォ", "fo", "フ + ォ"),
                sound("フュ", "fyu", "フ + ュ"),
            ),
            words = listOf(
                word("ソファ", "sofa", "ghế sofa", "ファ = fa"),
                word("ファイル", "fairu", "tệp tin", "ファ = fa"),
                word("フィルム", "firumu", "phim; cuộn phim", "フィ = fi"),
                word("カフェ", "kafe", "quán cà phê", "フェ = fe"),
                word("フォーク", "fooku", "cái nĩa", "フォ = fo"),
                word("フュージョン", "fyuujon", "sự kết hợp", "フュ = fyu"),
            ),
        ),
        ForeignSoundGroup(
            id = "v",
            shortLabel = "V",
            title = "Nhóm V · ヴァ行",
            rule = "ヴ là ウ thêm dakuten ゛; trong thực tế V có thể nghe gần B",
            sounds = listOf(
                sound("ヴァ", "va", "ヴ + ァ"), sound("ヴィ", "vi", "ヴ + ィ"),
                sound("ヴ", "vu", "ウ + ゛"), sound("ヴェ", "ve", "ヴ + ェ"),
                sound("ヴォ", "vo", "ヴ + ォ"), sound("ヴュ", "vyu", "ヴ + ュ"),
            ),
            words = listOf(
                word("ヴァイオリン", "vaiorin", "đàn violin", "ヴァ = va"),
                word("ヴィーナス", "viinasu", "sao Kim; Venus", "ヴィ = vi"),
                word("ヴェール", "veeru", "mạng che; khăn voan", "ヴェ = ve"),
                word("ヴォーカル", "vookaru", "giọng hát; ca sĩ chính", "ヴォ = vo"),
                word("インタヴュー", "intavyuu", "cuộc phỏng vấn", "ヴュ = vyu"),
            ),
        ),
        ForeignSoundGroup(
            id = "td",
            shortLabel = "T / D",
            title = "Nhóm T / D",
            rule = "Dùng nguyên âm nhỏ để giữ âm ti, tu, di, du của từ nước ngoài",
            sounds = listOf(
                sound("ティ", "ti", "テ + ィ"), sound("トゥ", "tu", "ト + ゥ"),
                sound("テュ", "tyu", "テ + ュ"), sound("ディ", "di", "デ + ィ"),
                sound("ドゥ", "du", "ド + ゥ"), sound("デュ", "dyu", "デ + ュ"),
            ),
            words = listOf(
                word("パーティー", "paatii", "bữa tiệc", "ティ = ti"),
                word("ティッシュ", "tisshu", "khăn giấy", "ティ = ti"),
                word("トゥデイ", "tudei", "hôm nay; today", "トゥ = tu"),
                word("ディスク", "disuku", "đĩa; disk", "ディ = di"),
                word("デュエット", "dyuetto", "bản song ca", "デュ = dyu"),
                word("ヒンドゥー", "hinduu", "Ấn Độ giáo; Hindu", "ドゥ = du"),
            ),
        ),
        ForeignSoundGroup(
            id = "wy",
            shortLabel = "W / Y",
            title = "Nhóm W / Y",
            rule = "ウ hoặc イ kết hợp nguyên âm nhỏ để biểu diễn wi, we, wo, ye",
            sounds = listOf(
                sound("ウィ", "wi", "ウ + ィ"), sound("ウェ", "we", "ウ + ェ"),
                sound("ウォ", "wo", "ウ + ォ"), sound("イェ", "ye", "イ + ェ"),
            ),
            words = listOf(
                word("ウィスキー", "wisukii", "rượu whisky", "ウィ = wi"),
                word("ウェブ", "webu", "web; trang mạng", "ウェ = we"),
                word("ハロウィーン", "harowiin", "lễ Halloween", "ウィ = wi"),
                word("ウォーター", "wootaa", "nước; water", "ウォ = wo"),
                word("イェス", "yesu", "vâng; yes", "イェ = ye"),
            ),
        ),
        ForeignSoundGroup(
            id = "sjc",
            shortLabel = "SH/J/CH",
            title = "Nhóm SH / J / CH",
            rule = "シ・ジ・チ + ェ nhỏ tạo she, je, che trong từ ngoại lai",
            sounds = listOf(
                sound("シェ", "she", "シ + ェ"), sound("ジェ", "je", "ジ + ェ"),
                sound("チェ", "che", "チ + ェ"),
            ),
            words = listOf(
                word("シェフ", "shefu", "đầu bếp", "シェ = she"),
                word("シェア", "shea", "chia sẻ; thị phần", "シェ = she"),
                word("ジェット", "jetto", "máy bay phản lực", "ジェ = je"),
                word("ジェスチャー", "jesuchaa", "cử chỉ", "ジェ = je"),
                word("チェック", "chekku", "kiểm tra", "チェ = che"),
                word("チェーン", "cheen", "dây xích; chuỗi", "チェ = che"),
            ),
        ),
        ForeignSoundGroup(
            id = "extended",
            shortLabel = "Khác",
            title = "Nhóm mở rộng TS / KW / GW",
            rule = "Các tổ hợp ít gặp hơn, thường xuất hiện trong tên riêng và từ phiên âm",
            sounds = listOf(
                sound("ツァ", "tsa", "ツ + ァ"), sound("ツィ", "tsi", "ツ + ィ"),
                sound("ツェ", "tse", "ツ + ェ"), sound("ツォ", "tso", "ツ + ォ"),
                sound("クァ", "kwa", "ク + ァ"), sound("クィ", "kwi", "ク + ィ"),
                sound("クェ", "kwe", "ク + ェ"), sound("クォ", "kwo", "ク + ォ"),
                sound("グァ", "gwa", "グ + ァ"),
            ),
            words = listOf(
                word("モーツァルト", "mootsaruto", "Mozart", "ツァ = tsa"),
                word("カンツォーネ", "kantsoone", "ca khúc Ý", "ツォ = tso"),
                word("クァルテット", "kwarutetto", "bộ tứ; tứ tấu", "クァ = kwa"),
                word("クォーツ", "kwootsu", "thạch anh; quartz", "クォ = kwo"),
                word("グァテマラ", "gwatemara", "Guatemala", "グァ = gwa"),
            ),
        ),
    )
}
