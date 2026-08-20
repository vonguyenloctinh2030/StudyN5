package com.studyn5.kana.data

data class LanguageLesson(
    val id: Int,
    val title: String,
    val subtitle: String,
    val symbol: String,
    val vocabulary: List<VocabularyEntry>,
    val grammar: List<GrammarPattern>,
)

data class VocabularyEntry(
    val japanese: String,
    val romaji: String,
    val meaning: String,
    val category: String,
    val exampleJapanese: String,
    val exampleRomaji: String,
    val exampleMeaning: String,
    val referenceJapanese: String? = null,
)

data class GrammarPattern(
    val title: String,
    val formula: String,
    val explanation: String,
    val examples: List<LanguageExample>,
)

data class LanguageExample(val japanese: String, val romaji: String, val meaning: String)

data class DialogueScenario(val title: String, val lines: List<LanguageExample>)

object LanguageLearningData {
    private fun v(
        j: String,
        r: String,
        m: String,
        c: String,
        ej: String,
        er: String,
        em: String,
        reference: String? = null,
    ) = VocabularyEntry(j, r, m, c, ej, er, em, reference)

    private fun e(j: String, r: String, m: String) = LanguageExample(j, r, m)

    val lessons: List<LanguageLesson> by lazy {
        listOf(
            LanguageLesson(1, "Chào hỏi & giới thiệu", "Làm quen và giới thiệu bản thân", "あ", greetings, greetingGrammar),
            LanguageLesson(2, "Quốc gia, nghề nghiệp & ngôn ngữ", "Nói về quê quán, công việc và khả năng", "国", identityWords, identityGrammar),
            LanguageLesson(3, "Số đếm 1–100", "Đếm số, tuổi và số người", "百", numberWords, numberGrammar),
            LanguageLesson(4, "Gia đình của tôi", "Giới thiệu thành viên trong gia đình", "家", familyWords, familyGrammar),
        )
    }

