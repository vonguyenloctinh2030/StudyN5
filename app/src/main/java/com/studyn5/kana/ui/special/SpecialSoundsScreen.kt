package com.studyn5.kana.ui.special

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.ForeignSound
import com.studyn5.kana.data.ForeignSoundData
import com.studyn5.kana.data.ForeignSoundGroup
import com.studyn5.kana.data.ForeignWord
import com.studyn5.kana.data.SpecialSoundCategory
import com.studyn5.kana.data.SpecialSoundData
import com.studyn5.kana.data.SpecialSoundEntry
import com.studyn5.kana.data.SpecialSoundLesson
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaCardShape
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaRed
import com.studyn5.kana.ui.theme.KanaSmallShape

@Composable
fun SpecialSoundsScreen(
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var foreignGroupIndex by remember { mutableIntStateOf(0) }
    var isPracticing by remember { mutableStateOf(false) }
    val lessons = SpecialSoundData.lessons
    val lesson = lessons[selectedIndex]
    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex, foreignGroupIndex) {
        listState.scrollToItem(0)
        isPracticing = false
    }

    if (isPracticing) {
        ForeignPracticeScreen(
            group = ForeignSoundData.groups[foreignGroupIndex],
            onSpeak = onSpeak,
            onBack = { isPracticing = false },
        )
        return
    }

    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            ScreenHeader(onBack)
            CategoryTabs(
                lessons = lessons,
                selectedIndex = selectedIndex,
                onSelect = { selectedIndex = it },
            )

            if (lesson.category == SpecialSoundCategory.GAIRAIGO) {
                ForeignSoundContent(
                    selectedGroupIndex = foreignGroupIndex,
                    onSelectGroup = { foreignGroupIndex = it },
                    onSpeak = onSpeak,
                    onPractice = { isPracticing = true },
                    listState = listState,
                    modifier = Modifier.weight(1f),
                )
            } else {
                LessonContent(
                    lesson = lesson,
                    onSpeak = onSpeak,
                    listState = listState,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun ScreenHeader(onBack: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(start = 8.dp, top = 10.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Quay lại", tint = MaterialTheme.colorScheme.primary)
        }
        Column {
            Text("Âm đặc biệt", fontSize = 22.sp, fontWeight = FontWeight.Black)
            Text(
                "Nhìn quy tắc · nghe âm · nhớ qua ví dụ",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CategoryTabs(
    lessons: List<SpecialSoundLesson>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    Row(
        Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        lessons.forEachIndexed { index, item ->
            val selected = index == selectedIndex
            Box(
                Modifier.clip(RoundedCornerShape(50))
                    .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                    .border(
                        1.dp,
                        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(50),
                    )
                    .clickable { onSelect(index) }
                    .padding(horizontal = 14.dp, vertical = 9.dp),
            ) {
                Text(
                    item.category.label,
                    fontSize = 13.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                    color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun ForeignSoundContent(
    selectedGroupIndex: Int,
    onSelectGroup: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    onPractice: () -> Unit,
    listState: LazyListState,
    modifier: Modifier,
) {
    val groups = ForeignSoundData.groups
    val group = groups[selectedGroupIndex]
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, bottom = 26.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp),
    ) {
        item { ForeignIntroCard() }
        item {
            Row(
                Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp),
            ) {
                groups.forEachIndexed { index, item ->
                    val selected = index == selectedGroupIndex
                    Box(
                        Modifier.clip(KanaSmallShape)
                            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                            .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, KanaSmallShape)
                            .clickable { onSelectGroup(index) }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(item.shortLabel, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
        item {
            Column {
                Text(group.title, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                Text(group.rule, fontSize = 12.sp, lineHeight = 17.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item { SoundGrid(group.sounds, onSpeak) }
        item {
            Text(
                "TỪ VÍ DỤ",
                modifier = Modifier.padding(top = 5.dp, start = 3.dp),
                fontSize = 11.sp,
                letterSpacing = .8.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        items(group.words, key = ForeignWord::audioKey) { word ->
            ForeignWordRow(word, onSpeak)
        }
        item {
            Box(
                Modifier.fillMaxWidth().clip(KanaSmallShape).background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = onPractice).padding(vertical = 15.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("Luyện ${group.shortLabel}", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
        item { TipCard("Đọc cả từ sau khi nghe âm riêng. Não sẽ nhớ tổ hợp nhanh hơn khi gặp trong một từ có nghĩa.") }
    }
}

@Composable
private fun ForeignIntroCard() {
    Column(
        Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.primary).padding(17.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Katakana mở rộng", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        Text("Âm dùng để phiên âm từ nước ngoài", color = Color.White.copy(alpha = .75f), fontSize = 12.sp)
        Spacer(Modifier.height(13.dp))
        Text("フ  +  ァ  →  ファ", color = Color.White, fontFamily = KanaFontFamily, fontSize = 31.sp, fontWeight = FontWeight.Bold)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text("fu + a nhỏ", color = Color.White.copy(alpha = .7f), fontSize = 11.sp)
            Text("fa", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SoundGrid(sounds: List<ForeignSound>, onSpeak: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        sounds.chunked(4).forEach { rowSounds ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowSounds.forEach { sound ->
                    SoundTile(sound, Modifier.weight(1f), onSpeak)
                }
                repeat(4 - rowSounds.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun SoundTile(sound: ForeignSound, modifier: Modifier, onSpeak: (String) -> Unit) {
    Column(
        modifier.height(132.dp).clip(KanaSmallShape).background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaSmallShape)
            .clickable { onSpeak(sound.audioKey) }.padding(vertical = 9.dp, horizontal = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(sound.kana, fontFamily = KanaFontFamily, fontSize = 25.sp)
        Text(sound.romaji, color = KanaRed, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
        Text(sound.formation, fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
        Spacer(Modifier.height(6.dp))
        SpeakerButton { onSpeak(sound.audioKey) }
    }
}

@Composable
private fun ForeignWordRow(word: ForeignWord, onSpeak: (String) -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(KanaSmallShape).background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaSmallShape)
            .clickable { onSpeak(word.audioKey) }.padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(word.kana, modifier = Modifier.weight(.95f), fontFamily = KanaFontFamily, fontSize = if (word.kana.length > 7) 20.sp else 24.sp, maxLines = 1)
        Column(Modifier.weight(1.1f).padding(horizontal = 9.dp)) {
            Text(word.romaji, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
            Text(word.meaning, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
        }
        SpeakerButton { onSpeak(word.audioKey) }
    }
}

@Composable
private fun SpeakerButton(onClick: () -> Unit) {
    Box(
        Modifier.size(36.dp).clip(CircleShape).background(KanaRed.copy(alpha = .11f)).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        SpeakerGlyph(color = KanaRed, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun ForeignPracticeScreen(group: ForeignSoundGroup, onSpeak: (String) -> Unit, onBack: () -> Unit) {
    var deck by remember(group.id) { mutableStateOf(group.words.shuffled()) }
    var position by remember(group.id) { mutableIntStateOf(0) }
    val word = deck[position]
    var showMeaning by remember(word.audioKey) { mutableStateOf(false) }

    fun next() {
        if (position < deck.lastIndex) {
            position++
        } else {
            val previous = deck.lastOrNull()
            var nextDeck = group.words.shuffled()
            if (nextDeck.size > 1 && nextDeck.first() == previous) {
                nextDeck = nextDeck.drop(1) + nextDeck.first()
            }
            deck = nextDeck
            position = 0
        }
    }

    BackHandler(onBack = onBack)
    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
            Row(Modifier.padding(top = 9.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Quay lại", tint = MaterialTheme.colorScheme.primary) }
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Ôn âm ngoại lai", fontSize = 18.sp, fontWeight = FontWeight.Black)
                    Text(group.title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("${position + 1} / ${deck.size}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            LinearProgressIndicator(
                progress = (position + 1f) / deck.size,
                modifier = Modifier.fillMaxWidth().height(5.dp).clip(CircleShape),
                color = MaterialTheme.colorScheme.tertiary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Spacer(Modifier.height(18.dp))
            Box(
                Modifier.fillMaxWidth().height(320.dp).clip(KanaCardShape).background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(18.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("HÃY ĐỌC", color = MaterialTheme.colorScheme.tertiary, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.ExtraBold)
                    Spacer(Modifier.height(22.dp))
                    Text(word.kana, fontFamily = KanaFontFamily, fontSize = if (word.kana.length > 7) 48.sp else 61.sp, lineHeight = 70.sp, textAlign = TextAlign.Center, maxLines = 2)
                    Spacer(Modifier.height(20.dp))
                    Text(word.kana.toList().joinToString(" · "), fontFamily = KanaFontFamily, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.height(12.dp))
            if (showMeaning) {
                Column(
                    Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.tertiaryContainer).padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(word.romaji, fontSize = 21.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    Text(word.meaning, fontSize = 15.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                    Spacer(Modifier.height(9.dp))
                    Text(word.focus, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
            } else {
                Box(
                    Modifier.fillMaxWidth().height(94.dp).clip(KanaCardShape).border(1.dp, MaterialTheme.colorScheme.outline, KanaCardShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Nghe thử hoặc hiện đáp án", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                PracticeButton("Nghe âm", false, Modifier.weight(1f), leadingSpeaker = true) { onSpeak(word.audioKey) }
                PracticeButton(if (showMeaning) "Ẩn nghĩa" else "Hiện nghĩa", false, Modifier.weight(1f)) { showMeaning = !showMeaning }
            }
            Spacer(Modifier.height(10.dp))
            PracticeButton("Tiếp theo  →", true, Modifier.fillMaxWidth(), onClick = ::next)
        }
    }
}

@Composable
private fun PracticeButton(
    text: String,
    filled: Boolean,
    modifier: Modifier,
    leadingSpeaker: Boolean = false,
    onClick: () -> Unit,
) {
    val color = if (filled) KanaRed else MaterialTheme.colorScheme.primary
    Box(
        modifier.clip(KanaSmallShape).background(if (filled) color else MaterialTheme.colorScheme.surface)
            .border(1.dp, color, KanaSmallShape).clickable(onClick = onClick).padding(vertical = 14.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            if (leadingSpeaker) {
                SpeakerGlyph(
                    color = if (filled) Color.White else color,
                    modifier = Modifier.size(19.dp),
                )
                Spacer(Modifier.width(7.dp))
            }
            Text(text, color = if (filled) Color.White else color, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun SpeakerGlyph(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val speaker = Path().apply {
            moveTo(size.width * .08f, size.height * .38f)
            lineTo(size.width * .30f, size.height * .38f)
            lineTo(size.width * .53f, size.height * .18f)
            lineTo(size.width * .53f, size.height * .82f)
            lineTo(size.width * .30f, size.height * .62f)
            lineTo(size.width * .08f, size.height * .62f)
            close()
        }
        drawPath(speaker, color)
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)
        drawArc(
            color = color,
            startAngle = -55f,
            sweepAngle = 110f,
            useCenter = false,
            topLeft = Offset(size.width * .47f, size.height * .28f),
            size = Size(size.width * .27f, size.height * .44f),
            style = stroke,
        )
        drawArc(
            color = color,
            startAngle = -55f,
            sweepAngle = 110f,
            useCenter = false,
            topLeft = Offset(size.width * .42f, size.height * .14f),
            size = Size(size.width * .48f, size.height * .72f),
            style = stroke,
        )
    }
}

@Composable
private fun LessonContent(
    lesson: SpecialSoundLesson,
    onSpeak: (String) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, bottom = 26.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        item { RuleCard(lesson) }
        lesson.groups.forEach { group ->
            item {
                Text(
                    group.title.uppercase(),
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp, bottom = 1.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = .7.sp,
                )
            }
            items(group.entries) { entry -> SoundRow(entry, onSpeak) }
        }
        item { TipCard(lesson.tip) }
    }
}

@Composable
private fun RuleCard(lesson: SpecialSoundLesson) {
    Column(
        Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.primaryContainer).padding(16.dp),
    ) {
        Text(lesson.title, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        Text(lesson.explanation, Modifier.padding(top = 3.dp), fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(
            Modifier.fillMaxWidth().padding(top = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            RuleValue(lesson.ruleBefore, Modifier.weight(1f))
            Text("→", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            RuleValue(lesson.ruleAfter, Modifier.weight(1f))
        }
    }
}

@Composable
private fun RuleValue(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier.clip(KanaSmallShape).background(MaterialTheme.colorScheme.surface).padding(horizontal = 8.dp, vertical = 13.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, fontFamily = KanaFontFamily, fontSize = 17.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun SoundRow(entry: SpecialSoundEntry, onSpeak: (String) -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(KanaSmallShape).background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaSmallShape)
            .clickable { onSpeak(entry.audioKey) }.padding(start = 14.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(entry.kana, Modifier.weight(.9f), fontFamily = KanaFontFamily, fontSize = if (entry.kana.length > 5) 20.sp else 23.sp, fontWeight = FontWeight.Medium)
        Column(Modifier.weight(1.15f)) {
            Text(entry.romaji, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Text(entry.formation, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        SpeakerButton { onSpeak(entry.audioKey) }
    }
}

@Composable
private fun TipCard(tip: String) {
    Row(
        Modifier.fillMaxWidth().padding(top = 5.dp).clip(KanaSmallShape)
            .background(MaterialTheme.colorScheme.tertiaryContainer).padding(13.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text("◆", fontSize = 15.sp, color = MaterialTheme.colorScheme.tertiary)
        Column {
            Text("Mẹo dễ nhớ", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onTertiaryContainer)
            Spacer(Modifier.height(2.dp))
            Text(tip, fontSize = 12.sp, lineHeight = 17.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
        }
    }
}
