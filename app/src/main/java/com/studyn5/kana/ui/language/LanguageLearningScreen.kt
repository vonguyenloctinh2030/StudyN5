package com.studyn5.kana.ui.language

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.DialogueScenario
import com.studyn5.kana.data.GrammarPattern
import com.studyn5.kana.data.LanguageLearningData
import com.studyn5.kana.data.LanguageLesson
import com.studyn5.kana.data.VocabularyEntry
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaCardShape
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaJade
import com.studyn5.kana.ui.theme.KanaRed
import com.studyn5.kana.ui.theme.KanaSmallShape

private enum class LanguagePage { LESSONS, HUB, VOCABULARY, GRAMMAR, PRACTICE, DIALOGUE }

@Composable
fun LanguageLearningScreen(
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onSpeakDialogue: (List<String>) -> Unit,
) {
    var page by remember { mutableStateOf(LanguagePage.LESSONS) }
    var lesson by remember { mutableStateOf<LanguageLesson?>(null) }

    fun goBack() {
        when (page) {
            LanguagePage.LESSONS -> onBack()
            LanguagePage.HUB -> { page = LanguagePage.LESSONS; lesson = null }
            else -> page = LanguagePage.HUB
        }
    }
    BackHandler(onBack = ::goBack)

    when (page) {
        LanguagePage.LESSONS -> LanguageLessonList(
            onBack = onBack,
            onOpen = { lesson = it; page = LanguagePage.HUB },
        )
        LanguagePage.HUB -> lesson?.let { selected ->
            LessonHub(selected, ::goBack) { page = it }
        }
        LanguagePage.VOCABULARY -> lesson?.let { VocabularyList(it, ::goBack, onSpeak) }
        LanguagePage.GRAMMAR -> lesson?.let { GrammarList(it, ::goBack, onSpeak) }
        LanguagePage.PRACTICE -> lesson?.let { QuickPractice(it, ::goBack, onSpeak) }
        LanguagePage.DIALOGUE -> lesson?.let { DialoguePractice(it, ::goBack, onSpeak, onSpeakDialogue) }
    }
}

@Composable
private fun Header(title: String, subtitle: String? = null, onBack: () -> Unit, trailing: String? = null) {
    Row(Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 13.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Quay lại", tint = MaterialTheme.colorScheme.primary)
        }
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 21.sp, fontWeight = FontWeight.Black)
            if (subtitle != null) Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        if (trailing != null) Text(trailing, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = KanaRed)
    }
}

@Composable
private fun LanguageLessonList(onBack: () -> Unit, onOpen: (LanguageLesson) -> Unit) {
    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
            Header("Bài học", "Từ vựng · ngữ pháp · luyện tập · hội thoại", onBack)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(LanguageLearningData.lessons, key = LanguageLesson::id) { lesson -> LessonRow(lesson) { onOpen(lesson) } }
                item { ComingLessonRow(LanguageLearningData.lessons.size + 1) }
                item { Spacer(Modifier.height(18.dp)) }
            }
        }
    }
}

