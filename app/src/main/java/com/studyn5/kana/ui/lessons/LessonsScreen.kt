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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.LessonDefinition
import com.studyn5.kana.data.LessonItem
import com.studyn5.kana.ui.theme.KanaFontFamily

@Composable
fun LessonsScreen(
    viewModel: LessonViewModel,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
) {
    if (viewModel.selectedLesson.value == null) {
        LessonList(viewModel, onBack)
    } else {
        LessonPlayer(viewModel, onSpeak)
    }
}

@Composable
private fun LessonList(viewModel: LessonViewModel, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
            }
            Column {
                Text("Lessons", fontSize = 21.sp, fontWeight = FontWeight.ExtraBold)
                Text(
                    "Chọn bài và luyện đủ 100 từ mỗi vòng",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(viewModel.lessons, key = LessonDefinition::id) { lesson ->
                LessonCard(lesson) { viewModel.openLesson(lesson) }
            }
            item { Spacer(Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun LessonCard(lesson: LessonDefinition, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                lesson.symbol,
                fontFamily = KanaFontFamily,
                fontSize = if (lesson.symbol.length > 1) 21.sp else 28.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
        Column(modifier = Modifier.weight(1f).padding(horizontal = 13.dp)) {
            Text("Lesson ${lesson.id}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
            Text(lesson.title, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            Text(lesson.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text("›", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun LessonPlayer(viewModel: LessonViewModel, onSpeak: (String) -> Unit) {
    val lesson = viewModel.selectedLesson.value ?: return
    val item = viewModel.current.value ?: return
    var showMeaning by remember(item.audioKey) { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = viewModel::backToLessons) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Danh sách Lesson")
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Lesson ${lesson.id} · ${lesson.title}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                Text(lesson.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                "${viewModel.position.value}/100",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
            )
        }

        Spacer(Modifier.height(18.dp))
        KanaCard(item, showMeaning)
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            LessonButton(
                text = "🔊 Nghe",
                filled = true,
                modifier = Modifier.weight(1f),
            ) { onSpeak(item.audioKey) }
            LessonButton(
                text = if (showMeaning) "Ẩn nghĩa" else "Hiện nghĩa",
                filled = false,
                modifier = Modifier.weight(1f),
            ) { showMeaning = !showMeaning }
        }
        Spacer(Modifier.height(10.dp))
        LessonButton(
            text = "Tiếp theo →",
            filled = false,
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::next,
        )
    }
}

@Composable
private fun KanaCard(item: LessonItem, showMeaning: Boolean) {
    val kanaSize = when (item.kana.length) {
        2, 3 -> 76.sp
        4, 5 -> 64.sp
        6 -> 56.sp
        else -> 48.sp
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFFBF7EF))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(24.dp))
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                item.kana,
                fontFamily = KanaFontFamily,
                fontSize = kanaSize,
                lineHeight = (kanaSize.value + 10).sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
            )
            if (showMeaning) {
                Spacer(Modifier.height(24.dp))
                Text(
                    item.romaji,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    item.meaning,
                    fontSize = 17.sp,
                    lineHeight = 23.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun LessonButton(
    text: String,
    filled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (filled) MaterialTheme.colorScheme.primary else Color.White)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text,
            color = if (filled) Color.White else MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
        )
    }
}