    private val greetings = listOf(
        v("はじめまして", "hajimemashite", "Rất vui được gặp bạn", "Chào hỏi", "はじめまして。ティンです。", "Hajimemashite. Tinh desu.", "Rất vui được gặp bạn. Tôi là Tính."),
        v("おはようございます", "ohayou gozaimasu", "Chào buổi sáng", "Chào hỏi", "おはようございます、せんせい。", "Ohayou gozaimasu, sensei.", "Chào buổi sáng, thầy/cô."),
        v("こんにちは", "konnichiwa", "Xin chào", "Chào hỏi", "みなさん、こんにちは。", "Minasan, konnichiwa.", "Xin chào mọi người."),
        v("こんばんは", "konbanwa", "Chào buổi tối", "Chào hỏi", "こんばんは、たなかさん。", "Konbanwa, Tanaka-san.", "Chào buổi tối, anh/chị Tanaka."),
        v("さようなら", "sayounara", "Tạm biệt", "Chào hỏi", "せんせい、さようなら。", "Sensei, sayounara.", "Tạm biệt thầy/cô."),
        v("ありがとうございます", "arigatou gozaimasu", "Xin cảm ơn", "Lịch sự", "どうもありがとうございます。", "Doumo arigatou gozaimasu.", "Xin chân thành cảm ơn."),
        v("すみません", "sumimasen", "Xin lỗi / làm phiền", "Lịch sự", "すみません、もういちどおねがいします。", "Sumimasen, mou ichido onegai shimasu.", "Xin lỗi, vui lòng nói lại một lần nữa."),
        v("どうぞ", "douzo", "Xin mời", "Lịch sự", "どうぞ、よろしくおねがいします。", "Douzo, yoroshiku onegai shimasu.", "Rất mong được bạn giúp đỡ."),
        v("よろしくおねがいします", "yoroshiku onegai shimasu", "Rất mong được giúp đỡ", "Giới thiệu", "これから、よろしくおねがいします。", "Kore kara, yoroshiku onegai shimasu.", "Từ nay rất mong được bạn giúp đỡ."),
        v("わたし", "watashi", "Tôi", "Giới thiệu", "わたしはティンです。", "Watashi wa Tinh desu.", "Tôi là Tính."),
        v("なまえ", "namae", "Tên", "Giới thiệu", "わたしのなまえはティンです。", "Watashi no namae wa Tinh desu.", "Tên tôi là Tính."),
        v("おなまえ", "onamae", "Tên của bạn (lịch sự)", "Giới thiệu", "おなまえはなんですか。", "Onamae wa nan desu ka.", "Bạn tên là gì?"),
        v("なん", "nan", "Gì", "Từ để hỏi", "おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
        v("はい", "hai", "Vâng / đúng", "Phản hồi", "はい、そうです。", "Hai, sou desu.", "Vâng, đúng vậy."),
        v("いいえ", "iie", "Không", "Phản hồi", "いいえ、ちがいます。", "Iie, chigaimasu.", "Không, không phải."),
        v("おげんきですか", "ogenki desu ka", "Bạn có khỏe không?", "Chào hỏi", "たなかさん、おげんきですか。", "Tanaka-san, ogenki desu ka.", "Anh/chị Tanaka có khỏe không?"),
        v("げんきです", "genki desu", "Tôi khỏe", "Phản hồi", "はい、げんきです。", "Hai, genki desu.", "Vâng, tôi khỏe."),
        v("どういたしまして", "dou itashimashite", "Không có gì", "Lịch sự", "いいえ、どういたしまして。", "Iie, dou itashimashite.", "Không có gì."),
        v("しつれいします", "shitsurei shimasu", "Xin phép / xin thất lễ", "Lịch sự", "では、しつれいします。", "Dewa, shitsurei shimasu.", "Vậy tôi xin phép."),
        v("またあした", "mata ashita", "Hẹn gặp lại ngày mai", "Chào hỏi", "またあした。さようなら。", "Mata ashita. Sayounara.", "Hẹn gặp lại ngày mai. Tạm biệt."),
        v("おやすみなさい", "oyasumi nasai", "Chúc ngủ ngon", "Chào hỏi", "おやすみなさい、またあした。", "Oyasumi nasai, mata ashita.", "Chúc ngủ ngon, hẹn ngày mai."),
        v("こちらこそ", "kochira koso", "Chính tôi cũng vậy", "Phản hồi", "こちらこそ、よろしくおねがいします。", "Kochira koso, yoroshiku onegai shimasu.", "Chính tôi cũng mong được bạn giúp đỡ."),
        v("どうも", "doumo", "Cảm ơn / xin chào (thân mật theo ngữ cảnh)", "Lịch sự", "どうも、ありがとうございます。", "Doumo, arigatou gozaimasu.", "Xin chân thành cảm ơn."),
        v("もういちど", "mou ichido", "Một lần nữa", "Trong lớp", "もういちどおねがいします。", "Mou ichido onegai shimasu.", "Vui lòng nói lại một lần nữa."),
        v("ゆっくり", "yukkuri", "Chậm rãi", "Trong lớp", "ゆっくりおねがいします。", "Yukkuri onegai shimasu.", "Vui lòng nói chậm."),
    )

    private val greetingGrammar = listOf(
        GrammarPattern("Khẳng định danh tính", "N1 は N2 です", "Nêu N1 là N2. Trợ từ は trong mẫu này đọc là ‘wa’.", listOf(e("わたしはティンです。", "Watashi wa Tinh desu.", "Tôi là Tính."), e("わたしはエンジニアです。", "Watashi wa enjinia desu.", "Tôi là kỹ sư."))),
        GrammarPattern("Phủ định danh tính", "N1 は N2 じゃないです", "Nói N1 không phải là N2. Đây là cách nói lịch sự thông dụng.", listOf(e("わたしはがくせいじゃないです。", "Watashi wa gakusei ja nai desu.", "Tôi không phải là sinh viên."), e("キムさんはせんせいじゃないです。", "Kimu-san wa sensei ja nai desu.", "Chị Kim không phải là giáo viên."))),
        GrammarPattern("Câu hỏi phải không", "N1 は N2 ですか", "Thêm か cuối câu để tạo câu hỏi.", listOf(e("ティンさんはエンジニアですか。", "Tinh-san wa enjinia desu ka.", "Anh Tính là kỹ sư phải không?"), e("マイさんはがくせいですか。", "Mai-san wa gakusei desu ka.", "Bạn Mai là sinh viên phải không?"))),
        GrammarPattern("Hỏi thông tin", "N1 は なんですか", "Dùng なん để hỏi N1 là gì.", listOf(e("おなまえはなんですか。", "Onamae wa nan desu ka.", "Bạn tên là gì?"), e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"))),
        GrammarPattern("Sở hữu", "N1 の N2", "の nối hai danh từ; N2 thuộc về hoặc liên quan đến N1.", listOf(e("わたしのなまえはティンです。", "Watashi no namae wa Tinh desu.", "Tên tôi là Tính."), e("わたしのくにはベトナムです。", "Watashi no kuni wa Betonamu desu.", "Đất nước của tôi là Việt Nam."))),
    )

    private val countryRows = listOf(
        listOf("ベトナム", "Betonamu", "Việt Nam", "ベトナムじん", "Betonamu-jin", "người Việt Nam", "ベトナムご", "Betonamu-go", "tiếng Việt"),
        listOf("にほん", "Nihon", "Nhật Bản", "にほんじん", "Nihon-jin", "người Nhật", "にほんご", "Nihon-go", "tiếng Nhật"),
        listOf("かんこく", "Kankoku", "Hàn Quốc", "かんこくじん", "Kankoku-jin", "người Hàn Quốc", "かんこくご", "Kankoku-go", "tiếng Hàn"),
        listOf("ちゅうごく", "Chuugoku", "Trung Quốc", "ちゅうごくじん", "Chuugoku-jin", "người Trung Quốc", "ちゅうごくご", "Chuugoku-go", "tiếng Trung"),
        listOf("アメリカ", "Amerika", "Mỹ", "アメリカじん", "Amerika-jin", "người Mỹ", "えいご", "Eigo", "tiếng Anh"),
        listOf("イギリス", "Igirisu", "Anh", "イギリスじん", "Igirisu-jin", "người Anh", "えいご", "Eigo", "tiếng Anh"),
        listOf("イタリア", "Itaria", "Ý", "イタリアじん", "Itaria-jin", "người Ý", "イタリアご", "Itaria-go", "tiếng Ý"),
        listOf("フランス", "Furansu", "Pháp", "フランスじん", "Furansu-jin", "người Pháp", "フランスご", "Furansu-go", "tiếng Pháp"),
        listOf("ドイツ", "Doitsu", "Đức", "ドイツじん", "Doitsu-jin", "người Đức", "ドイツご", "Doitsu-go", "tiếng Đức"),
        listOf("タイ", "Tai", "Thái Lan", "タイじん", "Tai-jin", "người Thái", "タイご", "Tai-go", "tiếng Thái"),
    )

    private val identityWords: List<VocabularyEntry> = buildList {
        add(v("くに", "kuni", "đất nước", "Khái niệm", "わたしのくにはベトナムです。", "Watashi no kuni wa Betonamu desu.", "Đất nước của tôi là Việt Nam."))
        add(v("じん／～じん", "jin / ~jin", "người / quốc tịch", "Quy tắc", "わたしはベトナムじんです。", "Watashi wa Betonamu-jin desu.", "Tôi là người Việt Nam."))
        add(v("ご／～ご", "go / ~go", "ngôn ngữ / tiếng…", "Quy tắc", "わたしはにほんごができます。", "Watashi wa Nihon-go ga dekimasu.", "Tôi biết tiếng Nhật."))
        countryRows.forEach { row ->
            add(v(row[0], row[1], row[2], "Quốc gia", "${row[0]}からです。", "${row[1]} kara desu.", "Tôi đến từ ${row[2]}."))
            add(v(row[3], row[4], row[5], "Quốc tịch", "わたしは${row[3]}です。", "Watashi wa ${row[4]} desu.", "Tôi là ${row[5]}."))
            if (none { it.japanese == row[6] }) add(v(row[6], row[7], row[8], "Ngôn ngữ", "${row[6]}ができます。", "${row[7]} ga dekimasu.", "Tôi biết ${row[8]}."))
        }
        addAll(listOf(
            v("しごと", "shigoto", "công việc, nghề nghiệp", "Nghề nghiệp", "おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
            v("おしごとはなんですか", "oshigoto wa nan desu ka", "Bạn làm nghề gì?", "Cụm từ", "おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
            v("おくにはどちらですか", "okuni wa dochira desu ka", "Bạn đến từ nước nào?", "Cụm từ", "おくにはどちらですか。", "Okuni wa dochira desu ka.", "Bạn đến từ nước nào?"),
            v("どちらからですか", "dochira kara desu ka", "Bạn đến từ đâu?", "Cụm từ", "どちらからですか。ベトナムからです。", "Dochira kara desu ka. Betonamu kara desu.", "Bạn đến từ đâu? Tôi đến từ Việt Nam."),
            v("なんご", "nan-go", "ngôn ngữ nào / tiếng gì", "Từ để hỏi", "なんごができますか。", "Nan-go ga dekimasu ka.", "Bạn biết ngôn ngữ nào?"),
            v("エンジニア", "enjinia", "kỹ sư", "Nghề nghiệp", "わたしはエンジニアです。", "Watashi wa enjinia desu.", "Tôi là kỹ sư."),
            v("ソフトウェアエンジニア", "sofutowea enjinia", "kỹ sư phần mềm", "Nghề nghiệp", "わたしはソフトウェアエンジニアです。", "Watashi wa sofutowea enjinia desu.", "Tôi là kỹ sư phần mềm."),
            v("かいはつしゃ", "kaihatsusha", "lập trình viên / nhà phát triển", "Nghề nghiệp", "わたしはかいはつしゃです。", "Watashi wa kaihatsusha desu.", "Tôi là lập trình viên."),
            v("きょうし", "kyoushi", "giáo viên (nghề nghiệp)", "Nghề nghiệp", "あねはきょうしです。", "Ane wa kyoushi desu.", "Chị tôi là giáo viên."),
            v("せんせい", "sensei", "thầy/cô; cách gọi người dạy", "Nghề nghiệp", "たなかさんはせんせいです。", "Tanaka-san wa sensei desu.", "Anh/chị Tanaka là giáo viên."),
            v("がくせい", "gakusei", "học sinh, sinh viên", "Nghề nghiệp", "マイさんはがくせいです。", "Mai-san wa gakusei desu.", "Bạn Mai là sinh viên."),
            v("かいしゃいん", "kaishain", "nhân viên công ty", "Nghề nghiệp", "ちちはかいしゃいんです。", "Chichi wa kaishain desu.", "Bố tôi là nhân viên công ty."),
            v("ぎんこういん", "ginkouin", "nhân viên ngân hàng", "Nghề nghiệp", "はははぎんこういんです。", "Haha wa ginkouin desu.", "Mẹ tôi là nhân viên ngân hàng."),
            v("いしゃ", "isha", "bác sĩ", "Nghề nghiệp", "あにはいしゃです。", "Ani wa isha desu.", "Anh tôi là bác sĩ."),
            v("こうむいん", "koumuin", "công chức", "Nghề nghiệp", "おっとはこうむいんです。", "Otto wa koumuin desu.", "Chồng tôi là công chức."),
            v("しゅふ", "shufu", "người nội trợ", "Nghề nghiệp", "つまはしゅふです。", "Tsuma wa shufu desu.", "Vợ tôi là người nội trợ."),
            v("どちら", "dochira", "đâu / phía nào (lịch sự)", "Từ để hỏi", "どちらからですか。", "Dochira kara desu ka.", "Bạn đến từ đâu?"),
            v("から", "kara", "từ", "Trợ từ", "ベトナムからです。", "Betonamu kara desu.", "Tôi đến từ Việt Nam."),
            v("できます", "dekimasu", "có thể, biết", "Khả năng", "にほんごができます。", "Nihon-go ga dekimasu.", "Tôi biết tiếng Nhật."),
            v("できません", "dekimasen", "không thể, không biết", "Khả năng", "かんこくごはできません。", "Kankoku-go wa dekimasen.", "Tôi không biết tiếng Hàn."),
        ))
    }

    private val identityGrammar = listOf(
        GrammarPattern("Hỏi xuất thân", "どちらからですか", "Cách hỏi lịch sự ‘Bạn đến từ đâu?’; trả lời bằng địa danh + からです.", listOf(e("どちらからですか。", "Dochira kara desu ka.", "Bạn đến từ đâu?"), e("ベトナムからです。", "Betonamu kara desu.", "Tôi đến từ Việt Nam."))),
        GrammarPattern("Hỏi nghề nghiệp", "おしごとは なんですか", "お làm câu hỏi lịch sự hơn; なん nghĩa là gì.", listOf(e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"), e("ソフトウェアエンジニアです。", "Sofutowea enjinia desu.", "Tôi là kỹ sư phần mềm."))),
        GrammarPattern("Quốc tịch", "Tên nước + じん", "Gắn ～じん sau tên nước để nói quốc tịch hoặc người của nước đó.", listOf(e("わたしはベトナムじんです。", "Watashi wa Betonamu-jin desu.", "Tôi là người Việt Nam."), e("キムさんはかんこくじんです。", "Kimu-san wa Kankoku-jin desu.", "Chị Kim là người Hàn Quốc."))),
        GrammarPattern("Ngôn ngữ", "Tên nước + ご", "Nhiều tên ngôn ngữ dùng ～ご. Tiếng Anh là えいご, không phải アメリカご hay イギリスご.", listOf(e("にほんごをべんきょうします。", "Nihon-go o benkyou shimasu.", "Tôi học tiếng Nhật."), e("えいごができます。", "Eigo ga dekimasu.", "Tôi biết tiếng Anh."))),
        GrammarPattern("Nói khả năng", "N1 は N2 が できます", "N2 là ngôn ngữ hoặc kỹ năng mà N1 có thể thực hiện.", listOf(e("わたしはベトナムごができます。", "Watashi wa Betonamu-go ga dekimasu.", "Tôi biết tiếng Việt."), e("ティンさんはにほんごができますか。", "Tinh-san wa Nihon-go ga dekimasu ka.", "Anh Tính biết tiếng Nhật không?"))),
        GrammarPattern("Phủ định khả năng", "N2 は できません", "Dùng できません để nói không thể hoặc chưa biết một kỹ năng.", listOf(e("いいえ、できません。", "Iie, dekimasen.", "Không, tôi không biết."), e("かんこくごはできません。", "Kankoku-go wa dekimasen.", "Tôi không biết tiếng Hàn."))),
    )

    private fun japaneseNumber(n: Int): Pair<String, String> {
        val kanji = arrayOf("", "一", "二", "三", "四", "五", "六", "七", "八", "九")
        val reading = arrayOf("", "ichi", "ni", "san", "yon", "go", "roku", "nana", "hachi", "kyuu")
        if (n == 100) return "百" to "hyaku"
        val tens = n / 10
        val ones = n % 10
        val j = (if (tens == 0) "" else if (tens == 1) "十" else kanji[tens] + "十") + kanji[ones]
        val r = (if (tens == 0) "" else if (tens == 1) "juu" else reading[tens] + "-juu") +
            (if (ones == 0) "" else if (tens == 0) reading[ones] else "-${reading[ones]}")
        return j to r
    }

    private fun hiraganaNumber(n: Int): String {
        val reading = arrayOf("", "いち", "に", "さん", "よん", "ご", "ろく", "なな", "はち", "きゅう")
        if (n == 100) return "ひゃく"
        val tens = n / 10
        val ones = n % 10
        return (if (tens == 0) "" else if (tens == 1) "じゅう" else reading[tens] + "じゅう") + reading[ones]
    }

    private fun ageExpression(age: Int): Pair<String, String> {
        if (age == 20) return "はたち" to "hatachi"
        val (japanese, romaji) = japaneseNumber(age)
        val reading = when {
            age % 10 == 1 -> romaji.removeSuffix("ichi") + "issai"
            age % 10 == 8 -> romaji.removeSuffix("hachi") + "hassai"
            age % 10 == 0 -> romaji.removeSuffix("juu") + "jussai"
            else -> "$romaji-sai"
        }
        return "${japanese}さい" to reading
    }

    private val numberWords = (1..100).map { number ->
        val (kanji, r) = japaneseNumber(number)
        val hiragana = hiraganaNumber(number)
        v(
            hiragana,
            r,
            number.toString(),
            if (number < 10) "Số cơ bản" else "Số ghép",
            "ばんごうは${hiragana}です。",
            "Bangou wa $r desu.",
            "Số là $number.",
            reference = kanji,
        )
    } + listOf(
        v("ひとり", "hitori", "một người", "Đếm người", "ひとりです。", "Hitori desu.", "Có một người."),
        v("ふたり", "futari", "hai người", "Đếm người", "ふたりです。", "Futari desu.", "Có hai người."),
        v("さんにん", "san-nin", "ba người", "Đếm người", "かぞくはさんにんです。", "Kazoku wa san-nin desu.", "Gia đình có ba người."),
        v("よにん", "yo-nin", "bốn người", "Đếm người", "かぞくはよにんです。", "Kazoku wa yo-nin desu.", "Gia đình có bốn người."),
        v("なんにん", "nan-nin", "bao nhiêu người", "Đếm người", "かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình có bao nhiêu người?"),
        v("さい／～さい", "sai / ~sai", "… tuổi", "Tuổi", "わたしはさんじゅっさいです。", "Watashi wa sanjussai desu.", "Tôi 30 tuổi."),
        v("なんさい", "nan-sai", "bao nhiêu tuổi", "Tuổi", "なんさいですか。", "Nan-sai desu ka.", "Bạn bao nhiêu tuổi?"),
        v("はたち", "hatachi", "20 tuổi", "Tuổi", "わたしははたちです。", "Watashi wa hatachi desu.", "Tôi 20 tuổi."),
    )

    private val numberGrammar = listOf(
        GrammarPattern("Tạo số 11–19", "じゅう + số", "10 đứng trước số hàng đơn vị: 11 = じゅういち.", listOf(e("じゅういち", "juu-ichi", "11"), e("じゅうきゅう", "juu-kyuu", "19"))),
        GrammarPattern("Tạo số tròn chục", "số + じゅう", "Số hàng chục đứng trước じゅう: 30 = さんじゅう.", listOf(e("さんじゅう", "san-juu", "30"), e("きゅうじゅう", "kyuu-juu", "90"))),
        GrammarPattern("Tạo số ghép", "hàng chục + じゅう + hàng đơn vị", "Ghép hàng chục và hàng đơn vị theo thứ tự.", listOf(e("さんじゅうろく", "san-juu-roku", "36"), e("ななじゅうよん", "nana-juu-yon", "74"))),
        GrammarPattern("Hỏi và nói tuổi", "なんさいですか／～さいです", "Dùng ～さい sau số tuổi. 20 tuổi có cách đọc đặc biệt là はたち.", listOf(e("なんさいですか。", "Nan-sai desu ka.", "Bạn bao nhiêu tuổi?"), e("にじゅうろくさいです。", "Ni-juu-roku-sai desu.", "Tôi 26 tuổi."))),
        GrammarPattern("Đếm người", "số + にん", "Từ ba người trở lên thường dùng số + にん; một và hai người là ngoại lệ.", listOf(e("ひとり", "hitori", "một người"), e("ふたり", "futari", "hai người"), e("ななにん", "nana-nin", "bảy người"))),
        GrammarPattern("Hỏi số người", "なんにんですか", "Dùng なんにん để hỏi có bao nhiêu người.", listOf(e("かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?"), e("ろくにんです。", "Roku-nin desu.", "Có sáu người."))),
    )

    private val familyWords = listOf(
        v("かぞく", "kazoku", "gia đình", "Chung", "わたしのかぞくはろくにんです。", "Watashi no kazoku wa roku-nin desu.", "Gia đình tôi có sáu người."),
        v("りょうしん", "ryoushin", "bố mẹ", "Chung", "りょうしんはベトナムにいます。", "Ryoushin wa Betonamu ni imasu.", "Bố mẹ tôi ở Việt Nam."),
        v("ちち", "chichi", "bố của mình", "Gia đình mình", "ちちはかいしゃいんです。", "Chichi wa kaishain desu.", "Bố tôi là nhân viên công ty."),
        v("はは", "haha", "mẹ của mình", "Gia đình mình", "はははきょうしです。", "Haha wa kyoushi desu.", "Mẹ tôi là giáo viên."),
        v("あに", "ani", "anh trai của mình", "Gia đình mình", "あにはさんじゅうさいです。", "Ani wa sanjuu-sai desu.", "Anh tôi 30 tuổi."),
        v("あね", "ane", "chị gái của mình", "Gia đình mình", "あねはにほんごができます。", "Ane wa Nihon-go ga dekimasu.", "Chị tôi biết tiếng Nhật."),
        v("おとうと", "otouto", "em trai của mình", "Gia đình mình", "おとうとはがくせいです。", "Otouto wa gakusei desu.", "Em trai tôi là sinh viên."),
        v("いもうと", "imouto", "em gái của mình", "Gia đình mình", "いもうとはじゅうはっさいです。", "Imouto wa juu-hassai desu.", "Em gái tôi 18 tuổi."),
        v("おっと", "otto", "chồng của mình", "Vợ chồng", "おっとはエンジニアです。", "Otto wa enjinia desu.", "Chồng tôi là kỹ sư."),
        v("つま", "tsuma", "vợ của mình", "Vợ chồng", "つまはぎんこういんです。", "Tsuma wa ginkouin desu.", "Vợ tôi là nhân viên ngân hàng."),
        v("こども", "kodomo", "con, trẻ em", "Con cái", "こどもがふたりいます。", "Kodomo ga futari imasu.", "Tôi có hai người con."),
        v("むすこ", "musuko", "con trai của mình", "Con cái", "むすこはよんさいです。", "Musuko wa yon-sai desu.", "Con trai tôi bốn tuổi."),
        v("むすめ", "musume", "con gái của mình", "Con cái", "むすめはろくさいです。", "Musume wa roku-sai desu.", "Con gái tôi sáu tuổi."),
        v("おとうさん", "otousan", "bố của người khác / cách gọi bố", "Gia đình người khác", "おとうさんはおいくつですか。", "Otousan wa oikutsu desu ka.", "Bố bạn bao nhiêu tuổi?"),
        v("おかあさん", "okaasan", "mẹ của người khác / cách gọi mẹ", "Gia đình người khác", "おかあさんはきょうしですか。", "Okaasan wa kyoushi desu ka.", "Mẹ bạn là giáo viên phải không?"),
        v("おにいさん", "oniisan", "anh trai của người khác", "Gia đình người khác", "おにいさんはどちらですか。", "Oniisan wa dochira desu ka.", "Anh trai bạn là người nào?"),
        v("おねえさん", "oneesan", "chị gái của người khác", "Gia đình người khác", "おねえさんはかんこくにいます。", "Oneesan wa Kankoku ni imasu.", "Chị gái bạn ở Hàn Quốc."),
        v("います", "imasu", "có / ở (người, động vật)", "Tồn tại", "ちちと ははがいます。", "Chichi to haha ga imasu.", "Tôi có bố và mẹ."),
        v("と", "to", "và (nối danh từ)", "Trợ từ", "ちちと ははと あにがいます。", "Chichi to haha to ani ga imasu.", "Có bố, mẹ và anh trai."),
        v("だれ", "dare", "ai", "Từ để hỏi", "このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?"),
        v("そふ", "sofu", "ông của mình", "Họ hàng", "そふはななじゅうさいです。", "Sofu wa nana-juu-sai desu.", "Ông tôi 70 tuổi."),
        v("そぼ", "sobo", "bà của mình", "Họ hàng", "そぼはベトナムにいます。", "Sobo wa Betonamu ni imasu.", "Bà tôi ở Việt Nam."),
        v("おじ", "oji", "chú, bác, cậu của mình", "Họ hàng", "おじはいしゃです。", "Oji wa isha desu.", "Chú/bác/cậu tôi là bác sĩ."),
        v("おば", "oba", "cô, dì, bác gái của mình", "Họ hàng", "おばはきょうしです。", "Oba wa kyoushi desu.", "Cô/dì/bác gái tôi là giáo viên."),
        v("きょうだい", "kyoudai", "anh chị em", "Chung", "きょうだいがふたりいます。", "Kyoudai ga futari imasu.", "Tôi có hai anh chị em."),
    )

    private val familyGrammar = listOf(
        GrammarPattern("Nói số người trong gia đình", "かぞくは ～にんです", "Dùng số đếm người để nói quy mô gia đình.", listOf(e("わたしのかぞくはろくにんです。", "Watashi no kazoku wa roku-nin desu.", "Gia đình tôi có sáu người."), e("マイさんのかぞくはよにんです。", "Mai-san no kazoku wa yo-nin desu.", "Gia đình bạn Mai có bốn người."))),
        GrammarPattern("Liệt kê thành viên", "N1 と N2 が います", "と nối các danh từ; います dùng cho người và động vật.", listOf(e("ちちと ははがいます。", "Chichi to haha ga imasu.", "Tôi có bố và mẹ."), e("あにと いもうとがいます。", "Ani to imouto ga imasu.", "Tôi có anh trai và em gái."))),
        GrammarPattern("Nói mình có ai", "N が います", "Dùng がいます để nói một người hoặc con vật tồn tại/có mặt.", listOf(e("こどもがふたりいます。", "Kodomo ga futari imasu.", "Tôi có hai người con."), e("あねがいます。", "Ane ga imasu.", "Tôi có chị gái."))),
        GrammarPattern("Hỏi người trong ảnh", "このひとは だれですか", "Dùng để hỏi người này là ai; lịch sự hơn có thể dùng どなた.", listOf(e("このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?"), e("わたしのあねです。", "Watashi no ane desu.", "Đây là chị gái tôi."))),
        GrammarPattern("Phân biệt gia đình mình/người khác", "ちち ↔ おとうさん", "Dùng ちち, はは… khi nói về gia đình mình; dùng おとうさん, おかあさん… khi nói với hoặc về gia đình người khác.", listOf(e("ちちはいしゃです。", "Chichi wa isha desu.", "Bố tôi là bác sĩ."), e("おとうさんはいしゃですか。", "Otousan wa isha desu ka.", "Bố bạn là bác sĩ phải không?"))),
        GrammarPattern("Nói nơi đang ở", "N は địa điểm に います", "に đánh dấu nơi một người đang ở.", listOf(e("りょうしんはベトナムにいます。", "Ryoushin wa Betonamu ni imasu.", "Bố mẹ tôi ở Việt Nam."), e("あねはにほんにいます。", "Ane wa Nihon ni imasu.", "Chị tôi ở Nhật Bản."))),
    )

    fun quickPractice(lesson: LanguageLesson): List<VocabularyEntry> =
        List(25) { lesson.vocabulary[(it * 7 + lesson.id) % lesson.vocabulary.size] }

    fun dialogues(lesson: LanguageLesson): List<DialogueScenario> {
        val names = listOf("マイ" to "Mai", "ティン" to "Tinh", "キム" to "Kimu", "カーラ" to "Kaara", "アン" to "An")
        return List(25) { index ->
            val (name, nameRomaji) = names[index % names.size]
            val (otherName, otherNameRomaji) = names[(index / names.size + 1) % names.size]
            when (lesson.id) {
                1 -> DialogueScenario(
                    listOf("Lần đầu gặp mặt", "Buổi sáng ở lớp", "Chào đồng nghiệp", "Tự giới thiệu", "Làm quen bạn mới")[index % 5],
                    listOf(
                        e("はじめまして。わたしは${name}です。", "Hajimemashite. Watashi wa $nameRomaji desu.", "Rất vui được gặp bạn. Tôi là $nameRomaji."),
                        e("はじめまして。わたしは${otherName}です。", "Hajimemashite. Watashi wa $otherNameRomaji desu.", "Rất vui được gặp bạn. Tôi là $otherNameRomaji."),
                        e("どうぞよろしくおねがいします。", "Douzo yoroshiku onegai shimasu.", "Rất mong được bạn giúp đỡ."),
                    ),
                )
                2 -> {
                    val row = countryRows[index % countryRows.size]
                    val jobs = listOf(
                        Triple("ソフトウェアエンジニア", "sofutowea enjinia", "kỹ sư phần mềm"),
                        Triple("かいはつしゃ", "kaihatsusha", "lập trình viên"),
                        Triple("きょうし", "kyoushi", "giáo viên"),
                        Triple("がくせい", "gakusei", "sinh viên"),
                        Triple("かいしゃいん", "kaishain", "nhân viên công ty"),
                    )
                    val job = jobs[index % jobs.size]
                    DialogueScenario("Quê quán và nghề nghiệp ${index + 1}", listOf(
                        e("はじめまして。${otherName}です。", "Hajimemashite. $otherNameRomaji desu.", "Rất vui được gặp bạn. Tôi là $otherNameRomaji."),
                        e("どちらからですか。", "Dochira kara desu ka.", "Bạn đến từ đâu?"),
                        e("${row[0]}からです。", "${row[1]} kara desu.", "Tôi đến từ ${row[2]}."),
                        e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
                        e("${job.first}です。", "${job.second} desu.", "Tôi là ${job.third}."),
                        e("${row[6]}ができますか。", "${row[7]} ga dekimasu ka.", "Bạn biết ${row[8]} không?"),
                    ))
                }
                3 -> {
                    val age = 20 + index
                    val people = 3 + index % 6
                    val (ageText, ageRead) = ageExpression(age)
                    val (peopleJ, peopleR) = japaneseNumber(people)
                    val peopleRead = if (people == 4) "yo-nin" else "$peopleR-nin"
                    DialogueScenario("Tuổi và gia đình ${index + 1}", listOf(
                        e("なんさいですか。", "Nan-sai desu ka.", "Bạn bao nhiêu tuổi?"),
                        e("${ageText}です。", "$ageRead desu.", "Tôi $age tuổi."),
                        e("かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?"),
                        e("${peopleJ}にんです。", "$peopleRead desu.", "Có $people người."),
                    ))
                }
                else -> {
                    val members = listOf(
                        Triple("ちち", "chichi", "bố tôi"), Triple("はは", "haha", "mẹ tôi"),
                        Triple("あに", "ani", "anh tôi"), Triple("あね", "ane", "chị tôi"),
                        Triple("おとうと", "otouto", "em trai tôi"),
                    )
                    val member = members[index % members.size]
                    val job = listOf(
                        Triple("いしゃ", "isha", "bác sĩ"), Triple("きょうし", "kyoushi", "giáo viên"),
                        Triple("エンジニア", "enjinia", "kỹ sư"), Triple("かいしゃいん", "kaishain", "nhân viên công ty"),
                        Triple("ぎんこういん", "ginkouin", "nhân viên ngân hàng"),
                    )[index % 5]
                    val age = 25 + index
                    val (ageText, ageRead) = ageExpression(age)
                    DialogueScenario("Giới thiệu gia đình ${index + 1}", listOf(
                        e("このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?"),
                        e("わたしの${member.first}です。", "Watashi no ${member.second} desu.", "Đây là ${member.third}."),
                        e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Người đó làm nghề gì?"),
                        e("${job.first}です。", "${job.second} desu.", "Là ${job.third}."),
                        e("${ageText}です。", "$ageRead desu.", "Người đó $age tuổi."),
                    ))
                }
            }
        }
    }
}
