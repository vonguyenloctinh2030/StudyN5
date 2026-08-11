package com.studyn5.kana.ui.lessons

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.LessonDefinition
import com.studyn5.kana.data.LessonItem
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaCardShape
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaRed
import com.studyn5.kana.ui.theme.KanaSmallShape

@Composable
fun LessonsScreen(viewModel: LessonViewModel, onBack: () -> Unit, onSpeak: (String) -> Unit) {
    if (viewModel.selectedLesson.value == null) LessonList(viewModel, onBack) else LessonPlayer(viewModel, onSpeak)
}

@Composable
private fun LessonList(viewModel: LessonViewModel, onBack: () -> Unit) {
    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
            ScreenHeader("Lessons", "9 chặng luyện đọc · 100 từ mỗi bài", onBack)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(11.dp)) {
                items(viewModel.lessons, key = LessonDefinition::id) { lesson ->
                    LessonCard(lesson) { viewModel.openLesson(lesson) }
                }
                item { Spacer(Modifier.height(18.dp)) }
            }
        }
    }
}

@Composable
private fun ScreenHeader(title: String, subtitle: String, onBack: () -> Unit) {
    Row(Modifier.padding(top = 9.dp, bottom = 15.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Quay lại", tint = MaterialTheme.colorScheme.primary) }
        Column {
            Text(title, fontSize = 23.sp, fontWeight = FontWeight.Black)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun LessonCard(lesson: LessonDefinition, onClick: () -> Unit) {
    val accent = if (lesson.id % 3 == 0) KanaRed else MaterialTheme.colorScheme.primary
    Row(
        Modifier
            .fillMaxWidth()
            .shadow(2.dp, KanaCardShape)
            .clip(KanaCardShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape)
            .clickable(onClick = onClick)
            .padding(13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(60.dp).clip(KanaSmallShape).background(accent), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("${lesson.id}", color = Color.White.copy(alpha = .68f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(lesson.symbol, color = Color.White, fontFamily = KanaFontFamily, fontSize = if (lesson.symbol.length > 1) 21.sp else 27.sp, fontWeight = FontWeight.Bold)
            }
        }
        Column(Modifier.weight(1f).padding(horizontal = 13.dp)) {
            Text(lesson.title, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(3.dp))
            Text(lesson.description, fontSize = 11.sp, lineHeight = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("100 từ · 2–8 ký tự", fontSize = 10.sp, color = accent, fontWeight = FontWeight.Bold)
        }
        Box(Modifier.size(30.dp).clip(CircleShape).background(accent.copy(alpha = .1f)), contentAlignment = Alignment.Center) {
            Text("›", fontSize = 24.sp, color = accent)
        }
    }
}

@Composable
private fun LessonPlayer(viewModel: LessonViewModel, onSpeak: (String) -> Unit) {
    val lesson = viewModel.selectedLesson.value ?: return
    val item = viewModel.current.value ?: return
    var showMeaning by remember(item.audioKey) { mutableStateOf(false) }
    val position = viewModel.position.value

    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
            Row(Modifier.padding(top = 9.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = viewModel::backToLessons) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Danh sách Lessons", tint = MaterialTheme.colorScheme.primary) }
                Column(Modifier.weight(1f)) {
                    Text("Lesson ${lesson.id}", fontSize = 11.sp, color = KanaRed, fontWeight = FontWeight.ExtraBold)
                    Text(lesson.title, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                }
                Text("$position / 100", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
            }
            LinearProgressIndicator(
                progress = position / 100f,
                modifier = Modifier.fillMaxWidth().height(5.dp).clip(CircleShape),
                color = KanaRed,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Spacer(Modifier.height(18.dp))
            KanaCard(item)
            if (showMeaning) {
                Spacer(Modifier.height(11.dp))
                AnswerCard(item)
            } else {
                Spacer(Modifier.height(11.dp))
                Box(Modifier.fillMaxWidth().height(91.dp).clip(KanaCardShape).border(1.dp, MaterialTheme.colorScheme.outline, KanaCardShape), contentAlignment = Alignment.Center) {
                    Text("Nhấn “Hiện nghĩa” để kiểm tra đáp án", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.height(15.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                LessonButton("♪  Nghe", true, MaterialTheme.colorScheme.primary, Modifier.weight(1f)) { onSpeak(item.audioKey) }
                LessonButton(if (showMeaning) "Ẩn nghĩa" else "Hiện nghĩa", false, MaterialTheme.colorScheme.primary, Modifier.weight(1f)) { showMeaning = !showMeaning }
            }
            Spacer(Modifier.height(10.dp))
            LessonButton("Tiếp theo  →", true, KanaRed, Modifier.fillMaxWidth(), viewModel::next)
        }
    }
}

@Composable
private fun KanaCard(item: LessonItem) {
    val kanaSize = when (item.kana.length) { 2, 3 -> 76.sp; 4, 5 -> 64.sp; 6 -> 56.sp; else -> 48.sp }
    Box(
        Modifier.fillMaxWidth().height(280.dp).shadow(3.dp, KanaCardShape).clip(KanaCardShape)
            .background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("ĐỌC KANA", color = KanaRed, fontSize = 10.sp, letterSpacing = 1.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(16.dp))
            Text(item.kana, fontFamily = KanaFontFamily, fontSize = kanaSize, lineHeight = (kanaSize.value + 10).sp, textAlign = TextAlign.Center, maxLines = 2)
        }
    }
}

@Composable
private fun AnswerCard(item: LessonItem) {
    Row(
        Modifier.fillMaxWidth().height(91.dp).clip(KanaCardShape).background(MaterialTheme.colorScheme.tertiaryContainer).padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(10.dp).clip(CircleShape).background(MaterialTheme.colorScheme.tertiary))
        Column(Modifier.padding(start = 12.dp)) {
            Text(item.romaji, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onTertiaryContainer)
            Text(item.meaning, fontSize = 14.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
        }
    }
}

@Composable
private fun LessonButton(text: String, filled: Boolean, color: Color, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier.clip(KanaSmallShape).background(if (filled) color else MaterialTheme.colorScheme.surface)
            .border(1.dp, color, KanaSmallShape).clickable(onClick = onClick).padding(horizontal = 10.dp, vertical = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, color = if (filled) Color.White else color, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, textAlign = TextAlign.Center)
    }
}
