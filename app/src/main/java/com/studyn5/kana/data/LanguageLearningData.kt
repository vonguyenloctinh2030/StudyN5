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
    val questionAnswer: GrammarQuestionAnswer? = null,
)

data class LanguageExample(val japanese: String, val romaji: String, val meaning: String)

data class GrammarQuestionAnswer(
    val question: LanguageExample,
    val answer: LanguageExample,
)

data class DialogueScenario(val title: String, val lines: List<LanguageExample>)

data class VocabularyLibraryItem(val group: String, val entry: VocabularyEntry)

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

    private fun qa(
        qj: String,
        qr: String,
        qm: String,
        aj: String,
        ar: String,
        am: String,
    ) = GrammarQuestionAnswer(e(qj, qr, qm), e(aj, ar, am))

    val lessons: List<LanguageLesson> by lazy {
        listOf(
            LanguageLesson(1, "Chào hỏi & giới thiệu", "Làm quen và giới thiệu bản thân", "あ", greetings, greetingGrammar),
            LanguageLesson(2, "Quốc gia, nghề nghiệp & ngôn ngữ", "Nói về quê quán, công việc và khả năng", "国", identityWords, identityGrammar),
            LanguageLesson(3, "Số đếm 1–100", "Đếm số, tuổi và số người", "百", numberWords, numberGrammar),
            LanguageLesson(4, "Gia đình, tuổi & nơi sống", "Hỏi và giới thiệu đầy đủ về gia đình", "家", familyWords, familyGrammar),
            LanguageLesson(5, "Đồ ăn, thức uống & sở thích", "Nói món yêu thích và thói quen ăn sáng", "食", foodWords, foodGrammar),
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
        GrammarPattern("Khẳng định danh tính", "N1 は N2 です", "Nêu N1 là N2. Trợ từ は trong mẫu này đọc là ‘wa’.", listOf(e("わたしはティンです。", "Watashi wa Tinh desu.", "Tôi là Tính."), e("わたしはエンジニアです。", "Watashi wa enjinia desu.", "Tôi là kỹ sư.")), qa("おなまえはなんですか。", "Onamae wa nan desu ka.", "Bạn tên là gì?", "わたしはティンです。", "Watashi wa Tinh desu.", "Tôi là Tính.")),
        GrammarPattern("Phủ định danh tính", "N1 は N2 じゃないです", "Nói N1 không phải là N2. Đây là cách nói lịch sự thông dụng.", listOf(e("わたしはがくせいじゃないです。", "Watashi wa gakusei ja nai desu.", "Tôi không phải là sinh viên."), e("キムさんはせんせいじゃないです。", "Kimu-san wa sensei ja nai desu.", "Chị Kim không phải là giáo viên.")), qa("がくせいですか。", "Gakusei desu ka.", "Bạn là sinh viên phải không?", "いいえ、がくせいじゃないです。", "Iie, gakusei ja nai desu.", "Không, tôi không phải là sinh viên.")),
        GrammarPattern("Câu hỏi phải không", "N1 は N2 ですか", "Thêm か cuối câu để tạo câu hỏi.", listOf(e("ティンさんはエンジニアですか。", "Tinh-san wa enjinia desu ka.", "Anh Tính là kỹ sư phải không?"), e("マイさんはがくせいですか。", "Mai-san wa gakusei desu ka.", "Bạn Mai là sinh viên phải không?")), qa("ティンさんはエンジニアですか。", "Tinh-san wa enjinia desu ka.", "Anh Tính là kỹ sư phải không?", "はい、エンジニアです。", "Hai, enjinia desu.", "Vâng, tôi là kỹ sư.")),
        GrammarPattern("Hỏi thông tin", "N1 は なんですか", "Dùng なん để hỏi N1 là gì.", listOf(e("おなまえはなんですか。", "Onamae wa nan desu ka.", "Bạn tên là gì?"), e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?")), qa("おなまえはなんですか。", "Onamae wa nan desu ka.", "Bạn tên là gì?", "なまえはマイです。", "Namae wa Mai desu.", "Tên tôi là Mai.")),
        GrammarPattern("Sở hữu", "N1 の N2", "の nối hai danh từ; N2 thuộc về hoặc liên quan đến N1.", listOf(e("わたしのなまえはティンです。", "Watashi no namae wa Tinh desu.", "Tên tôi là Tính."), e("わたしのくにはベトナムです。", "Watashi no kuni wa Betonamu desu.", "Đất nước của tôi là Việt Nam.")), qa("これはだれのかばんですか。", "Kore wa dare no kaban desu ka.", "Đây là cặp của ai?", "わたしのかばんです。", "Watashi no kaban desu.", "Đây là cặp của tôi.")),
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
            v("わかります", "wakarimasu", "hiểu", "Khả năng", "にほんごがすこしわかります。", "Nihon-go ga sukoshi wakarimasu.", "Tôi hiểu một chút tiếng Nhật."),
            v("わかりません", "wakarimasen", "không hiểu", "Khả năng", "すみません、わかりません。", "Sumimasen, wakarimasen.", "Xin lỗi, tôi không hiểu."),
            v("すこし", "sukoshi", "một chút", "Mức độ", "にほんごがすこしできます。", "Nihon-go ga sukoshi dekimasu.", "Tôi biết một chút tiếng Nhật."),
            v("よく", "yoku", "tốt, rõ; thường", "Mức độ", "えいごがよくわかります。", "Eigo ga yoku wakarimasu.", "Tôi hiểu tiếng Anh khá rõ."),
            v("あまり", "amari", "không… lắm", "Mức độ", "にほんごはあまりできません。", "Nihon-go wa amari dekimasen.", "Tôi không giỏi tiếng Nhật lắm."),
            v("ぜんぜん", "zenzen", "hoàn toàn không", "Mức độ", "ドイツごはぜんぜんわかりません。", "Doitsu-go wa zenzen wakarimasen.", "Tôi hoàn toàn không hiểu tiếng Đức."),
        ))
    }

    private val identityGrammar = listOf(
        GrammarPattern("Hỏi xuất thân", "どちらからですか", "Cách hỏi lịch sự ‘Bạn đến từ đâu?’; trả lời bằng địa danh + からです.", listOf(e("どちらからですか。", "Dochira kara desu ka.", "Bạn đến từ đâu?"), e("ベトナムからです。", "Betonamu kara desu.", "Tôi đến từ Việt Nam.")), qa("どちらからですか。", "Dochira kara desu ka.", "Bạn đến từ đâu?", "ベトナムからです。", "Betonamu kara desu.", "Tôi đến từ Việt Nam.")),
        GrammarPattern("Hỏi nghề nghiệp", "おしごとは なんですか", "お làm câu hỏi lịch sự hơn; なん nghĩa là gì.", listOf(e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"), e("ソフトウェアエンジニアです。", "Sofutowea enjinia desu.", "Tôi là kỹ sư phần mềm.")), qa("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?", "ソフトウェアエンジニアです。", "Sofutowea enjinia desu.", "Tôi là kỹ sư phần mềm.")),
        GrammarPattern("Quốc tịch", "Tên nước + じん", "Gắn ～じん sau tên nước để nói quốc tịch hoặc người của nước đó.", listOf(e("わたしはベトナムじんです。", "Watashi wa Betonamu-jin desu.", "Tôi là người Việt Nam."), e("キムさんはかんこくじんです。", "Kimu-san wa Kankoku-jin desu.", "Chị Kim là người Hàn Quốc.")), qa("どこのくにのひとですか。", "Doko no kuni no hito desu ka.", "Bạn là người nước nào?", "ベトナムじんです。", "Betonamu-jin desu.", "Tôi là người Việt Nam.")),
        GrammarPattern("Ngôn ngữ", "Tên nước + ご", "Nhiều tên ngôn ngữ dùng ～ご. Tiếng Anh là えいご, không phải アメリカご hay イギリスご.", listOf(e("にほんごをべんきょうします。", "Nihon-go o benkyou shimasu.", "Tôi học tiếng Nhật."), e("えいごができます。", "Eigo ga dekimasu.", "Tôi biết tiếng Anh.")), qa("なんごをべんきょうしますか。", "Nan-go o benkyou shimasu ka.", "Bạn học ngôn ngữ nào?", "にほんごをべんきょうします。", "Nihon-go o benkyou shimasu.", "Tôi học tiếng Nhật.")),
        GrammarPattern("Nói khả năng", "N1 は N2 が できます", "N2 là ngôn ngữ hoặc kỹ năng mà N1 có thể thực hiện.", listOf(e("わたしはベトナムごができます。", "Watashi wa Betonamu-go ga dekimasu.", "Tôi biết tiếng Việt."), e("ティンさんはにほんごができますか。", "Tinh-san wa Nihon-go ga dekimasu ka.", "Anh Tính biết tiếng Nhật không?")), qa("にほんごができますか。", "Nihon-go ga dekimasu ka.", "Bạn biết tiếng Nhật không?", "はい、すこしできます。", "Hai, sukoshi dekimasu.", "Vâng, tôi biết một chút.")),
        GrammarPattern("Phủ định khả năng", "N2 は できません", "Dùng できません để nói không thể hoặc chưa biết một kỹ năng.", listOf(e("いいえ、できません。", "Iie, dekimasen.", "Không, tôi không biết."), e("かんこくごはできません。", "Kankoku-go wa dekimasen.", "Tôi không biết tiếng Hàn.")), qa("かんこくごができますか。", "Kankoku-go ga dekimasu ka.", "Bạn biết tiếng Hàn không?", "いいえ、できません。", "Iie, dekimasen.", "Không, tôi không biết.")),
        GrammarPattern("Nói mức độ khả năng", "すこし／あまり／ぜんぜん + できます／できません", "すこし đi với câu khẳng định; あまり và ぜんぜん thường đi với câu phủ định.", listOf(e("にほんごがすこしできます。", "Nihon-go ga sukoshi dekimasu.", "Tôi biết một chút tiếng Nhật."), e("にほんごはあまりできません。", "Nihon-go wa amari dekimasen.", "Tôi không giỏi tiếng Nhật lắm."), e("ドイツごはぜんぜんできません。", "Doitsu-go wa zenzen dekimasen.", "Tôi hoàn toàn không biết tiếng Đức.")), qa("にほんごがどのくらいできますか。", "Nihon-go ga dono kurai dekimasu ka.", "Bạn biết tiếng Nhật ở mức nào?", "すこしできます。", "Sukoshi dekimasu.", "Tôi biết một chút.")),
        GrammarPattern("Nói hiểu hoặc không hiểu", "N が わかります／わかりません", "Dùng わかります khi hiểu nội dung; khác với できます là có khả năng thực hiện hoặc sử dụng.", listOf(e("えいごがわかります。", "Eigo ga wakarimasu.", "Tôi hiểu tiếng Anh."), e("すみません、にほんごがわかりません。", "Sumimasen, Nihon-go ga wakarimasen.", "Xin lỗi, tôi không hiểu tiếng Nhật.")), qa("にほんごがわかりますか。", "Nihon-go ga wakarimasu ka.", "Bạn hiểu tiếng Nhật không?", "はい、すこしわかります。", "Hai, sukoshi wakarimasu.", "Vâng, tôi hiểu một chút.")),
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
        val kana = hiraganaNumber(age)
        val romaji = japaneseNumber(age).second
        if (age == 100) return "ひゃくさい" to "hyaku-sai"
        return when (age % 10) {
            1 -> kana.removeSuffix("いち") + "いっさい" to romaji.removeSuffix("ichi") + "issai"
            8 -> kana.removeSuffix("はち") + "はっさい" to romaji.removeSuffix("hachi") + "hassai"
            0 -> kana.removeSuffix("じゅう") + "じゅっさい" to romaji.removeSuffix("juu") + "jussai"
            else -> "${kana}さい" to "$romaji-sai"
        }
    }

    private fun peopleExpression(people: Int): Pair<String, String> = when (people) {
        1 -> "ひとり" to "hitori"
        2 -> "ふたり" to "futari"
        else -> {
            val kana = hiraganaNumber(people)
            val romaji = japaneseNumber(people).second
            if (people % 10 == 4) {
                kana.removeSuffix("よん") + "よにん" to romaji.removeSuffix("yon") + "yo-nin"
            } else {
                "${kana}にん" to "$romaji-nin"
            }
        }
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
        v("ごにん", "go-nin", "năm người", "Đếm người", "かぞくはごにんです。", "Kazoku wa go-nin desu.", "Gia đình có năm người."),
        v("ろくにん", "roku-nin", "sáu người", "Đếm người", "かぞくはろくにんです。", "Kazoku wa roku-nin desu.", "Gia đình có sáu người."),
        v("ななにん", "nana-nin", "bảy người", "Đếm người", "グループはななにんです。", "Guruupu wa nana-nin desu.", "Nhóm có bảy người."),
        v("はちにん", "hachi-nin", "tám người", "Đếm người", "はちにんいます。", "Hachi-nin imasu.", "Có tám người."),
        v("きゅうにん", "kyuu-nin", "chín người", "Đếm người", "クラスにきゅうにんいます。", "Kurasu ni kyuu-nin imasu.", "Trong lớp có chín người."),
        v("じゅうにん", "juu-nin", "mười người", "Đếm người", "チームはじゅうにんです。", "Chiimu wa juu-nin desu.", "Đội có mười người."),
        v("なんにん", "nan-nin", "bao nhiêu người", "Đếm người", "かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình có bao nhiêu người?"),
        v("さい／～さい", "sai / ~sai", "… tuổi", "Tuổi", "わたしはさんじゅっさいです。", "Watashi wa sanjussai desu.", "Tôi 30 tuổi."),
        v("なんさい", "nan-sai", "bao nhiêu tuổi", "Tuổi", "なんさいですか。", "Nan-sai desu ka.", "Bạn bao nhiêu tuổi?"),
        v("おいくつ", "oikutsu", "bao nhiêu tuổi (lịch sự)", "Tuổi", "おいくつですか。", "Oikutsu desu ka.", "Bạn bao nhiêu tuổi?"),
        v("じゅっさい", "jussai", "10 tuổi", "Tuổi", "おとうとはじゅっさいです。", "Otouto wa jussai desu.", "Em trai tôi 10 tuổi.", reference = "十歳"),
        v("じゅういっさい", "juu-issai", "11 tuổi", "Tuổi", "むすこはじゅういっさいです。", "Musuko wa juu-issai desu.", "Con trai tôi 11 tuổi.", reference = "十一歳"),
        v("じゅうごさい", "juu-go-sai", "15 tuổi", "Tuổi", "いもうとはじゅうごさいです。", "Imouto wa juu-go-sai desu.", "Em gái tôi 15 tuổi.", reference = "十五歳"),
        v("じゅうはっさい", "juu-hassai", "18 tuổi", "Tuổi", "あにはじゅうはっさいです。", "Ani wa juu-hassai desu.", "Anh tôi 18 tuổi.", reference = "十八歳"),
        v("はたち", "hatachi", "20 tuổi", "Tuổi", "わたしははたちです。", "Watashi wa hatachi desu.", "Tôi 20 tuổi."),
        v("にじゅうはっさい", "nijuu-hassai", "28 tuổi", "Tuổi", "あねはにじゅうはっさいです。", "Ane wa nijuu-hassai desu.", "Chị tôi 28 tuổi.", reference = "二十八歳"),
    )

    private val numberGrammar = listOf(
        GrammarPattern("Tạo số 11–19", "じゅう + số", "10 đứng trước số hàng đơn vị: 11 = じゅういち.", listOf(e("じゅういち", "juu-ichi", "11"), e("じゅうきゅう", "juu-kyuu", "19")), qa("これはなんばんですか。", "Kore wa nan-ban desu ka.", "Đây là số mấy?", "じゅういちばんです。", "Juu-ichi-ban desu.", "Đây là số 11.")),
        GrammarPattern("Tạo số tròn chục", "số + じゅう", "Số hàng chục đứng trước じゅう: 30 = さんじゅう.", listOf(e("さんじゅう", "san-juu", "30"), e("きゅうじゅう", "kyuu-juu", "90")), qa("なんばんですか。", "Nan-ban desu ka.", "Số mấy?", "さんじゅうばんです。", "San-juu-ban desu.", "Số 30.")),
        GrammarPattern("Tạo số ghép", "hàng chục + じゅう + hàng đơn vị", "Ghép hàng chục và hàng đơn vị theo thứ tự.", listOf(e("さんじゅうろく", "san-juu-roku", "36"), e("ななじゅうよん", "nana-juu-yon", "74")), qa("なんページですか。", "Nan peeji desu ka.", "Trang bao nhiêu?", "さんじゅうろくページです。", "San-juu-roku peeji desu.", "Trang 36.")),
        GrammarPattern("Hỏi và nói tuổi", "おいくつですか／～さいです", "おいくつですか lịch sự hơn なんさいですか. Thêm ～さい sau số tuổi; 20 tuổi đọc đặc biệt là はたち.", listOf(e("なんさいですか。", "Nan-sai desu ka.", "Bạn bao nhiêu tuổi?"), e("にじゅうろくさいです。", "Nijuu-roku-sai desu.", "Tôi 26 tuổi."), e("じゅうはっさい", "juu-hassai", "18 tuổi có biến âm はち → はっ"), e("はたち", "hatachi", "20 tuổi, cách đọc đặc biệt")), qa("おいくつですか。", "Oikutsu desu ka.", "Bạn bao nhiêu tuổi?", "にじゅうはっさいです。", "Nijuu-hassai desu.", "Tôi 28 tuổi.")),
        GrammarPattern("Đếm người", "số + にん", "Một người là ひとり, hai người là ふたり, bốn người là よにん; các số còn lại trong bài dùng số + にん.", listOf(e("ひとり", "hitori", "một người"), e("ふたり", "futari", "hai người"), e("よにん", "yo-nin", "bốn người"), e("ななにん", "nana-nin", "bảy người")), qa("グループはなんにんですか。", "Guruupu wa nan-nin desu ka.", "Nhóm có bao nhiêu người?", "はちにんです。", "Hachi-nin desu.", "Có tám người.")),
        GrammarPattern("Hỏi số người", "なんにんですか／～にんです", "Dùng なんにん để hỏi có bao nhiêu người và trả lời bằng số đếm người.", listOf(e("かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?"), e("ろくにんです。", "Roku-nin desu.", "Có sáu người.")), qa("かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?", "よにんです。", "Yo-nin desu.", "Gia đình tôi có bốn người.")),
    )

    private val familyWords = listOf(
        v("かぞく", "kazoku", "gia đình", "Chung", "わたしのかぞくはろくにんです。", "Watashi no kazoku wa roku-nin desu.", "Gia đình tôi có sáu người."),
        v("ごかぞく", "gokazoku", "gia đình của người khác (lịch sự)", "Gia đình người khác", "ごかぞくはなんにんですか。", "Gokazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?", reference = "ご家族"),
        v("りょうしん", "ryoushin", "bố mẹ", "Chung", "りょうしんはベトナムにいます。", "Ryoushin wa Betonamu ni imasu.", "Bố mẹ tôi ở Việt Nam."),
        v("ごりょうしん", "goryoushin", "bố mẹ của người khác (lịch sự)", "Gia đình người khác", "ごりょうしんはどこにすんでいますか。", "Goryoushin wa doko ni sunde imasu ka.", "Bố mẹ bạn sống ở đâu?", reference = "ご両親"),
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
        v("おとうとさん", "otouto-san", "em trai của người khác", "Gia đình người khác", "おとうとさんはなんさいですか。", "Otouto-san wa nan-sai desu ka.", "Em trai bạn bao nhiêu tuổi?"),
        v("いもうとさん", "imouto-san", "em gái của người khác", "Gia đình người khác", "いもうとさんはがくせいですか。", "Imouto-san wa gakusei desu ka.", "Em gái bạn là học sinh phải không?"),
        v("ごしゅじん", "goshujin", "chồng của người khác", "Gia đình người khác", "ごしゅじんはおいくつですか。", "Goshujin wa oikutsu desu ka.", "Chồng bạn bao nhiêu tuổi?"),
        v("おくさん", "okusan", "vợ của người khác", "Gia đình người khác", "おくさんはきょうしです。", "Okusan wa kyoushi desu.", "Vợ anh ấy là giáo viên."),
        v("おこさん", "okosan", "con của người khác", "Gia đình người khác", "おこさんはなんにんいますか。", "Okosan wa nan-nin imasu ka.", "Bạn có mấy người con?"),
        v("むすこさん", "musuko-san", "con trai của người khác", "Gia đình người khác", "むすこさんはじゅういっさいです。", "Musuko-san wa juu-issai desu.", "Con trai bạn 11 tuổi."),
        v("むすめさん", "musume-san", "con gái của người khác", "Gia đình người khác", "むすめさんはかわいいですね。", "Musume-san wa kawaii desu ne.", "Con gái bạn đáng yêu nhỉ."),
        v("おとこのひと", "otoko no hito", "người đàn ông", "Người", "あのおとこのひとはだれですか。", "Ano otoko no hito wa dare desu ka.", "Người đàn ông kia là ai?", reference = "男の人"),
        v("おんなのひと", "onna no hito", "người phụ nữ", "Người", "あのおんなのひとはわたしのあねです。", "Ano onna no hito wa watashi no ane desu.", "Người phụ nữ kia là chị tôi.", reference = "女の人"),
        v("おとこのこ", "otoko no ko", "bé trai", "Người", "このおとこのこはむすこです。", "Kono otoko no ko wa musuko desu.", "Bé trai này là con trai tôi.", reference = "男の子"),
        v("おんなのこ", "onna no ko", "bé gái", "Người", "このおんなのこはむすめです。", "Kono onna no ko wa musume desu.", "Bé gái này là con gái tôi.", reference = "女の子"),
        v("このひと", "kono hito", "người này", "Người", "このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?", reference = "この人"),
        v("このこ", "kono ko", "đứa trẻ này", "Người", "このこはだれですか。", "Kono ko wa dare desu ka.", "Đứa trẻ này là ai?", reference = "この子"),
        v("かっこいい", "kakkoii", "ngầu, đẹp trai, phong độ", "Mô tả người", "おにいさんはかっこいいですね。", "Oniisan wa kakkoii desu ne.", "Anh trai bạn trông phong độ nhỉ."),
        v("かわいい", "kawaii", "đáng yêu, dễ thương", "Mô tả người", "むすめさんはかわいいですね。", "Musume-san wa kawaii desu ne.", "Con gái bạn đáng yêu nhỉ."),
        v("きれい", "kirei", "đẹp; sạch", "Mô tả người", "おねえさんはきれいですね。", "Oneesan wa kirei desu ne.", "Chị gái bạn đẹp nhỉ."),
        v("わかい", "wakai", "trẻ, trẻ tuổi", "Mô tả người", "おとうさんはわかいですね。", "Otousan wa wakai desu ne.", "Bố bạn trông trẻ nhỉ."),
        v("すんでいます", "sunde imasu", "đang sống, sinh sống", "Sinh sống", "わたしはベトナムにすんでいます。", "Watashi wa Betonamu ni sunde imasu.", "Tôi sống ở Việt Nam.", reference = "住んでいます"),
        v("ひとりで", "hitori de", "một mình", "Sinh sống", "ひとりでほっかいどうにすんでいます。", "Hitori de Hokkaidou ni sunde imasu.", "Tôi sống một mình ở Hokkaido."),
        v("どこにすんでいますか", "doko ni sunde imasu ka", "Bạn sống ở đâu?", "Cụm hỏi", "どこにすんでいますか。ホーチミンにすんでいます。", "Doko ni sunde imasu ka. Hoo Chi Min ni sunde imasu.", "Bạn sống ở đâu? Tôi sống ở Hồ Chí Minh."),
        v("とうきょう", "Toukyou", "Tokyo", "Địa điểm", "とうきょうにすんでいます。", "Toukyou ni sunde imasu.", "Tôi sống ở Tokyo.", reference = "東京"),
        v("おおさか", "Oosaka", "Osaka", "Địa điểm", "わたしたちはおおさかにすんでいます。", "Watashitachi wa Oosaka ni sunde imasu.", "Chúng tôi sống ở Osaka.", reference = "大阪"),
        v("ほっかいどう", "Hokkaidou", "Hokkaido", "Địa điểm", "ほっかいどうにすんでいます。", "Hokkaidou ni sunde imasu.", "Tôi sống ở Hokkaido.", reference = "北海道"),
        v("パリ", "Pari", "Paris", "Địa điểm", "フランスのパリにすんでいます。", "Furansu no Pari ni sunde imasu.", "Tôi sống ở Paris, Pháp."),
        v("ホーチミン", "Hoo Chi Min", "Hồ Chí Minh", "Địa điểm", "ホーチミンにすんでいます。", "Hoo Chi Min ni sunde imasu.", "Tôi sống ở Hồ Chí Minh."),
        v("ドンタップ", "Dontappu", "Đồng Tháp", "Địa điểm", "ちちとはははドンタップにすんでいます。", "Chichi to haha wa Dontappu ni sunde imasu.", "Bố và mẹ tôi sống ở Đồng Tháp."),
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
        GrammarPattern("Hỏi số người trong gia đình", "かぞくは なんにんですか／～にんです", "Dùng なんにん để hỏi quy mô gia đình và trả lời bằng số đếm người.", listOf(e("わたしのかぞくはろくにんです。", "Watashi no kazoku wa roku-nin desu.", "Gia đình tôi có sáu người."), e("マイさんのかぞくはよにんです。", "Mai-san no kazoku wa yo-nin desu.", "Gia đình bạn Mai có bốn người.")), qa("かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?", "よにんです。", "Yo-nin desu.", "Gia đình tôi có bốn người.")),
        GrammarPattern("Hỏi gia đình có những ai", "ごかぞくには だれが いますか／N が います", "います dùng để nói sự tồn tại của người hoặc động vật; が đánh dấu người đang có mặt.", listOf(e("こどもがふたりいます。", "Kodomo ga futari imasu.", "Tôi có hai người con."), e("あねがいます。", "Ane ga imasu.", "Tôi có chị gái.")), qa("ごかぞくにはだれがいますか。", "Gokazoku ni wa dare ga imasu ka.", "Gia đình bạn có những ai?", "ちちと ははと おとうとがいます。", "Chichi to haha to otouto ga imasu.", "Có bố, mẹ và em trai.")),
        GrammarPattern("Liệt kê thành viên", "N1 と N2 と N3", "と nối các danh từ trong một danh sách xác định, mang nghĩa ‘và’.", listOf(e("ちちと ははがいます。", "Chichi to haha ga imasu.", "Tôi có bố và mẹ."), e("ちちと ははと おとうとと わたしです。", "Chichi to haha to otouto to watashi desu.", "Gồm bố, mẹ, em trai và tôi.")), qa("かぞくはだれとだれですか。", "Kazoku wa dare to dare desu ka.", "Gia đình bạn gồm những ai?", "ちちと ははと あねと わたしです。", "Chichi to haha to ane to watashi desu.", "Gồm bố, mẹ, chị gái và tôi.")),
        GrammarPattern("Hỏi người trong ảnh", "このひとは だれですか", "このひと là ‘người này’; với trẻ em có thể dùng このこ. Lịch sự hơn だれ có thể dùng どなた.", listOf(e("このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?"), e("このこはわたしのむすめです。", "Kono ko wa watashi no musume desu.", "Đứa trẻ này là con gái tôi.")), qa("このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?", "わたしのあねです。", "Watashi no ane desu.", "Đây là chị gái tôi.")),
        GrammarPattern("Phân biệt gia đình mình/người khác", "ちち ↔ おとうさん", "Dùng ちち, はは, おっと, つま… khi nói về gia đình mình; dùng おとうさん, おかあさん, ごしゅじん, おくさん… khi nói với hoặc về gia đình người khác.", listOf(e("ちちはいしゃです。", "Chichi wa isha desu.", "Bố tôi là bác sĩ."), e("おとうさんはいしゃですか。", "Otousan wa isha desu ka.", "Bố bạn là bác sĩ phải không?")), qa("おとうさんはおいくつですか。", "Otousan wa oikutsu desu ka.", "Bố bạn bao nhiêu tuổi?", "ちちはごじゅっさいです。", "Chichi wa gojussai desu.", "Bố tôi 50 tuổi.")),
        GrammarPattern("Hỏi và trả lời tuổi", "N は おいくつですか／～さいです", "おいくつですか là cách hỏi lịch sự; なんさいですか trực tiếp hơn và thường dùng với người nhỏ tuổi hoặc khi thân mật.", listOf(e("いもうとはじゅうごさいです。", "Imouto wa juu-go-sai desu.", "Em gái tôi 15 tuổi."), e("あねはにじゅうはっさいです。", "Ane wa nijuu-hassai desu.", "Chị tôi 28 tuổi.")), qa("おにいさんはおいくつですか。", "Oniisan wa oikutsu desu ka.", "Anh trai bạn bao nhiêu tuổi?", "にじゅうはっさいです。", "Nijuu-hassai desu.", "Anh ấy 28 tuổi.")),
        GrammarPattern("Hỏi nghề nghiệp của người thân", "N の おしごとは なんですか", "Đặt người thân trước の để hỏi nghề nghiệp của người đó.", listOf(e("ちちはかいしゃいんです。", "Chichi wa kaishain desu.", "Bố tôi là nhân viên công ty."), e("あねはきょうしです。", "Ane wa kyoushi desu.", "Chị tôi là giáo viên.")), qa("おとうさんのおしごとはなんですか。", "Otousan no oshigoto wa nan desu ka.", "Bố bạn làm nghề gì?", "ちちはソフトウェアエンジニアです。", "Chichi wa sofutowea enjinia desu.", "Bố tôi là kỹ sư phần mềm.")),
        GrammarPattern("Hỏi và nói nơi sinh sống", "N は địa điểm に すんでいます", "に đánh dấu nơi sinh sống; すんでいます diễn tả đang sống hoặc cư trú tại đó.", listOf(e("わたしはベトナムにすんでいます。", "Watashi wa Betonamu ni sunde imasu.", "Tôi sống ở Việt Nam."), e("いもうとはおおさかにすんでいます。", "Imouto wa Oosaka ni sunde imasu.", "Em gái tôi sống ở Osaka."), e("ひとりでほっかいどうにすんでいます。", "Hitori de Hokkaidou ni sunde imasu.", "Tôi sống một mình ở Hokkaido.")), qa("どこにすんでいますか。", "Doko ni sunde imasu ka.", "Bạn sống ở đâu?", "ホーチミンにすんでいます。", "Hoo Chi Min ni sunde imasu.", "Tôi sống ở Hồ Chí Minh.")),
        GrammarPattern("Hỏi người thân sống ở đâu", "N は どこに すんでいますか", "Thay N bằng người thân cần hỏi; trả lời bằng địa điểm + にすんでいます.", listOf(e("ちちとはははドンタップにすんでいます。", "Chichi to haha wa Dontappu ni sunde imasu.", "Bố và mẹ tôi sống ở Đồng Tháp."), e("わたしとおとうとはホーチミンにすんでいます。", "Watashi to otouto wa Hoo Chi Min ni sunde imasu.", "Tôi và em trai sống ở Hồ Chí Minh.")), qa("ごりょうしんはどこにすんでいますか。", "Goryoushin wa doko ni sunde imasu ka.", "Bố mẹ bạn sống ở đâu?", "ドンタップにすんでいます。", "Dontappu ni sunde imasu.", "Bố mẹ tôi sống ở Đồng Tháp.")),
        GrammarPattern("Nhận xét ngoại hình", "N は tính từ です／ですね", "ですね tạo lời nhận xét mềm và mời người nghe đồng tình. Không thêm お trước かわいい.", listOf(e("おにいさんはかっこいいですね。", "Oniisan wa kakkoii desu ne.", "Anh trai bạn trông phong độ nhỉ."), e("むすめさんはかわいいですね。", "Musume-san wa kawaii desu ne.", "Con gái bạn đáng yêu nhỉ."), e("おねえさんはきれいですね。", "Oneesan wa kirei desu ne.", "Chị gái bạn đẹp nhỉ.")), qa("おとうとはどんなひとですか。", "Otouto wa donna hito desu ka.", "Em trai bạn là người thế nào?", "わかくて、かっこいいです。", "Wakakute, kakkoii desu.", "Em ấy trẻ và phong độ.")),
    )

    private val foodWords = listOf(
        v("りょうり", "ryouri", "món ăn đã nấu; việc nấu ăn", "Khái niệm", "すきなりょうりはなんですか。", "Suki na ryouri wa nan desu ka.", "Món ăn bạn thích là gì?", reference = "料理"),
        v("たべもの", "tabemono", "đồ ăn, thức ăn", "Khái niệm", "すきなたべものはなんですか。", "Suki na tabemono wa nan desu ka.", "Món ăn bạn thích là gì?", reference = "食べ物"),
        v("のみもの", "nomimono", "đồ uống", "Khái niệm", "のみものはなにがいいですか。", "Nomimono wa nani ga ii desu ka.", "Bạn muốn đồ uống gì?", reference = "飲み物"),
        v("すきなりょうり", "suki na ryouri", "món ăn yêu thích", "Sở thích", "すきなりょうりはカレーです。", "Suki na ryouri wa karee desu.", "Món ăn yêu thích của tôi là cà ri.", reference = "好きな料理"),
        v("すきなたべもの", "suki na tabemono", "đồ ăn yêu thích", "Sở thích", "すきなたべものはなんですか。", "Suki na tabemono wa nan desu ka.", "Đồ ăn bạn yêu thích là gì?", reference = "好きな食べ物"),
        v("すきなのみもの", "suki na nomimono", "đồ uống yêu thích", "Sở thích", "すきなのみものはおちゃです。", "Suki na nomimono wa ocha desu.", "Đồ uống yêu thích của tôi là trà.", reference = "好きな飲み物"),
        v("にく", "niku", "thịt", "Đồ ăn", "わたしはにくがすきです。", "Watashi wa niku ga suki desu.", "Tôi thích thịt.", reference = "肉"),
        v("さかな", "sakana", "cá", "Đồ ăn", "さかなをたべます。", "Sakana o tabemasu.", "Tôi ăn cá.", reference = "魚"),
        v("やさい", "yasai", "rau củ", "Đồ ăn", "まいにちやさいをたべます。", "Mainichi yasai o tabemasu.", "Tôi ăn rau mỗi ngày.", reference = "野菜"),
        v("くだもの", "kudamono", "trái cây", "Đồ ăn", "あさ、くだものをたべます。", "Asa, kudamono o tabemasu.", "Buổi sáng tôi ăn trái cây.", reference = "果物"),
        v("たまご", "tamago", "trứng", "Đồ ăn", "あさごはんにたまごをたべます。", "Asagohan ni tamago o tabemasu.", "Tôi ăn trứng vào bữa sáng.", reference = "卵"),
        v("パン", "pan", "bánh mì", "Đồ ăn", "あさごはんはパンです。", "Asagohan wa pan desu.", "Bữa sáng của tôi là bánh mì."),
        v("ごはん", "gohan", "cơm; bữa ăn", "Đồ ăn", "ごはんをたべます。", "Gohan o tabemasu.", "Tôi ăn cơm.", reference = "ご飯"),
        v("みそしる", "misoshiru", "canh miso", "Đồ ăn", "ごはんをたべます。みそしるをのみます。", "Gohan o tabemasu. Misoshiru o nomimasu.", "Tôi ăn cơm và uống canh miso.", reference = "味噌汁"),
        v("すし", "sushi", "sushi", "Đồ ăn", "すしがだいすきです。", "Sushi ga daisuki desu.", "Tôi rất thích sushi.", reference = "寿司"),
        v("そば", "soba", "mì soba", "Đồ ăn", "ひるにそばをたべます。", "Hiru ni soba o tabemasu.", "Buổi trưa tôi ăn mì soba."),
        v("うどん", "udon", "mì udon", "Đồ ăn", "うどんはおいしいです。", "Udon wa oishii desu.", "Mì udon ngon."),
        v("ラーメン", "raamen", "mì ramen", "Đồ ăn", "ラーメンをよくたべます。", "Raamen o yoku tabemasu.", "Tôi thường ăn ramen."),
        v("カレー", "karee", "cơm cà ri", "Đồ ăn", "きょうはカレーをたべます。", "Kyou wa karee o tabemasu.", "Hôm nay tôi ăn cà ri."),
        v("ピザ", "piza", "pizza", "Đồ ăn", "ピザがすきです。", "Piza ga suki desu.", "Tôi thích pizza."),
        v("ハンバーガー", "hanbaagaa", "hamburger", "Đồ ăn", "ハンバーガーをたべます。", "Hanbaagaa o tabemasu.", "Tôi ăn hamburger."),
        v("ハンバーグ", "hanbaagu", "thịt băm áp chảo kiểu Nhật", "Đồ ăn", "ハンバーグがすきです。", "Hanbaagu ga suki desu.", "Tôi thích hamburger steak."),
        v("サンドイッチ", "sandoicchi", "bánh sandwich", "Đồ ăn", "あさ、サンドイッチをたべます。", "Asa, sandoicchi o tabemasu.", "Buổi sáng tôi ăn sandwich."),
        v("ぎょうざ", "gyouza", "há cảo, bánh xếp Nhật", "Đồ ăn", "ぎょうざをください。", "Gyouza o kudasai.", "Cho tôi há cảo."),
        v("やきにく", "yakiniku", "thịt nướng", "Đồ ăn", "やきにくがだいすきです。", "Yakiniku ga daisuki desu.", "Tôi rất thích thịt nướng.", reference = "焼き肉"),
        v("からあげ", "karaage", "gà chiên kiểu Nhật", "Đồ ăn", "からあげをたべます。", "Karaage o tabemasu.", "Tôi ăn gà chiên.", reference = "唐揚げ"),
        v("フライドポテト", "furaido poteto", "khoai tây chiên", "Đồ ăn", "フライドポテトはすきじゃないです。", "Furaido poteto wa suki ja nai desu.", "Tôi không thích khoai tây chiên."),
        v("おにぎり", "onigiri", "cơm nắm", "Đồ ăn", "おにぎりをふたつたべます。", "Onigiri o futatsu tabemasu.", "Tôi ăn hai cơm nắm."),
        v("てんぷら", "tenpura", "tempura", "Đồ ăn", "てんぷらはどうですか。", "Tenpura wa dou desu ka.", "Tempura thì sao?", reference = "天ぷら"),
        v("とんかつ", "tonkatsu", "thịt heo chiên xù", "Đồ ăn", "とんかつをおすすめします。", "Tonkatsu o osusume shimasu.", "Tôi đề xuất món tonkatsu."),
        v("ケーキ", "keeki", "bánh ngọt", "Đồ ăn", "ケーキをときどきたべます。", "Keeki o tokidoki tabemasu.", "Thỉnh thoảng tôi ăn bánh ngọt."),
        v("アイスクリーム", "aisu kuriimu", "kem", "Đồ ăn", "アイスクリームがすきです。", "Aisu kuriimu ga suki desu.", "Tôi thích kem."),
        v("コーヒー", "koohii", "cà phê", "Thức uống", "コーヒーをのみます。", "Koohii o nomimasu.", "Tôi uống cà phê."),
        v("こうちゃ", "koucha", "trà đen", "Thức uống", "こうちゃはいかがですか。", "Koucha wa ikaga desu ka.", "Bạn dùng trà đen nhé?", reference = "紅茶"),
        v("ぎゅうにゅう", "gyuunyuu", "sữa bò", "Thức uống", "あさ、ぎゅうにゅうをのみます。", "Asa, gyuunyuu o nomimasu.", "Buổi sáng tôi uống sữa.", reference = "牛乳"),
        v("おちゃ", "ocha", "trà xanh; trà", "Thức uống", "おちゃをどうぞ。", "Ocha o douzo.", "Mời bạn dùng trà.", reference = "お茶"),
        v("ジュース", "juusu", "nước ép; nước trái cây", "Thức uống", "オレンジジュースをのみます。", "Orenji juusu o nomimasu.", "Tôi uống nước cam."),
        v("みず", "mizu", "nước", "Thức uống", "みずをください。", "Mizu o kudasai.", "Cho tôi nước.", reference = "水"),
        v("ワイン", "wain", "rượu vang", "Thức uống", "ワインはのみません。", "Wain wa nomimasen.", "Tôi không uống rượu vang."),
        v("すき", "suki", "thích", "Sở thích", "なにがすきですか。", "Nani ga suki desu ka.", "Bạn thích gì?", reference = "好き"),
        v("だいすき", "daisuki", "rất thích", "Sở thích", "すしがだいすきです。", "Sushi ga daisuki desu.", "Tôi rất thích sushi.", reference = "大好き"),
        v("すきじゃない", "suki ja nai", "không thích", "Sở thích", "さかなはすきじゃないです。", "Sakana wa suki ja nai desu.", "Tôi không thích cá.", reference = "好きじゃない"),
        v("なに", "nani", "gì", "Từ để hỏi", "なにをたべますか。", "Nani o tabemasu ka.", "Bạn ăn gì?", reference = "何"),
        v("たべます", "tabemasu", "ăn", "Động từ", "パンをたべます。", "Pan o tabemasu.", "Tôi ăn bánh mì.", reference = "食べます"),
        v("たべません", "tabemasen", "không ăn", "Động từ", "あさはなにもたべません。", "Asa wa nani mo tabemasen.", "Buổi sáng tôi không ăn gì.", reference = "食べません"),
        v("のみます", "nomimasu", "uống", "Động từ", "おちゃをのみます。", "Ocha o nomimasu.", "Tôi uống trà.", reference = "飲みます"),
        v("のみません", "nomimasen", "không uống", "Động từ", "コーヒーはのみません。", "Koohii wa nomimasen.", "Tôi không uống cà phê.", reference = "飲みません"),
        v("すすめます", "susumemasu", "giới thiệu, đề xuất", "Động từ", "ともだちにおちゃをすすめます。", "Tomodachi ni ocha o susumemasu.", "Tôi giới thiệu trà cho bạn.", reference = "勧めます"),
        v("おすすめ", "osusume", "món/điều được đề xuất", "Giao tiếp", "おすすめはなんですか。", "Osusume wa nan desu ka.", "Món đề xuất là gì?"),
        v("いつも", "itsumo", "luôn luôn", "Tần suất", "いつもあさごはんをたべます。", "Itsumo asagohan o tabemasu.", "Tôi luôn ăn sáng."),
        v("よく", "yoku", "thường xuyên", "Tần suất", "パンをよくたべます。", "Pan o yoku tabemasu.", "Tôi thường ăn bánh mì."),
        v("ときどき", "tokidoki", "thỉnh thoảng", "Tần suất", "ときどきコーヒーをのみます。", "Tokidoki koohii o nomimasu.", "Thỉnh thoảng tôi uống cà phê."),
        v("あまり", "amari", "không thường; không… lắm", "Tần suất", "あさごはんはあまりたべません。", "Asagohan wa amari tabemasen.", "Tôi không thường ăn sáng."),
        v("ぜんぜん", "zenzen", "hoàn toàn không", "Tần suất", "ワインはぜんぜんのみません。", "Wain wa zenzen nomimasen.", "Tôi hoàn toàn không uống rượu vang."),
        v("あさ", "asa", "buổi sáng", "Thói quen", "あさ、みずをのみます。", "Asa, mizu o nomimasu.", "Buổi sáng tôi uống nước.", reference = "朝"),
        v("あさごはん", "asagohan", "bữa sáng", "Thói quen", "あさごはんはパンとたまごです。", "Asagohan wa pan to tamago desu.", "Bữa sáng là bánh mì và trứng.", reference = "朝ご飯"),
        v("ひるごはん", "hirugohan", "bữa trưa", "Thói quen", "ひるごはんにうどんをたべます。", "Hirugohan ni udon o tabemasu.", "Bữa trưa tôi ăn udon.", reference = "昼ご飯"),
        v("ばんごはん", "bangohan", "bữa tối", "Thói quen", "ばんごはんはカレーです。", "Bangohan wa karee desu.", "Bữa tối là cà ri.", reference = "晩ご飯"),
        v("しゅうかん", "shuukan", "thói quen", "Thói quen", "あさごはんのしゅうかんについてはなします。", "Asagohan no shuukan ni tsuite hanashimasu.", "Tôi nói về thói quen ăn sáng.", reference = "習慣"),
        v("みせ", "mise", "cửa hàng, quán", "Địa điểm ăn uống", "あのみせでひるごはんをたべます。", "Ano mise de hirugohan o tabemasu.", "Tôi ăn trưa ở cửa hàng kia.", reference = "店"),
        v("あのみせ", "ano mise", "cửa hàng/quán kia", "Địa điểm ăn uống", "あのみせはおいしいです。", "Ano mise wa oishii desu.", "Quán kia có đồ ăn ngon.", reference = "あの店"),
        v("どこでたべますか", "doko de tabemasu ka", "Bạn ăn ở đâu?", "Cụm hỏi", "ひるごはんはどこでたべますか。", "Hirugohan wa doko de tabemasu ka.", "Bạn ăn trưa ở đâu?", reference = "どこで食べますか"),
        v("おいしい", "oishii", "ngon", "Mô tả món", "このりょうりはおいしいです。", "Kono ryouri wa oishii desu.", "Món này ngon.", reference = "美味しい"),
        v("そうしましょう", "sou shimashou", "hãy làm như vậy; chốt vậy nhé", "Giao tiếp", "じゃ、そうしましょう。", "Ja, sou shimashou.", "Vậy chốt như thế nhé.", reference = "そうしましょう"),
        v("おねがいします", "onegai shimasu", "xin vui lòng; cho tôi xin", "Giao tiếp", "コーヒーをおねがいします。", "Koohii o onegai shimasu.", "Cho tôi cà phê.", reference = "お願いします"),
        v("けっこうです", "kekkou desu", "không, cảm ơn; đủ rồi", "Giao tiếp", "いいえ、けっこうです。", "Iie, kekkou desu.", "Không, cảm ơn."),
    )

    private val foodGrammar = listOf(
        GrammarPattern("Nói món mình thích", "S は N が すきです", "が đánh dấu đối tượng được thích. すき là tính từ-na, vì vậy không dùng を trong mẫu này.", listOf(e("わたしはにくがすきです。", "Watashi wa niku ga suki desu.", "Tôi thích thịt."), e("マイさんはすしがだいすきです。", "Mai-san wa sushi ga daisuki desu.", "Bạn Mai rất thích sushi.")), qa("なにがすきですか。", "Nani ga suki desu ka.", "Bạn thích gì?", "すしがすきです。", "Sushi ga suki desu.", "Tôi thích sushi.")),
        GrammarPattern("Nói món không thích", "N は すきじゃないです", "Đưa N làm chủ đề bằng は rồi dùng dạng phủ định すきじゃないです. Có thể nói mềm hơn bằng あまりすきじゃないです.", listOf(e("さかなはすきじゃないです。", "Sakana wa suki ja nai desu.", "Tôi không thích cá."), e("ワインはあまりすきじゃないです。", "Wain wa amari suki ja nai desu.", "Tôi không thích rượu vang lắm.")), qa("さかなはすきですか。", "Sakana wa suki desu ka.", "Bạn thích cá không?", "いいえ、すきじゃないです。", "Iie, suki ja nai desu.", "Không, tôi không thích.")),
        GrammarPattern("Hỏi và nói ăn gì", "N を たべます／なにを たべますか", "を đánh dấu món được ăn và đọc là ‘o’. Dùng なにを để hỏi ăn gì.", listOf(e("ごはんをたべます。", "Gohan o tabemasu.", "Tôi ăn cơm."), e("あさ、パンをたべます。", "Asa, pan o tabemasu.", "Buổi sáng tôi ăn bánh mì.")), qa("あさごはんになにをたべますか。", "Asagohan ni nani o tabemasu ka.", "Bữa sáng bạn ăn gì?", "パンとたまごをたべます。", "Pan to tamago o tabemasu.", "Tôi ăn bánh mì và trứng.")),
        GrammarPattern("Hỏi và nói uống gì", "N を のみます／なにを のみますか", "を đánh dấu đồ uống. Có thể thêm thời điểm ở đầu câu.", listOf(e("コーヒーをのみます。", "Koohii o nomimasu.", "Tôi uống cà phê."), e("あさ、ぎゅうにゅうをのみます。", "Asa, gyuunyuu o nomimasu.", "Buổi sáng tôi uống sữa.")), qa("なにをのみますか。", "Nani o nomimasu ka.", "Bạn uống gì?", "おちゃをのみます。", "Ocha o nomimasu.", "Tôi uống trà.")),
        GrammarPattern("Hỏi và phủ định động từ lịch sự", "N を Vますか／はい、Vます／いいえ、Vません", "Thêm か để hỏi có thực hiện hành động hay không. Đổi ます thành ません để phủ định. Khi đưa N thành chủ đề tương phản, は có thể thay を: パンはたべません.", listOf(e("やさいをたべます。", "Yasai o tabemasu.", "Tôi ăn rau."), e("パンはたべません。", "Pan wa tabemasen.", "Tôi không ăn bánh mì."), e("コーヒーはのみません。", "Koohii wa nomimasen.", "Tôi không uống cà phê.")), qa("さかなをたべますか。", "Sakana o tabemasu ka.", "Bạn có ăn cá không?", "いいえ、たべません。", "Iie, tabemasen.", "Không, tôi không ăn.")),
        GrammarPattern("Mời đồ uống", "N、のみますか", "Nêu đồ uống rồi hỏi のみますか để mời. Nhận lời bằng はい、おねがいします; từ chối lịch sự bằng いいえ、けっこうです.", listOf(e("コーヒー、のみますか。", "Koohii, nomimasu ka.", "Bạn dùng cà phê không?"), e("はい、おねがいします。", "Hai, onegai shimasu.", "Vâng, cho tôi xin."), e("いいえ、けっこうです。", "Iie, kekkou desu.", "Không, cảm ơn.")), qa("おちゃ、のみますか。", "Ocha, nomimasu ka.", "Bạn dùng trà không?", "はい、おねがいします。", "Hai, onegai shimasu.", "Vâng, cho tôi xin.")),
        GrammarPattern("Đề xuất món", "N は どうですか／N を おすすめします", "Dùng ～はどうですか để gợi ý nhẹ nhàng; ～をおすすめします để giới thiệu rõ một món.", listOf(e("てんぷらはどうですか。", "Tenpura wa dou desu ka.", "Tempura thì sao?"), e("おちゃをおすすめします。", "Ocha o osusume shimasu.", "Tôi đề xuất trà.")), qa("おすすめはなんですか。", "Osusume wa nan desu ka.", "Bạn đề xuất món gì?", "カレーをおすすめします。", "Karee o osusume shimasu.", "Tôi đề xuất món cà ri.")),
        GrammarPattern("Tần suất và thói quen ăn uống", "よく + Vます／あまり + Vません", "よく diễn tả hành động thường làm. あまり mang nghĩa không thường/không nhiều và phải đi với câu phủ định. いつも và ときどき cũng đặt trước hành động.", listOf(e("コーヒーをよくのみます。", "Koohii o yoku nomimasu.", "Tôi thường uống cà phê."), e("こうちゃはあまりのみません。", "Koucha wa amari nomimasen.", "Tôi không thường uống trà đen."), e("いつもあさごはんをたべます。", "Itsumo asagohan o tabemasu.", "Tôi luôn ăn sáng.")), qa("パンをよくたべますか。", "Pan o yoku tabemasu ka.", "Bạn có thường ăn bánh mì không?", "いいえ、あまりたべません。", "Iie, amari tabemasen.", "Không, tôi không thường ăn.")),
        GrammarPattern("Nói nhiều món", "N1 と N2 を たべます／のみます", "と nối các danh từ được liệt kê đầy đủ trước を. Đồ ăn và đồ uống nên dùng đúng động từ tương ứng.", listOf(e("パンとたまごをたべます。", "Pan to tamago o tabemasu.", "Tôi ăn bánh mì và trứng."), e("コーヒーとみずをのみます。", "Koohii to mizu o nomimasu.", "Tôi uống cà phê và nước.")), qa("あさごはんはなんですか。", "Asagohan wa nan desu ka.", "Bữa sáng của bạn là gì?", "パンとたまごとぎゅうにゅうです。", "Pan to tamago to gyuunyuu desu.", "Là bánh mì, trứng và sữa.")),
        GrammarPattern("好き bổ nghĩa cho danh từ", "すきな + N", "すき là tính từ-na. Khi đứng trước danh từ phải thêm な: すきなりょうり, すきなたべもの, すきなのみもの.", listOf(e("すきなりょうりはカレーです。", "Suki na ryouri wa karee desu.", "Món ăn yêu thích của tôi là cà ri."), e("すきなのみものはおちゃです。", "Suki na nomimono wa ocha desu.", "Đồ uống yêu thích của tôi là trà.")), qa("すきなりょうりはなんですか。", "Suki na ryouri wa nan desu ka.", "Món ăn yêu thích của bạn là gì?", "すきなりょうりはすしです。", "Suki na ryouri wa sushi desu.", "Món ăn yêu thích của tôi là sushi.")),
        GrammarPattern("Nói nơi thực hiện hành động", "Địa điểm で Vます", "Trợ từ で đánh dấu nơi hành động diễn ra. Phân biệt với に dùng cho nơi tồn tại hoặc đích đến.", listOf(e("あのみせでひるごはんをたべます。", "Ano mise de hirugohan o tabemasu.", "Tôi ăn trưa ở quán kia."), e("うちでコーヒーをのみます。", "Uchi de koohii o nomimasu.", "Tôi uống cà phê ở nhà.")), qa("ひるごはんはどこでたべますか。", "Hirugohan wa doko de tabemasu ka.", "Bạn ăn trưa ở đâu?", "あのみせでたべます。", "Ano mise de tabemasu.", "Tôi ăn ở quán kia.")),
        GrammarPattern("Nhận xét món ăn", "N は おいしいです", "おいしい là tính từ-i, đặt trực tiếp trước です để nhận xét món ăn ngon. Dạng hỏi là おいしいですか.", listOf(e("このりょうりはおいしいです。", "Kono ryouri wa oishii desu.", "Món này ngon."), e("あのみせはおいしいです。", "Ano mise wa oishii desu.", "Quán kia có đồ ăn ngon.")), qa("このカレーはおいしいですか。", "Kono karee wa oishii desu ka.", "Món cà ri này có ngon không?", "はい、おいしいです。", "Hai, oishii desu.", "Vâng, ngon.")),
        GrammarPattern("Rủ và đồng ý cùng làm", "Vます → Vましょう／じゃ、そうしましょう", "Đổi ます thành ましょう để rủ hoặc đề nghị cùng làm. じゃ、そうしましょう dùng để đồng ý và chốt phương án vừa nêu.", listOf(e("いっしょにたべましょう。", "Issho ni tabemashou.", "Chúng ta cùng ăn nhé."), e("じゃ、そうしましょう。", "Ja, sou shimashou.", "Vậy chốt như thế nhé.")), qa("あのみせでたべませんか。", "Ano mise de tabemasen ka.", "Chúng ta ăn ở quán kia nhé?", "はい、そうしましょう。", "Hai, sou shimashou.", "Vâng, làm vậy nhé.")),
    )

    private val foundationVocabulary = listOf(
        v("は", "wa", "trợ từ đánh dấu chủ đề", "Từ cơ bản", "わたしはティンです。", "Watashi wa Tinh desu.", "Tôi là Tính."),
        v("が", "ga", "trợ từ đánh dấu chủ thể hoặc đối tượng của khả năng", "Từ cơ bản", "にほんごができます。", "Nihon-go ga dekimasu.", "Tôi biết tiếng Nhật."),
        v("の", "no", "của; nối hai danh từ", "Từ cơ bản", "わたしのなまえはティンです。", "Watashi no namae wa Tinh desu.", "Tên tôi là Tính."),
        v("か", "ka", "trợ từ đặt cuối câu hỏi", "Từ cơ bản", "がくせいですか。", "Gakusei desu ka.", "Bạn là sinh viên phải không?"),
        v("も", "mo", "cũng", "Từ cơ bản", "わたしもエンジニアです。", "Watashi mo enjinia desu.", "Tôi cũng là kỹ sư."),
        v("を", "o", "trợ từ đánh dấu đối tượng của hành động", "Từ cơ bản", "にほんごをべんきょうします。", "Nihon-go o benkyou shimasu.", "Tôi học tiếng Nhật."),
        v("と", "to", "và; nối các danh từ", "Từ cơ bản", "ちちと ははがいます。", "Chichi to haha ga imasu.", "Tôi có bố và mẹ."),
        v("で", "de", "tại; trợ từ chỉ nơi diễn ra hành động", "Từ cơ bản", "あのみせでひるごはんをたべます。", "Ano mise de hirugohan o tabemasu.", "Tôi ăn trưa ở quán kia."),
        v("から", "kara", "từ; xuất phát từ", "Từ cơ bản", "ベトナムからです。", "Betonamu kara desu.", "Tôi đến từ Việt Nam."),
        v("に", "ni", "ở, đến; đánh dấu địa điểm hoặc đích", "Từ cơ bản", "とうきょうにいます。", "Toukyou ni imasu.", "Tôi ở Tokyo."),
        v("です", "desu", "là; đuôi câu lịch sự", "Từ cơ bản", "エンジニアです。", "Enjinia desu.", "Tôi là kỹ sư."),
        v("じゃないです", "ja nai desu", "không phải", "Từ cơ bản", "がくせいじゃないです。", "Gakusei ja nai desu.", "Tôi không phải là sinh viên."),
        v("お", "o", "tiền tố lịch sự", "Từ cơ bản", "おなまえはなんですか。", "Onamae wa nan desu ka.", "Bạn tên là gì?"),
        v("さん", "san", "anh/chị/bạn…; hậu tố lịch sự sau tên", "Từ cơ bản", "たなかさんはせんせいです。", "Tanaka-san wa sensei desu.", "Anh/chị Tanaka là giáo viên."),
        v("あなた", "anata", "bạn", "Từ cơ bản", "あなたのかぞくはなんにんですか。", "Anata no kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?"),
        v("わたしたち", "watashitachi", "chúng tôi, chúng ta", "Từ cơ bản", "わたしたちはおおさかにすんでいます。", "Watashitachi wa Oosaka ni sunde imasu.", "Chúng tôi sống ở Osaka."),
        v("なに／なん", "nani / nan", "gì", "Từ cơ bản", "おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
        v("どこ", "doko", "ở đâu", "Từ cơ bản", "どこにすんでいますか。", "Doko ni sunde imasu ka.", "Bạn sống ở đâu?"),
        v("どちら", "dochira", "đâu, phía nào; cách nói lịch sự", "Từ cơ bản", "どちらからですか。", "Dochira kara desu ka.", "Bạn đến từ đâu?"),
        v("だれ", "dare", "ai", "Từ cơ bản", "このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?"),
        v("これ", "kore", "cái này", "Từ cơ bản", "これはかぞくのしゃしんです。", "Kore wa kazoku no shashin desu.", "Đây là ảnh gia đình."),
        v("この", "kono", "… này; đứng trước danh từ", "Từ cơ bản", "このひとはわたしのあねです。", "Kono hito wa watashi no ane desu.", "Người này là chị tôi."),
        v("あの", "ano", "… kia; đứng trước danh từ", "Từ cơ bản", "あのひとはだれですか。", "Ano hito wa dare desu ka.", "Người kia là ai?"),
        v("どんな", "donna", "như thế nào, loại nào", "Từ cơ bản", "おとうとはどんなひとですか。", "Otouto wa donna hito desu ka.", "Em trai bạn là người thế nào?"),
        v("ですね", "desu ne", "… nhỉ; lời nhận xét tìm sự đồng tình", "Từ cơ bản", "かわいいですね。", "Kawaii desu ne.", "Đáng yêu nhỉ."),
        v("ひと", "hito", "người", "Từ cơ bản", "このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?"),
    )

    val vocabularyGroups = listOf(
        "Tất cả",
        "Chào hỏi & giới thiệu",
        "Nghề nghiệp",
        "Ngôn ngữ & khả năng",
        "Quốc gia & quốc tịch",
        "Gia đình",
        "Mô tả người",
        "Địa điểm & sinh sống",
        "Số đếm",
        "Đồ ăn & thức uống",
        "Sở thích & thói quen",
        "Từ cơ bản",
    )

    val vocabularyLibrary: List<VocabularyLibraryItem> by lazy {
        val basicJapanese = setOf("わたし", "なん", "どちら", "から", "と", "だれ", "います")
        val imported = lessons.flatMap { lesson ->
            lesson.vocabulary.map { entry ->
                val group = when {
                    entry.japanese in basicJapanese -> "Từ cơ bản"
                    lesson.id == 1 -> "Chào hỏi & giới thiệu"
                    lesson.id == 2 && entry.japanese == "おしごとはなんですか" -> "Nghề nghiệp"
                    lesson.id == 2 && entry.japanese in setOf("おくにはどちらですか", "どちらからですか") -> "Quốc gia & quốc tịch"
                    lesson.id == 2 && entry.japanese == "なんご" -> "Ngôn ngữ & khả năng"
                    lesson.id == 2 && entry.category == "Nghề nghiệp" -> "Nghề nghiệp"
                    lesson.id == 2 && entry.category in setOf("Ngôn ngữ", "Khả năng", "Mức độ") -> "Ngôn ngữ & khả năng"
                    lesson.id == 2 && entry.category in setOf("Quốc gia", "Quốc tịch", "Khái niệm") -> "Quốc gia & quốc tịch"
                    lesson.id == 2 && entry.category == "Quy tắc" && entry.japanese.contains("ご") -> "Ngôn ngữ & khả năng"
                    lesson.id == 2 && entry.category == "Quy tắc" -> "Quốc gia & quốc tịch"
                    lesson.id == 3 -> "Số đếm"
                    lesson.id == 4 && entry.category in setOf("Mô tả người", "Người") -> "Mô tả người"
                    lesson.id == 4 && entry.category in setOf("Địa điểm", "Sinh sống", "Cụm hỏi") -> "Địa điểm & sinh sống"
                    lesson.id == 4 && entry.category in setOf("Chung", "Gia đình mình", "Gia đình người khác", "Vợ chồng", "Con cái", "Họ hàng") -> "Gia đình"
                    lesson.id == 5 && entry.category in setOf("Đồ ăn", "Thức uống", "Địa điểm ăn uống", "Mô tả món") -> "Đồ ăn & thức uống"
                    lesson.id == 5 && entry.category in setOf("Sở thích", "Động từ", "Tần suất", "Thói quen", "Giao tiếp", "Khái niệm", "Cụm hỏi") -> "Sở thích & thói quen"
                    else -> "Từ cơ bản"
                }
                VocabularyLibraryItem(group, entry)
            }
        }
        (foundationVocabulary.map { VocabularyLibraryItem("Từ cơ bản", it) } + imported)
            .distinctBy { it.group to it.entry.japanese }
    }

    fun quickPractice(lesson: LanguageLesson): List<VocabularyEntry> =
        List(25) { lesson.vocabulary[(it * 7 + lesson.id) % lesson.vocabulary.size] }

    fun dialogues(lesson: LanguageLesson): List<DialogueScenario> {
        val names = listOf("マイ" to "Mai", "ティン" to "Tinh", "キム" to "Kimu", "カーラ" to "Kaara", "アン" to "An")
        val cities = listOf(
            Triple("ホーチミン", "Hoo Chi Min", "Hồ Chí Minh"), Triple("とうきょう", "Toukyou", "Tokyo"),
            Triple("ソウル", "Souru", "Seoul"), Triple("ペキン", "Pekin", "Bắc Kinh"),
            Triple("ニューヨーク", "Nyuu Yooku", "New York"), Triple("ロンドン", "Rondon", "London"),
            Triple("ローマ", "Rooma", "Rome"), Triple("パリ", "Pari", "Paris"),
            Triple("ベルリン", "Berurin", "Berlin"), Triple("バンコク", "Bankoku", "Bangkok"),
        )
        val jobs = listOf(
            Triple("ソフトウェアエンジニア", "sofutowea enjinia", "kỹ sư phần mềm"),
            Triple("かいはつしゃ", "kaihatsusha", "lập trình viên"), Triple("きょうし", "kyoushi", "giáo viên"),
            Triple("がくせい", "gakusei", "sinh viên"), Triple("かいしゃいん", "kaishain", "nhân viên công ty"),
            Triple("ぎんこういん", "ginkouin", "nhân viên ngân hàng"), Triple("いしゃ", "isha", "bác sĩ"),
            Triple("こうむいん", "koumuin", "công chức"), Triple("しゅふ", "shufu", "người nội trợ"),
        )
        return List(25) { index ->
            val scene = index % 5
            val variant = index / 5
            val (name, nameRomaji) = names[(scene + variant) % names.size]
            val (otherName, otherNameRomaji) = names[(scene + variant + 1) % names.size]
            when (lesson.id) {
                1 -> when (scene) {
                    0 -> DialogueScenario("Lần đầu gặp mặt ${index / 5 + 1}", listOf(
                        e("こんにちは。はじめまして。", "Konnichiwa. Hajimemashite.", "Xin chào. Rất vui được gặp bạn."),
                        e("こんにちは。はじめまして。", "Konnichiwa. Hajimemashite.", "Xin chào. Rất vui được gặp bạn."),
                        e("わたしは${name}です。おなまえはなんですか。", "Watashi wa $nameRomaji desu. Onamae wa nan desu ka.", "Tôi là $nameRomaji. Bạn tên là gì?"),
                        e("${otherName}です。", "$otherNameRomaji desu.", "Tôi là $otherNameRomaji."),
                        e("おげんきですか。", "Ogenki desu ka.", "Bạn có khỏe không?"),
                        e("はい、げんきです。${name}さんは。", "Hai, genki desu. $nameRomaji-san wa?", "Vâng, tôi khỏe. Còn bạn $nameRomaji?"),
                        e("わたしもげんきです。", "Watashi mo genki desu.", "Tôi cũng khỏe."),
                        e("どうぞよろしくおねがいします。", "Douzo yoroshiku onegai shimasu.", "Rất mong được bạn giúp đỡ."),
                        e("こちらこそ、よろしくおねがいします。", "Kochira koso, yoroshiku onegai shimasu.", "Chính tôi cũng rất mong được bạn giúp đỡ."),
                    ))
                    1 -> DialogueScenario("Chưa nghe rõ tên ${index / 5 + 1}", listOf(
                        e("すみません、おなまえはなんですか。", "Sumimasen, onamae wa nan desu ka.", "Xin lỗi, bạn tên là gì?"),
                        e("${otherName}です。", "$otherNameRomaji desu.", "Tôi là $otherNameRomaji."),
                        e("すみません、もういちどおねがいします。", "Sumimasen, mou ichido onegai shimasu.", "Xin lỗi, vui lòng nói lại một lần nữa."),
                        e("${otherName}です。", "$otherNameRomaji desu.", "Tôi là $otherNameRomaji."),
                        e("わかりました。ありがとうございます。", "Wakarimashita. Arigatou gozaimasu.", "Tôi hiểu rồi. Xin cảm ơn."),
                        e("どういたしまして。", "Dou itashimashite.", "Không có gì."),
                    ))
                    2 -> DialogueScenario("Chào buổi sáng ${index / 5 + 1}", listOf(
                        e("おはようございます。", "Ohayou gozaimasu.", "Chào buổi sáng."),
                        e("おはようございます。おげんきですか。", "Ohayou gozaimasu. Ogenki desu ka.", "Chào buổi sáng. Bạn có khỏe không?"),
                        e("はい、げんきです。${otherName}さんは。", "Hai, genki desu. $otherNameRomaji-san wa?", "Vâng, tôi khỏe. Còn bạn $otherNameRomaji?"),
                        e("わたしもげんきです。", "Watashi mo genki desu.", "Tôi cũng khỏe."),
                        e("きょうもよろしくおねがいします。", "Kyou mo yoroshiku onegai shimasu.", "Hôm nay cũng mong bạn giúp đỡ."),
                        e("こちらこそ、よろしくおねがいします。", "Kochira koso, yoroshiku onegai shimasu.", "Chính tôi cũng mong được bạn giúp đỡ."),
                    ))
                    3 -> DialogueScenario("Nhầm tên ${index / 5 + 1}", listOf(
                        e("すみません、${otherName}さんですか。", "Sumimasen, $otherNameRomaji-san desu ka.", "Xin lỗi, bạn là $otherNameRomaji phải không?"),
                        e("いいえ、${name}です。", "Iie, $nameRomaji desu.", "Không, tôi là $nameRomaji."),
                        e("すみません。わたしは${otherName}です。", "Sumimasen. Watashi wa $otherNameRomaji desu.", "Xin lỗi. Tôi là $otherNameRomaji."),
                        e("はじめまして。", "Hajimemashite.", "Rất vui được gặp bạn."),
                        e("はじめまして。どうぞよろしくおねがいします。", "Hajimemashite. Douzo yoroshiku onegai shimasu.", "Rất vui được gặp bạn. Mong được bạn giúp đỡ."),
                        e("こちらこそ、よろしくおねがいします。", "Kochira koso, yoroshiku onegai shimasu.", "Chính tôi cũng mong được bạn giúp đỡ."),
                    ))
                    else -> DialogueScenario("Kết thúc buổi học ${index / 5 + 1}", listOf(
                        e("きょうはありがとうございました。", "Kyou wa arigatou gozaimashita.", "Hôm nay xin cảm ơn bạn."),
                        e("こちらこそ、ありがとうございました。", "Kochira koso, arigatou gozaimashita.", "Chính tôi cũng xin cảm ơn."),
                        e("またあした。", "Mata ashita.", "Hẹn gặp lại ngày mai."),
                        e("はい、またあした。", "Hai, mata ashita.", "Vâng, hẹn gặp lại ngày mai."),
                        e("さようなら。", "Sayounara.", "Tạm biệt."),
                        e("さようなら。", "Sayounara.", "Tạm biệt."),
                    ))
                }
                2 -> {
                    val row = countryRows[(scene * 2 + variant) % countryRows.size]
                    val job = jobs[(scene + variant) % jobs.size]
                    val otherRow = countryRows[(scene * 2 + variant + 3) % countryRows.size]
                    val otherJob = jobs[(scene + variant + 3) % jobs.size]
                    val city = cities[(scene * 2 + variant) % cities.size]
                    val unknownLanguage = countryRows[(scene + variant + 1) % countryRows.size]
                    val knownLanguage = countryRows[(scene + variant + 2) % countryRows.size]
                    when (scene) {
                        0 -> DialogueScenario("Làm quen toàn diện ${index / 5 + 1}", listOf(
                            e("はじめまして。${name}です。", "Hajimemashite. $nameRomaji desu.", "Rất vui được gặp bạn. Tôi là $nameRomaji."),
                            e("はじめまして。${otherName}です。どちらからですか。", "Hajimemashite. $otherNameRomaji desu. Dochira kara desu ka.", "Rất vui được gặp bạn. Tôi là $otherNameRomaji. Bạn đến từ đâu?"),
                            e("${row[0]}からです。", "${row[1]} kara desu.", "Tôi đến từ ${row[2]}."),
                            e("しゅっしんはどちらですか。", "Shusshin wa dochira desu ka.", "Quê bạn ở đâu?"),
                            e("${city.first}です。", "${city.second} desu.", "Quê tôi ở ${city.third}."),
                            e("${unknownLanguage[6]}ができますか。", "${unknownLanguage[7]} ga dekimasu ka.", "Bạn biết ${unknownLanguage[8]} không?"),
                            e("いいえ、${unknownLanguage[6]}はできません。", "Iie, ${unknownLanguage[7]} wa dekimasen.", "Không, tôi không biết ${unknownLanguage[8]}."),
                            e("${knownLanguage[6]}ができますか。", "${knownLanguage[7]} ga dekimasu ka.", "Còn bạn biết ${knownLanguage[8]} không?"),
                            e("はい、${knownLanguage[6]}ができます。", "Hai, ${knownLanguage[7]} ga dekimasu.", "Vâng, tôi biết ${knownLanguage[8]}."),
                            e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
                            e("${job.first}です。${otherName}さんは。", "${job.second} desu. $otherNameRomaji-san wa?", "Tôi là ${job.third}. Còn bạn $otherNameRomaji?"),
                            e("${otherJob.first}です。", "${otherJob.second} desu.", "Tôi là ${otherJob.third}."),
                        ))
                        1 -> DialogueScenario("Hai đồng nghiệp mới ${index / 5 + 1}", listOf(
                            e("おはようございます。あたらしいかたですか。", "Ohayou gozaimasu. Atarashii kata desu ka.", "Chào buổi sáng. Bạn là người mới phải không?"),
                            e("はい、${name}です。よろしくおねがいします。", "Hai, $nameRomaji desu. Yoroshiku onegai shimasu.", "Vâng, tôi là $nameRomaji. Mong được giúp đỡ."),
                            e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
                            e("${job.first}です。あなたは。", "${job.second} desu. Anata wa?", "Tôi là ${job.third}. Còn bạn?"),
                            e("${otherJob.first}です。どちらからですか。", "${otherJob.second} desu. Dochira kara desu ka.", "Tôi là ${otherJob.third}. Bạn đến từ đâu?"),
                            e("${row[0]}からです。${row[6]}とえいごができます。", "${row[1]} kara desu. ${row[7]} to Eigo ga dekimasu.", "Tôi đến từ ${row[2]}. Tôi biết ${row[8]} và tiếng Anh."),
                            e("にほんごはどうですか。", "Nihon-go wa dou desu ka.", "Tiếng Nhật của bạn thế nào?"),
                            e("にほんごはすこしできます。", "Nihon-go wa sukoshi dekimasu.", "Tôi biết một chút tiếng Nhật."),
                        ))
                        2 -> DialogueScenario("Giao lưu quốc tế ${index / 5 + 1}", listOf(
                            e("${name}さんは${row[3]}ですか。", "$nameRomaji-san wa ${row[4]} desu ka.", "Bạn $nameRomaji là ${row[5]} phải không?"),
                            e("はい、そうです。${otherName}さんは。", "Hai, sou desu. $otherNameRomaji-san wa?", "Vâng, đúng vậy. Còn bạn $otherNameRomaji?"),
                            e("わたしは${otherRow[3]}です。", "Watashi wa ${otherRow[4]} desu.", "Tôi là ${otherRow[5]}."),
                            e("${otherRow[6]}がわかりますか。", "${otherRow[7]} ga wakarimasu ka.", "Bạn có hiểu ${otherRow[8]} không?"),
                            e("いいえ、ぜんぜんわかりません。", "Iie, zenzen wakarimasen.", "Không, tôi hoàn toàn không hiểu."),
                            e("えいごはわかりますか。", "Eigo wa wakarimasu ka.", "Bạn có hiểu tiếng Anh không?"),
                            e("はい、よくわかります。", "Hai, yoku wakarimasu.", "Vâng, tôi hiểu khá rõ."),
                            e("そうですか。わたしはあまりわかりません。", "Sou desu ka. Watashi wa amari wakarimasen.", "Vậy à. Tôi không hiểu lắm."),
                        ))
                        3 -> DialogueScenario("Tìm bạn luyện ngôn ngữ ${index / 5 + 1}", listOf(
                            e("なんごができますか。", "Nan-go ga dekimasu ka.", "Bạn biết ngôn ngữ nào?"),
                            e("${row[6]}ができます。${name}さんは。", "${row[7]} ga dekimasu. $nameRomaji-san wa?", "Tôi biết ${row[8]}. Còn bạn $nameRomaji?"),
                            e("${knownLanguage[6]}ができます。", "${knownLanguage[7]} ga dekimasu.", "Tôi biết ${knownLanguage[8]}."),
                            e("にほんごもできますか。", "Nihon-go mo dekimasu ka.", "Bạn cũng biết tiếng Nhật không?"),
                            e("いいえ、にほんごはあまりできません。", "Iie, Nihon-go wa amari dekimasen.", "Không, tôi không giỏi tiếng Nhật lắm."),
                            e("わたしもです。いっしょにべんきょうしますか。", "Watashi mo desu. Issho ni benkyou shimasu ka.", "Tôi cũng vậy. Chúng ta học cùng nhau nhé?"),
                            e("はい、おねがいします。", "Hai, onegai shimasu.", "Vâng, nhờ bạn nhé."),
                            e("よろしくおねがいします。", "Yoroshiku onegai shimasu.", "Rất mong được bạn giúp đỡ."),
                        ))
                        else -> DialogueScenario("Đoán nhầm quốc tịch ${index / 5 + 1}", listOf(
                            e("すみません、にほんじんですか。", "Sumimasen, Nihon-jin desu ka.", "Xin lỗi, bạn là người Nhật phải không?"),
                            e("いいえ、にほんじんじゃないです。${row[3]}です。", "Iie, Nihon-jin ja nai desu. ${row[4]} desu.", "Không, tôi không phải người Nhật. Tôi là ${row[5]}."),
                            e("そうですか。どこにすんでいますか。", "Sou desu ka. Doko ni sunde imasu ka.", "Vậy à. Bạn đang sống ở đâu?"),
                            e("${city.first}にすんでいます。", "${city.second} ni sunde imasu.", "Tôi sống ở ${city.third}."),
                            e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Bạn làm nghề gì?"),
                            e("${job.first}です。あなたは。", "${job.second} desu. Anata wa?", "Tôi là ${job.third}. Còn bạn?"),
                            e("${otherJob.first}です。", "${otherJob.second} desu.", "Tôi là ${otherJob.third}."),
                            e("どうぞよろしくおねがいします。", "Douzo yoroshiku onegai shimasu.", "Rất mong được bạn giúp đỡ."),
                        ))
                    }
                }
                3 -> {
                    val age = 20 + index
                    val people = 3 + index % 6
                    val (ageText, ageRead) = ageExpression(age)
                    val otherAge = age + 2
                    val (otherAgeText, otherAgeRead) = ageExpression(otherAge)
                    val (peopleText, peopleRead) = peopleExpression(people)
                    val number = 11 + (index * 7) % 89
                    val numberKana = hiraganaNumber(number)
                    val (_, numberRomaji) = japaneseNumber(number)
                    when (scene) {
                        0 -> DialogueScenario("Tuổi và gia đình ${index / 5 + 1}", listOf(
                            e("なんさいですか。", "Nan-sai desu ka.", "Bạn bao nhiêu tuổi?"),
                            e("${ageText}です。あなたは。", "$ageRead desu. Anata wa?", "Tôi $age tuổi. Còn bạn?"),
                            e("${otherAgeText}です。", "$otherAgeRead desu.", "Tôi $otherAge tuổi."),
                            e("かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?"),
                            e("${peopleText}です。あなたのかぞくは。", "$peopleRead desu. Anata no kazoku wa?", "Gia đình tôi có $people người. Còn gia đình bạn?"),
                            e("ごにんです。", "Go-nin desu.", "Gia đình tôi có năm người."),
                        ))
                        1 -> DialogueScenario("Tìm phòng học ${index / 5 + 1}", listOf(
                            e("すみません、きょうしつはなんばんですか。", "Sumimasen, kyoushitsu wa nan-ban desu ka.", "Xin lỗi, phòng học số mấy?"),
                            e("${numberKana}ばんです。", "$numberRomaji-ban desu.", "Phòng số $number."),
                            e("${numberKana}ばんですね。", "$numberRomaji-ban desu ne.", "Là phòng số $number nhỉ."),
                            e("はい、そうです。", "Hai, sou desu.", "Vâng, đúng vậy."),
                            e("ありがとうございます。", "Arigatou gozaimasu.", "Xin cảm ơn."),
                            e("どういたしまして。", "Dou itashimashite.", "Không có gì."),
                        ))
                        2 -> DialogueScenario("Mở đúng trang sách ${index / 5 + 1}", listOf(
                            e("なんページですか。", "Nan peeji desu ka.", "Trang bao nhiêu vậy?"),
                            e("${numberKana}ページです。", "$numberRomaji peeji desu.", "Trang $number."),
                            e("すみません、もういちどおねがいします。", "Sumimasen, mou ichido onegai shimasu.", "Xin lỗi, vui lòng nói lại."),
                            e("${numberKana}ページです。", "$numberRomaji peeji desu.", "Trang $number."),
                            e("わかりました。", "Wakarimashita.", "Tôi hiểu rồi."),
                            e("では、はじめましょう。", "Dewa, hajimemashou.", "Vậy chúng ta bắt đầu nhé."),
                        ))
                        3 -> DialogueScenario("Chia nhóm ${index / 5 + 1}", listOf(
                            e("このグループはなんにんですか。", "Kono guruupu wa nan-nin desu ka.", "Nhóm này có bao nhiêu người?"),
                            e("${peopleText}です。", "$peopleRead desu.", "Có $people người."),
                            e("おとこのひとはなんにんですか。", "Otoko no hito wa nan-nin desu ka.", "Có bao nhiêu nam?"),
                            e("ふたりです。", "Futari desu.", "Có hai nam."),
                            e("おんなのひとは。", "Onna no hito wa?", "Còn nữ thì sao?"),
                            e("${peopleExpression(people - 2).first}です。", "${peopleExpression(people - 2).second} desu.", "Có ${people - 2} nữ."),
                        ))
                        else -> DialogueScenario("Kết quả bài kiểm tra ${index / 5 + 1}", listOf(
                            e("テストはなんてんでしたか。", "Tesuto wa nan-ten deshita ka.", "Bài kiểm tra được bao nhiêu điểm?"),
                            e("${numberKana}てんでした。", "$numberRomaji-ten deshita.", "Tôi được $number điểm."),
                            e("すごいですね。", "Sugoi desu ne.", "Giỏi quá nhỉ."),
                            e("ありがとうございます。あなたは。", "Arigatou gozaimasu. Anata wa?", "Cảm ơn. Còn bạn?"),
                            e("${hiraganaNumber((number - 7).coerceAtLeast(1))}てんでした。", "${japaneseNumber((number - 7).coerceAtLeast(1)).second}-ten deshita.", "Tôi được ${(number - 7).coerceAtLeast(1)} điểm."),
                            e("つぎもがんばりましょう。", "Tsugi mo ganbarimashou.", "Lần sau chúng ta cũng cùng cố gắng nhé."),
                        ))
                    }
                }
                5 -> {
                    val foods = listOf(
                        Triple("すし", "sushi", "sushi"), Triple("やきにく", "yakiniku", "thịt nướng"),
                        Triple("カレー", "karee", "cà ri"), Triple("ラーメン", "raamen", "ramen"),
                        Triple("ぎょうざ", "gyouza", "há cảo"), Triple("てんぷら", "tenpura", "tempura"),
                        Triple("うどん", "udon", "udon"), Triple("ピザ", "piza", "pizza"),
                        Triple("さかな", "sakana", "cá"), Triple("やさい", "yasai", "rau củ"),
                    )
                    val drinks = listOf(
                        Triple("コーヒー", "koohii", "cà phê"), Triple("おちゃ", "ocha", "trà xanh"),
                        Triple("こうちゃ", "koucha", "trà đen"), Triple("ぎゅうにゅう", "gyuunyuu", "sữa"),
                        Triple("ジュース", "juusu", "nước ép"), Triple("みず", "mizu", "nước"),
                    )
                    val breakfasts = listOf(
                        Triple("パンとたまご", "pan to tamago", "bánh mì và trứng"),
                        Triple("ごはんとみそしる", "gohan to misoshiru", "cơm và canh miso"),
                        Triple("くだものとぎゅうにゅう", "kudamono to gyuunyuu", "trái cây và sữa"),
                        Triple("おにぎりとおちゃ", "onigiri to ocha", "cơm nắm và trà"),
                        Triple("サンドイッチとコーヒー", "sandoicchi to koohii", "sandwich và cà phê"),
                    )
                    val food = foods[(scene + variant) % foods.size]
                    val otherFood = foods[(scene + variant + 3) % foods.size]
                    val drink = drinks[(scene + variant) % drinks.size]
                    val otherDrink = drinks[(scene + variant + 2) % drinks.size]
                    val breakfast = breakfasts[variant % breakfasts.size]
                    when (scene) {
                        0 -> DialogueScenario("Món ăn yêu thích ${variant + 1}", listOf(
                            e("すきなたべものはなんですか。", "Suki na tabemono wa nan desu ka.", "Món ăn bạn thích là gì?"),
                            e("${food.first}がすきです。${name}さんは。", "${food.second} ga suki desu. $nameRomaji-san wa?", "Tôi thích ${food.third}. Còn bạn $nameRomaji?"),
                            e("${otherFood.first}がだいすきです。", "${otherFood.second} ga daisuki desu.", "Tôi rất thích ${otherFood.third}."),
                            e("${food.first}もすきですか。", "${food.second} mo suki desu ka.", "Bạn cũng thích ${food.third} không?"),
                            e("いいえ、${food.first}はあまりすきじゃないです。", "Iie, ${food.second} wa amari suki ja nai desu.", "Không, tôi không thích ${food.third} lắm."),
                            e("そうですか。", "Sou desu ka.", "Vậy à."),
                        ))
                        1 -> DialogueScenario("Mời đồ uống ${variant + 1}", listOf(
                            e("${drink.first}、のみますか。", "${drink.second}, nomimasu ka.", "Bạn dùng ${drink.third} không?"),
                            if (variant % 2 == 0) e("はい、おねがいします。", "Hai, onegai shimasu.", "Vâng, cho tôi xin.")
                            else e("いいえ、けっこうです。", "Iie, kekkou desu.", "Không, cảm ơn."),
                            if (variant % 2 == 0) e("はい、どうぞ。", "Hai, douzo.", "Vâng, mời bạn.")
                            else e("では、${otherDrink.first}はどうですか。", "Dewa, ${otherDrink.second} wa dou desu ka.", "Vậy ${otherDrink.third} thì sao?"),
                            if (variant % 2 == 0) e("ありがとうございます。", "Arigatou gozaimasu.", "Xin cảm ơn.")
                            else e("はい、おねがいします。", "Hai, onegai shimasu.", "Vâng, cho tôi xin."),
                        ))
                        2 -> DialogueScenario("Thói quen ăn sáng ${variant + 1}", listOf(
                            e("いつもあさごはんをたべますか。", "Itsumo asagohan o tabemasu ka.", "Bạn luôn ăn sáng chứ?"),
                            if (variant == 3) e("いいえ、あさごはんはあまりたべません。", "Iie, asagohan wa amari tabemasen.", "Không, tôi không thường ăn sáng.")
                            else e("はい、いつもたべます。", "Hai, itsumo tabemasu.", "Vâng, tôi luôn ăn."),
                            e("あさごはんはなんですか。", "Asagohan wa nan desu ka.", "Bữa sáng của bạn là gì?"),
                            e("${breakfast.first}です。", "${breakfast.second} desu.", "Bữa sáng của tôi là ${breakfast.third}."),
                            e("なにをのみますか。", "Nani o nomimasu ka.", "Bạn uống gì?"),
                            e("${drink.first}をのみます。", "${drink.second} o nomimasu.", "Tôi uống ${drink.third}."),
                        ))
                        3 -> DialogueScenario("Chọn món được đề xuất ${variant + 1}", listOf(
                            e("おすすめはなんですか。", "Osusume wa nan desu ka.", "Bạn đề xuất món gì?"),
                            e("${food.first}をおすすめします。", "${food.second} o osusume shimasu.", "Tôi đề xuất ${food.third}."),
                            e("${food.first}はどんなたべものですか。", "${food.second} wa donna tabemono desu ka.", "${food.third} là món như thế nào?"),
                            e("おいしいですよ。", "Oishii desu yo.", "Món đó ngon đấy."),
                            e("では、${food.first}をおねがいします。", "Dewa, ${food.second} o onegai shimasu.", "Vậy cho tôi ${food.third}."),
                            e("はい、わかりました。", "Hai, wakarimashita.", "Vâng, tôi hiểu rồi."),
                        ))
                        else -> DialogueScenario("Ăn uống cùng bạn ${variant + 1}", listOf(
                            e("ひるごはんはなにをたべますか。", "Hirugohan wa nani o tabemasu ka.", "Bữa trưa bạn ăn gì?"),
                            e("${food.first}をたべます。", "${food.second} o tabemasu.", "Tôi ăn ${food.third}."),
                            e("どこでたべますか。", "Doko de tabemasu ka.", "Bạn ăn ở đâu?"),
                            e("あのみせで${food.first}をたべます。", "Ano mise de ${food.second} o tabemasu.", "Tôi ăn ${food.third} ở quán kia."),
                            e("あのみせはおいしいですか。", "Ano mise wa oishii desu ka.", "Quán kia có ngon không?"),
                            e("はい、おいしいです。", "Hai, oishii desu.", "Vâng, ngon."),
                            e("じゃ、そうしましょう。", "Ja, sou shimashou.", "Vậy chốt như thế nhé."),
                        ))
                    }
                }
                else -> {
                    val members = listOf(
                        Triple("ちち", "chichi", "bố tôi"), Triple("はは", "haha", "mẹ tôi"),
                        Triple("あに", "ani", "anh tôi"), Triple("あね", "ane", "chị tôi"),
                        Triple("おとうと", "otouto", "em trai tôi"),
                    )
                    val member = members[(scene + variant) % members.size]
                    val job = jobs[(scene + variant) % jobs.size]
                    val otherJob = jobs[(scene + variant + 4) % jobs.size]
                    val age = 25 + index
                    val (ageText, ageRead) = ageExpression(age)
                    when (scene) {
                        0 -> DialogueScenario("Xem ảnh gia đình ${index / 5 + 1}", listOf(
                            e("これはかぞくのしゃしんですか。", "Kore wa kazoku no shashin desu ka.", "Đây là ảnh gia đình bạn phải không?"),
                            e("はい、そうです。", "Hai, sou desu.", "Vâng, đúng vậy."),
                            e("このひとはだれですか。", "Kono hito wa dare desu ka.", "Người này là ai?"),
                            e("わたしの${member.first}です。", "Watashi no ${member.second} desu.", "Đây là ${member.third}."),
                            e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Người đó làm nghề gì?"),
                            e("${job.first}です。", "${job.second} desu.", "Là ${job.third}."),
                            e("なんさいですか。", "Nan-sai desu ka.", "Người đó bao nhiêu tuổi?"),
                            e("${ageText}です。", "$ageRead desu.", "Người đó $age tuổi."),
                        ))
                        1 -> DialogueScenario("Gia đình có những ai ${index / 5 + 1}", listOf(
                            e("かぞくはなんにんですか。", "Kazoku wa nan-nin desu ka.", "Gia đình bạn có bao nhiêu người?"),
                            e("ごにんです。", "Go-nin desu.", "Gia đình tôi có năm người."),
                            e("だれがいますか。", "Dare ga imasu ka.", "Gia đình có những ai?"),
                            e("ちちと ははと あねと いもうとと わたしです。", "Chichi to haha to ane to imouto to watashi desu.", "Gồm bố, mẹ, chị gái, em gái và tôi."),
                            e("きょうだいがふたりいますね。", "Kyoudai ga futari imasu ne.", "Bạn có hai chị em nhỉ."),
                            e("はい、あねと いもうとがいます。あなたのかぞくは。", "Hai, ane to imouto ga imasu. Anata no kazoku wa?", "Vâng, tôi có chị và em gái. Còn gia đình bạn?"),
                            e("りょうしんと あにと わたしです。", "Ryoushin to ani to watashi desu.", "Gia đình tôi gồm bố mẹ, anh trai và tôi."),
                        ))
                        2 -> DialogueScenario("Nghề nghiệp của bố mẹ ${index / 5 + 1}", listOf(
                            e("おとうさんのおしごとはなんですか。", "Otousan no oshigoto wa nan desu ka.", "Bố bạn làm nghề gì?"),
                            e("${job.first}です。おかあさんは${otherJob.first}です。", "${job.second} desu. Okaasan wa ${otherJob.second} desu.", "Bố tôi là ${job.third}. Mẹ tôi là ${otherJob.third}."),
                            e("そうですか。りょうしんはどこにいますか。", "Sou desu ka. Ryoushin wa doko ni imasu ka.", "Vậy à. Bố mẹ bạn đang ở đâu?"),
                            e("${cities[(scene * 2 + variant) % cities.size].first}にいます。", "${cities[(scene * 2 + variant) % cities.size].second} ni imasu.", "Bố mẹ tôi ở ${cities[(scene * 2 + variant) % cities.size].third}."),
                            e("あなたのりょうしんは。", "Anata no ryoushin wa?", "Còn bố mẹ bạn?"),
                            e("ベトナムにいます。ちちはかいしゃいんで、はははきょうしです。", "Betonamu ni imasu. Chichi wa kaishain de, haha wa kyoushi desu.", "Bố mẹ tôi ở Việt Nam. Bố là nhân viên công ty và mẹ là giáo viên."),
                        ))
                        3 -> DialogueScenario("Anh chị em sống ở đâu ${index / 5 + 1}", listOf(
                            e("きょうだいがいますか。", "Kyoudai ga imasu ka.", "Bạn có anh chị em không?"),
                            e("はい、${member.first}がいます。", "Hai, ${member.second} ga imasu.", "Vâng, tôi có ${member.third}."),
                            e("どこにいますか。", "Doko ni imasu ka.", "Người đó đang ở đâu?"),
                            e("${cities[(scene * 2 + variant) % cities.size].first}にいます。", "${cities[(scene * 2 + variant) % cities.size].second} ni imasu.", "Người đó ở ${cities[(scene * 2 + variant) % cities.size].third}."),
                            e("おしごとはなんですか。", "Oshigoto wa nan desu ka.", "Người đó làm nghề gì?"),
                            e("${job.first}です。", "${job.second} desu.", "Là ${job.third}."),
                            e("なんさいですか。", "Nan-sai desu ka.", "Người đó bao nhiêu tuổi?"),
                            e("${ageText}です。", "$ageRead desu.", "Người đó $age tuổi."),
                        ))
                        else -> DialogueScenario("Con cái và gia đình ${index / 5 + 1}", listOf(
                            e("こどもがいますか。", "Kodomo ga imasu ka.", "Bạn có con không?"),
                            e("はい、こどもがふたりいます。", "Hai, kodomo ga futari imasu.", "Vâng, tôi có hai người con."),
                            e("むすこさんと むすめさんですか。", "Musuko-san to musume-san desu ka.", "Là một con trai và một con gái phải không?"),
                            e("はい、そうです。むすこははっさいで、むすめはろくさいです。", "Hai, sou desu. Musuko wa hassai de, musume wa roku-sai desu.", "Vâng. Con trai tám tuổi và con gái sáu tuổi."),
                            e("あなたはこどもがいますか。", "Anata wa kodomo ga imasu ka.", "Còn bạn có con không?"),
                            e("いいえ、こどもはいません。", "Iie, kodomo wa imasen.", "Không, tôi chưa có con."),
                            e("そうですか。", "Sou desu ka.", "Vậy à."),
                        ))
                    }
                }
            }
        }
    }
}
