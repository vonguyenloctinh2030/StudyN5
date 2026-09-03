package com.studyn5.kana.data

data class KanjiExample(
    val japanese: String,
    val reading: String,
    val meaning: String,
)

data class KanjiEntry(
    val character: String,
    val meaning: String,
    val onyomi: String,
    val kunyomi: String,
    val onAudio: String?,
    val kunAudio: String?,
    val strokeCount: Int,
    val category: String,
    val learned: Boolean,
    val memoryHint: String,
    val examples: List<KanjiExample>,
)

object KanjiData {
    private fun ex(japanese: String, reading: String, meaning: String) = KanjiExample(japanese, reading, meaning)

    private fun k(
        character: String,
        meaning: String,
        onyomi: String,
        kunyomi: String,
        onAudio: String?,
        kunAudio: String?,
        strokes: Int,
        category: String,
        learned: Boolean = false,
        hint: String,
        vararg examples: KanjiExample,
    ) = KanjiEntry(character, meaning, onyomi, kunyomi, onAudio, kunAudio, strokes, category, learned, hint, examples.toList())

    val categories = listOf("Tất cả", "Đã học", "Số đếm", "Thời gian", "Con người", "Tự nhiên", "Trường học")

    val entries = listOf(
        k("魚", "cá", "ギョ", "さかな", "ぎょ", "さかな", 11, "Đã học", true, "Phần đầu như con cá, bốn chấm dưới như đuôi và nước.", ex("魚がすきです。", "さかな が すき です。", "Tôi thích cá."), ex("魚を食べます。", "さかな を たべます。", "Tôi ăn cá.")),
        k("肉", "thịt", "ニク", "—", "にく", null, 6, "Đã học", true, "Hai người 人 ở trong khung miếng thịt.", ex("肉はすきじゃないです。", "にく は すきじゃない です。", "Tôi không thích thịt."), ex("牛肉はおいしいです。", "ぎゅうにく は おいしい です。", "Thịt bò ngon.")),
        k("卵", "trứng", "ラン", "たまご", "らん", "たまご", 7, "Đã học", true, "Hai phần khép lại như quả trứng có lòng đỏ.", ex("卵がすきですか。", "たまご が すき ですか。", "Bạn thích trứng không?"), ex("卵を三つください。", "たまご を みっつ ください。", "Cho tôi ba quả trứng.")),
        k("水", "nước", "スイ", "みず", "すい", "みず", 4, "Đã học", true, "Nét giữa là dòng nước, hai bên là những giọt bắn ra.", ex("水を飲みます。", "みず を のみます。", "Tôi uống nước."), ex("水曜日です。", "すいようび です。", "Là thứ Tư.")),
        k("食", "ăn; thức ăn", "ショク", "たべる", "しょく", "たべる", 9, "Đã học", true, "Mái che một bữa ăn bên dưới.", ex("朝ご飯を食べます。", "あさごはん を たべます。", "Tôi ăn sáng."), ex("食べ物は何がすきですか。", "たべもの は なに が すき ですか。", "Bạn thích đồ ăn gì?")),
        k("飲", "uống", "イン", "のむ", "いん", "のむ", 12, "Đã học", true, "Bên trái là ăn 食, bên phải gợi hành động há miệng uống.", ex("何を飲みますか。", "なに を のみますか。", "Bạn uống gì?"), ex("水を飲みます。", "みず を のみます。", "Tôi uống nước.")),

        k("一", "một", "イチ・イツ", "ひとつ", "いち", "ひとつ", 1, "Số đếm", hint = "Một nét ngang duy nhất: số một.", ex("一つください。", "ひとつ ください。", "Cho tôi một cái."), ex("一人です。", "ひとり です。", "Có một người.")),
        k("二", "hai", "ニ", "ふたつ", "に", "ふたつ", 2, "Số đếm", hint = "Hai nét ngang: số hai.", ex("二つください。", "ふたつ ください。", "Cho tôi hai cái."), ex("二人です。", "ふたり です。", "Có hai người.")),
        k("三", "ba", "サン", "みっつ", "さん", "みっつ", 3, "Số đếm", hint = "Ba nét ngang: số ba.", ex("三つください。", "みっつ ください。", "Cho tôi ba cái."), ex("三人です。", "さんにん です。", "Có ba người.")),
        k("四", "bốn", "シ", "よん・よつ", "し", "よん", 5, "Số đếm", hint = "Bốn góc nằm trong một khung.", ex("四つください。", "よっつ ください。", "Cho tôi bốn cái."), ex("四人です。", "よにん です。", "Có bốn người.")),
        k("五", "năm", "ゴ", "いつつ", "ご", "いつつ", 4, "Số đếm", hint = "Các nét giao nhau tạo điểm giữa của dãy một đến chín.", ex("五つください。", "いつつ ください。", "Cho tôi năm cái."), ex("家族は五人です。", "かぞく は ごにん です。", "Gia đình có năm người.")),
        k("六", "sáu", "ロク", "むっつ", "ろく", "むっつ", 4, "Số đếm", hint = "Nắp trên và hai chân xòe dưới.", ex("六つください。", "むっつ ください。", "Cho tôi sáu cái."), ex("家族は六人です。", "かぞく は ろくにん です。", "Gia đình có sáu người.")),
        k("七", "bảy", "シチ", "なな・ななつ", "しち", "なな", 2, "Số đếm", hint = "Nét ngang bị cắt và uốn móc: số bảy.", ex("七つください。", "ななつ ください。", "Cho tôi bảy cái."), ex("七人です。", "ななにん です。", "Có bảy người.")),
        k("八", "tám", "ハチ", "やっつ", "はち", "やっつ", 2, "Số đếm", hint = "Hai nét mở rộng sang hai phía.", ex("八つください。", "やっつ ください。", "Cho tôi tám cái."), ex("八人です。", "はちにん です。", "Có tám người.")),
        k("九", "chín", "キュウ・ク", "ここのつ", "きゅう", "ここのつ", 2, "Số đếm", hint = "Một nét cong ôm lấy nét móc: số chín.", ex("九つください。", "ここのつ ください。", "Cho tôi chín cái."), ex("九人です。", "きゅうにん です。", "Có chín người.")),
        k("十", "mười", "ジュウ", "とお", "じゅう", "とお", 2, "Số đếm", hint = "Dấu cộng đơn giản tượng trưng cho mười.", ex("ケーキが十あります。", "ケーキ が とお あります。", "Có mười chiếc bánh."), ex("十人です。", "じゅうにん です。", "Có mười người.")),
        k("百", "một trăm", "ヒャク", "—", "ひゃく", null, 6, "Số đếm", hint = "Một 一 đặt trên hình mặt trời 日: một trăm.", ex("百円です。", "ひゃくえん です。", "Giá 100 yên."), ex("百人います。", "ひゃくにん います。", "Có 100 người.")),
        k("千", "một nghìn", "セン", "ち", "せん", "ち", 3, "Số đếm", hint = "Nét phẩy trên chữ mười 十 làm thành một nghìn.", ex("千円です。", "せんえん です。", "Giá 1.000 yên."), ex("三千円です。", "さんぜんえん です。", "Giá 3.000 yên.")),
        k("円", "yên; hình tròn", "エン", "まるい", "えん", "まるい", 4, "Số đếm", hint = "Một vật được bao trong đường cong tròn.", ex("三百円です。", "さんびゃくえん です。", "Giá 300 yên."), ex("これは百円です。", "これ は ひゃくえん です。", "Cái này giá 100 yên.")),

        k("日", "ngày; mặt trời", "ニチ・ジツ", "ひ・か", "にち", "ひ", 4, "Thời gian", hint = "Hình mặt trời được giản hóa thành khung chữ nhật.", ex("今日はいい日です。", "きょう は いい ひ です。", "Hôm nay là một ngày đẹp."), ex("日曜日です。", "にちようび です。", "Là Chủ nhật.")),
        k("月", "tháng; mặt trăng", "ゲツ・ガツ", "つき", "げつ", "つき", 4, "Thời gian", hint = "Hình trăng khuyết với hai vạch sáng bên trong.", ex("月曜日です。", "げつようび です。", "Là thứ Hai."), ex("一月です。", "いちがつ です。", "Là tháng Một.")),
        k("年", "năm", "ネン", "とし", "ねん", "とし", 6, "Thời gian", hint = "Cây lúa chín được gặt mỗi năm một lần.", ex("今年は二千二十六年です。", "ことし は にせんにじゅうろくねん です。", "Năm nay là năm 2026."), ex("一年です。", "いちねん です。", "Là một năm.")),
        k("時", "giờ; thời gian", "ジ", "とき", "じ", "とき", 10, "Thời gian", hint = "Mặt trời 日 đi cùng ngôi chùa 寺 để ghi thời khắc.", ex("今、三時です。", "いま、さんじ です。", "Bây giờ là 3 giờ."), ex("何時ですか。", "なんじ ですか。", "Mấy giờ rồi?")),
        k("分", "phút; phần", "ブン・フン・プン", "わける", "ふん", "わける", 4, "Thời gian", hint = "Con dao 刀 chia vật thành nhiều phần.", ex("五分です。", "ごふん です。", "Là 5 phút."), ex("十分です。", "じゅっぷん です。", "Là 10 phút.")),
        k("半", "một nửa", "ハン", "なかば", "はん", "なかば", 5, "Thời gian", hint = "Một vật được chia đôi ở chính giữa.", ex("三時半です。", "さんじはん です。", "Là 3 giờ rưỡi."), ex("半分ください。", "はんぶん ください。", "Cho tôi một nửa.")),

        k("人", "người", "ジン・ニン", "ひと", "じん", "ひと", 2, "Con người", hint = "Hai chân của một người đang bước đi.", ex("あの人はだれですか。", "あの ひと は だれ ですか。", "Người kia là ai?"), ex("ベトナム人です。", "ベトナムじん です。", "Tôi là người Việt Nam.")),
        k("女", "nữ; phụ nữ", "ジョ", "おんな", "じょ", "おんな", 3, "Con người", hint = "Hình người phụ nữ ngồi quỳ thời xưa.", ex("女の人です。", "おんな の ひと です。", "Là một người phụ nữ."), ex("女の子がいます。", "おんな の こ が います。", "Có một bé gái.")),
        k("男", "nam; đàn ông", "ダン・ナン", "おとこ", "だん", "おとこ", 7, "Con người", hint = "Ruộng 田 cộng sức 力: người đàn ông làm đồng.", ex("男の人です。", "おとこ の ひと です。", "Là một người đàn ông."), ex("男の子がいます。", "おとこ の こ が います。", "Có một bé trai.")),
        k("子", "trẻ em; con", "シ", "こ", "し", "こ", 3, "Con người", hint = "Hình em bé dang hai tay.", ex("子どもが二人います。", "こども が ふたり います。", "Tôi có hai người con."), ex("この子はだれですか。", "この こ は だれ ですか。", "Đứa trẻ này là ai?")),

        k("山", "núi", "サン", "やま", "さん", "やま", 3, "Tự nhiên", hint = "Ba đỉnh núi, đỉnh giữa cao nhất.", ex("富士山です。", "ふじさん です。", "Đó là núi Phú Sĩ."), ex("山へ行きます。", "やま へ いきます。", "Tôi đi lên núi.")),
        k("川", "sông", "セン", "かわ", "せん", "かわ", 3, "Tự nhiên", hint = "Ba dòng nước chảy song song.", ex("川はきれいです。", "かわ は きれい です。", "Con sông đẹp."), ex("川へ行きます。", "かわ へ いきます。", "Tôi đi ra sông.")),
        k("木", "cây; gỗ", "モク・ボク", "き", "もく", "き", 4, "Tự nhiên", hint = "Thân cây ở giữa, cành và rễ tỏa hai bên.", ex("大きい木です。", "おおきい き です。", "Đó là cây lớn."), ex("木曜日です。", "もくようび です。", "Là thứ Năm.")),
        k("火", "lửa", "カ", "ひ", "か", "ひ", 4, "Tự nhiên", hint = "Ngọn lửa bùng lên với tia lửa hai bên.", ex("火曜日です。", "かようび です。", "Là thứ Ba."), ex("火を見ます。", "ひ を みます。", "Tôi nhìn ngọn lửa.")),
        k("金", "vàng; tiền", "キン・コン", "かね", "きん", "かね", 8, "Tự nhiên", hint = "Kim loại quý nằm dưới mái đất.", ex("金曜日です。", "きんようび です。", "Là thứ Sáu."), ex("お金はいくらですか。", "おかね は いくら ですか。", "Số tiền là bao nhiêu?")),
        k("土", "đất", "ド・ト", "つち", "ど", "つち", 3, "Tự nhiên", hint = "Mầm cây nhô lên khỏi mặt đất.", ex("土曜日です。", "どようび です。", "Là thứ Bảy."), ex("土はくろいです。", "つち は くろい です。", "Đất màu đen.")),

        k("学", "học", "ガク", "まなぶ", "がく", "まなぶ", 8, "Trường học", hint = "Đứa trẻ 子 học dưới mái trường.", ex("日本語を学びます。", "にほんご を まなびます。", "Tôi học tiếng Nhật."), ex("学生です。", "がくせい です。", "Tôi là học sinh/sinh viên.")),
        k("校", "trường học", "コウ", "—", "こう", null, 10, "Trường học", hint = "Cây 木 cạnh nơi người học giao nhau.", ex("学校へ行きます。", "がっこう へ いきます。", "Tôi đi đến trường."), ex("学校はどこですか。", "がっこう は どこ ですか。", "Trường học ở đâu?")),
        k("先", "trước; trước tiên", "セン", "さき", "せん", "さき", 6, "Trường học", hint = "Người 儿 đi trước mọi người.", ex("先生です。", "せんせい です。", "Là giáo viên."), ex("先に行きます。", "さき に いきます。", "Tôi đi trước.")),
        k("生", "sống; sinh", "セイ・ショウ", "いきる・うまれる・なま", "せい", "いきる", 5, "Trường học", hint = "Mầm cây mọc lên khỏi mặt đất: sự sống.", ex("学生です。", "がくせい です。", "Tôi là học sinh/sinh viên."), ex("先生は日本人です。", "せんせい は にほんじん です。", "Giáo viên là người Nhật.")),
        k("本", "sách; gốc", "ホン", "もと", "ほん", "もと", 5, "Trường học", hint = "Chữ 木 có dấu ở gốc cây; từ đó mang nghĩa gốc và sách.", ex("日本語の本です。", "にほんご の ほん です。", "Đây là sách tiếng Nhật."), ex("本を読みます。", "ほん を よみます。", "Tôi đọc sách.")),
        k("語", "ngôn ngữ; lời nói", "ゴ", "かたる", "ご", "かたる", 14, "Trường học", hint = "Lời 言 đi cùng âm 吾 tạo thành ngôn ngữ.", ex("日本語を話します。", "にほんご を はなします。", "Tôi nói tiếng Nhật."), ex("英語ができます。", "えいご が できます。", "Tôi biết tiếng Anh.")),
        k("国", "đất nước", "コク", "くに", "こく", "くに", 8, "Trường học", hint = "Viên ngọc 玉 được bảo vệ trong đường biên 囗 của quốc gia.", ex("国はベトナムです。", "くに は ベトナム です。", "Đất nước của tôi là Việt Nam."), ex("外国へ行きます。", "がいこく へ いきます。", "Tôi đi nước ngoài.")),
    )
}