@Composable
private fun LessonRow(lesson: LanguageLesson, onClick: () -> Unit) {
    val accent = listOf(KanaRed, MaterialTheme.colorScheme.primary, KanaJade, Color(0xFFB87432))[(lesson.id - 1) % 4]
    Row(
        Modifier.fillMaxWidth().shadow(1.dp, KanaCardShape).clip(KanaCardShape)
            .background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape)
            .clickable(onClick = onClick).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(68.dp).clip(KanaSmallShape).background(accent.copy(alpha = .11f)), contentAlignment = Alignment.Center) {
            Text(lesson.symbol, fontFamily = KanaFontFamily, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = accent)
            Text(lesson.id.toString(), Modifier.align(Alignment.TopStart).padding(6.dp), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = accent)
        }
        Column(Modifier.weight(1f).padding(horizontal = 13.dp)) {
            Text("Bài ${lesson.id} · ${lesson.title}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
            Text(lesson.subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
            Spacer(Modifier.height(6.dp))
            Text("${lesson.vocabulary.size} từ · ${lesson.grammar.size} mẫu · 25 luyện nhanh · 25 hội thoại", fontSize = 9.sp, color = accent, fontWeight = FontWeight.Bold)
        }
        Text("›", fontSize = 28.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ComingLessonRow(number: Int) {
    Row(
        Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.surface.copy(alpha = .55f))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(68.dp).clip(KanaSmallShape).border(1.dp, MaterialTheme.colorScheme.outline, KanaSmallShape), contentAlignment = Alignment.Center) {
            Text("＋", fontSize = 27.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Column(Modifier.padding(start = 13.dp)) {
            Text("Bài $number · Sắp cập nhật", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Cấu trúc sẵn sàng cho các bài tiếp theo", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun LessonHub(lesson: LanguageLesson, onBack: () -> Unit, onOpen: (LanguagePage) -> Unit) {
    KanaBackground(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
            item { Header("Bài ${lesson.id} · ${lesson.title}", lesson.subtitle, onBack) }
            item {
                Row(
                    Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.primary).padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(lesson.symbol, fontFamily = KanaFontFamily, fontSize = 52.sp, color = Color.White)
                    Column(Modifier.padding(start = 18.dp)) {
                        Text("MỤC TIÊU BÀI HỌC", color = Color.White.copy(alpha = .7f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(lesson.subtitle, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
            item { Spacer(Modifier.height(14.dp)) }
            item { ModuleRow("語", "Từ vựng", "${lesson.vocabulary.size} từ · danh sách theo nhóm", KanaJade) { onOpen(LanguagePage.VOCABULARY) } }
            item { Spacer(Modifier.height(10.dp)) }
            item { ModuleRow("文", "Ngữ pháp", "${lesson.grammar.size} mẫu câu · nhiều ví dụ", MaterialTheme.colorScheme.primary) { onOpen(LanguagePage.GRAMMAR) } }
            item { Spacer(Modifier.height(10.dp)) }
            item { ModuleRow("⚡", "Luyện nhanh", "25 câu · nghe, chọn và xem giải thích", KanaRed) { onOpen(LanguagePage.PRACTICE) } }
            item { Spacer(Modifier.height(10.dp)) }
            item { ModuleRow("話", "Luyện hội thoại", "25 tình huống · nghe từng câu hoặc toàn đoạn", Color(0xFF7358B8)) { onOpen(LanguagePage.DIALOGUE) } }
            item { Spacer(Modifier.height(14.dp)) }
            item {
                Row(Modifier.fillMaxWidth().clip(KanaSmallShape).background(MaterialTheme.colorScheme.primaryContainer).padding(13.dp), verticalAlignment = Alignment.CenterVertically) {
                    SpeakerGlyph(MaterialTheme.colorScheme.primary, Modifier.size(22.dp))
                    Text("Mọi mục đều có chữ Nhật, romaji, nghĩa, ví dụ và âm thanh.", Modifier.padding(start = 10.dp), fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }
    }
}

@Composable
private fun ModuleRow(symbol: String, title: String, subtitle: String, color: Color, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).clickable(onClick = onClick).padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(52.dp).clip(CircleShape).background(color.copy(alpha = .12f)), contentAlignment = Alignment.Center) {
            Text(symbol, fontFamily = KanaFontFamily, fontSize = 24.sp, color = color, fontWeight = FontWeight.Bold)
        }
        Column(Modifier.weight(1f).padding(horizontal = 13.dp)) {
            Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text("›", fontSize = 29.sp, color = color)
    }
}

@Composable
private fun VocabularyList(lesson: LanguageLesson, onBack: () -> Unit, onSpeak: (String) -> Unit) {
    val categories = listOf("Tất cả") + lesson.vocabulary.map(VocabularyEntry::category).distinct()
    var category by remember(lesson.id) { mutableStateOf("Tất cả") }
    val visible = if (category == "Tất cả") lesson.vocabulary else lesson.vocabulary.filter { it.category == category }
    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Header("Từ vựng", "Bài ${lesson.id} · ${lesson.title}", onBack, "${visible.size} từ")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                items(categories) { item ->
                    val selected = item == category
                    Text(item, Modifier.clip(CircleShape).background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                        .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, CircleShape)
                        .clickable { category = item }.padding(horizontal = 13.dp, vertical = 8.dp),
                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(10.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(visible, key = { it.category + it.japanese + it.meaning }) { item -> VocabularyCard(item, onSpeak) }
                item { Spacer(Modifier.height(18.dp)) }
            }
        }
    }
}

@Composable
private fun VocabularyCard(item: VocabularyEntry, onSpeak: (String) -> Unit) {
    Column(
        Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1.35f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        item.japanese,
                        fontFamily = KanaFontFamily,
                        fontSize = when {
                            item.japanese.length > 7 -> 16.sp
                            item.japanese.length > 5 -> 19.sp
                            else -> 25.sp
                        },
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    item.referenceJapanese?.let { reference ->
                        Text(
                            reference,
                            Modifier.padding(start = 9.dp).clip(CircleShape).background(Color(0xFFFFE7C2))
                                .padding(horizontal = 9.dp, vertical = 4.dp),
                            fontFamily = KanaFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A5A13),
                        )
                    }
                }
                Text(item.romaji, fontSize = 12.sp, color = KanaRed, fontWeight = FontWeight.Bold)
            }
            Text(item.meaning, Modifier.weight(.65f), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            SpeakerButton { onSpeak(item.japanese) }
        }
        Spacer(Modifier.height(9.dp))
        Column(Modifier.fillMaxWidth().clip(KanaSmallShape).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .55f)).padding(11.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("VÍ DỤ", fontSize = 9.sp, color = KanaJade, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.weight(1f))
                SpeakerButton(size = 34) { onSpeak(item.exampleJapanese) }
            }
            Text(item.exampleJapanese, fontFamily = KanaFontFamily, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Text(item.exampleRomaji, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(item.exampleMeaning, fontSize = 11.sp)
        }
    }
}

@Composable
private fun GrammarList(lesson: LanguageLesson, onBack: () -> Unit, onSpeak: (String) -> Unit) {
    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Header("Ngữ pháp", "Bài ${lesson.id} · ${lesson.title}", onBack, "${lesson.grammar.size} mẫu")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(lesson.grammar.withIndex().toList()) { indexed -> GrammarCard(indexed.index + 1, indexed.value, onSpeak) }
                item { Spacer(Modifier.height(18.dp)) }
            }
        }
    }
}

@Composable
private fun GrammarCard(number: Int, grammar: GrammarPattern, onSpeak: (String) -> Unit) {
    Column(Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(15.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("MẪU $number", Modifier.clip(CircleShape).background(KanaRed.copy(alpha = .1f)).padding(horizontal = 9.dp, vertical = 5.dp), color = KanaRed, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
            Text(grammar.title, Modifier.padding(start = 9.dp), fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
        }
        Text(grammar.formula, Modifier.fillMaxWidth().padding(vertical = 13.dp), fontFamily = KanaFontFamily, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
        Text(grammar.explanation, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(10.dp))
        grammar.examples.forEach { example ->
            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(6.dp).clip(CircleShape).background(KanaJade))
                Column(Modifier.weight(1f).padding(horizontal = 9.dp)) {
                    Text(example.japanese, fontFamily = KanaFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(example.romaji, fontSize = 9.sp, color = KanaRed)
                    Text(example.meaning, fontSize = 10.sp)
                }
                SpeakerButton(size = 34) { onSpeak(example.japanese) }
            }
        }
    }
}

@Composable
private fun QuickPractice(lesson: LanguageLesson, onBack: () -> Unit, onSpeak: (String) -> Unit) {
    val questions = remember(lesson.id) { LanguageLearningData.quickPractice(lesson) }
    var index by remember(lesson.id) { mutableIntStateOf(0) }
    var selected by remember(index) { mutableStateOf<String?>(null) }
    var checked by remember(index) { mutableStateOf(false) }
    val question = questions[index]
    val alternatives = remember(index, lesson.id) {
        (listOf(question.meaning) + lesson.vocabulary.asSequence().map { it.meaning }.filter { it != question.meaning }.distinct().drop(index % 5).take(3)).shuffled()
    }
    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
            Header("Luyện nhanh", "Chọn nghĩa đúng", onBack, "${index + 1} / 25")
            LinearProgressIndicator((index + 1) / 25f, Modifier.fillMaxWidth().height(5.dp).clip(CircleShape), color = KanaRed)
            Spacer(Modifier.height(18.dp))
            Column(Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        question.japanese,
                        fontFamily = KanaFontFamily,
                        fontSize = if (question.japanese.length > 6) 27.sp else 42.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                    question.referenceJapanese?.let { reference ->
                        Text(
                            reference,
                            Modifier.padding(start = 10.dp).clip(CircleShape).background(Color(0xFFFFE7C2))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            fontFamily = KanaFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9A5A13),
                        )
                    }
                }
                Text(question.romaji, fontSize = 13.sp, color = KanaRed)
                Spacer(Modifier.height(12.dp))
                SpeakerButton(size = 52) { onSpeak(question.japanese) }
            }
            Spacer(Modifier.height(14.dp))
            alternatives.forEach { answer ->
                val color = when {
                    checked && answer == question.meaning -> KanaJade
                    checked && answer == selected -> KanaRed
                    selected == answer -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outline
                }
                Row(Modifier.fillMaxWidth().clip(KanaSmallShape).background(MaterialTheme.colorScheme.surface).border(1.dp, color, KanaSmallShape).clickable(enabled = !checked) { selected = answer }.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(18.dp).clip(CircleShape).border(2.dp, color, CircleShape), contentAlignment = Alignment.Center) {
                        if (selected == answer) Box(Modifier.size(9.dp).clip(CircleShape).background(color))
                    }
                    Text(answer, Modifier.padding(start = 11.dp), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(8.dp))
            }
            if (checked) {
                Text("Ví dụ: ${question.exampleJapanese}\n${question.exampleRomaji}\n${question.exampleMeaning}", Modifier.fillMaxWidth().clip(KanaSmallShape).background(KanaJade.copy(alpha = .1f)).padding(11.dp), fontSize = 11.sp)
            }
            Spacer(Modifier.weight(1f))
            ActionButton(if (checked) "Tiếp theo" else "Kiểm tra", selected != null) {
                if (!checked) checked = true else { index = (index + 1) % 25 }
            }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun DialoguePractice(
    lesson: LanguageLesson,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onSpeakDialogue: (List<String>) -> Unit,
) {
    val dialogues = remember(lesson.id) { LanguageLearningData.dialogues(lesson) }
    var index by remember(lesson.id) { mutableIntStateOf(0) }
    var showMeaning by remember(index) { mutableStateOf(false) }
    val dialogue: DialogueScenario = dialogues[index]
    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 17.dp)) {
            Header("Luyện hội thoại", "Bài ${lesson.id} · ${dialogue.title}", onBack, "${index + 1} / 25")
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(dialogue.lines.withIndex().toList()) { indexed ->
                    val left = indexed.index % 2 == 0
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = if (left) Arrangement.Start else Arrangement.End) {
                        Column(Modifier.fillMaxWidth(.88f).clip(KanaCardShape).background(if (left) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.tertiaryContainer).padding(13.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(if (left) "A" else "B", fontWeight = FontWeight.Black, color = if (left) MaterialTheme.colorScheme.primary else KanaJade)
                                Column(Modifier.weight(1f).padding(horizontal = 9.dp)) {
                                    Text(indexed.value.japanese, fontFamily = KanaFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text(indexed.value.romaji, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    if (showMeaning) Text(indexed.value.meaning, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                }
                                SpeakerButton(size = 36) { onSpeak(indexed.value.japanese) }
                            }
                        }
                    }
                }
                item {
                    Text(if (showMeaning) "Ẩn nghĩa" else "Hiện nghĩa", Modifier.fillMaxWidth().clip(KanaSmallShape).border(1.dp, MaterialTheme.colorScheme.primary, KanaSmallShape).clickable { showMeaning = !showMeaning }.padding(13.dp), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(Modifier.weight(1f).clip(KanaSmallShape).border(1.dp, MaterialTheme.colorScheme.primary, KanaSmallShape).clickable {
                    onSpeakDialogue(dialogue.lines.map { it.japanese })
                }.padding(14.dp), contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) { SpeakerGlyph(MaterialTheme.colorScheme.primary, Modifier.size(18.dp)); Text("  Nghe toàn đoạn", fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                }
                Box(Modifier.weight(1f).clip(KanaSmallShape).background(KanaRed).clickable { index = (index + 1) % 25 }.padding(14.dp), contentAlignment = Alignment.Center) {
                    Text("Tiếp theo", color = Color.White, fontWeight = FontWeight.ExtraBold)
                }
            }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun ActionButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().clip(KanaSmallShape).background(if (enabled) KanaRed else MaterialTheme.colorScheme.surfaceVariant).clickable(enabled = enabled, onClick = onClick).padding(15.dp), contentAlignment = Alignment.Center) {
        Text(text, color = if (enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun SpeakerButton(size: Int = 42, onClick: () -> Unit) {
    Box(Modifier.size(size.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = .1f)).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        SpeakerGlyph(MaterialTheme.colorScheme.primary, Modifier.size((size * .48f).dp))
    }
}

@Composable
private fun SpeakerGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val speaker = Path().apply {
            moveTo(size.width * .08f, size.height * .38f)
            lineTo(size.width * .30f, size.height * .38f)
            lineTo(size.width * .52f, size.height * .18f)
            lineTo(size.width * .52f, size.height * .82f)
            lineTo(size.width * .30f, size.height * .62f)
            lineTo(size.width * .08f, size.height * .62f)
            close()
        }
        drawPath(speaker, color)
        drawArc(color, -48f, 96f, false, Offset(size.width * .45f, size.height * .30f), androidx.compose.ui.geometry.Size(size.width * .28f, size.height * .40f), style = androidx.compose.ui.graphics.drawscope.Stroke(size.width * .08f))
        drawArc(color, -48f, 96f, false, Offset(size.width * .44f, size.height * .15f), androidx.compose.ui.geometry.Size(size.width * .48f, size.height * .70f), style = androidx.compose.ui.graphics.drawscope.Stroke(size.width * .07f))
    }
}
