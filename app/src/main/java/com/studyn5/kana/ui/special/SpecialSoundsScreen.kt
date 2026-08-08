package com.studyn5.kana.ui.special

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.SpecialSoundData
import com.studyn5.kana.data.SpecialSoundEntry
import com.studyn5.kana.data.SpecialSoundLesson
import com.studyn5.kana.ui.theme.KanaFontFamily

@Composable
fun SpecialSoundsScreen(
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val lessons = SpecialSoundData.lessons
    val lesson = lessons[selectedIndex]
    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex) {
        listState.scrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 10.dp, end = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
            }
            Column {
                Text("Âm đặc biệt", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                Text(
                    "Nhìn quy tắc · nghe âm · nhớ qua ví dụ",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            lessons.forEachIndexed { index, item ->
                val selected = index == selectedIndex
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (selected) MaterialTheme.colorScheme.primary else Color.White)
                        .border(
                            width = 1.dp,
                            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(50),
                        )
                        .clickable { selectedIndex = index }
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

        LessonContent(
            lesson = lesson,
            onSpeak = onSpeak,
            listState = listState,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun LessonContent(
    lesson: SpecialSoundLesson,
    onSpeak: (String) -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState,
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
                    letterSpacing = 0.7.sp,
                )
            }
            items(group.entries) { entry ->
                SoundRow(entry = entry, onSpeak = onSpeak)
            }
        }

        item { TipCard(lesson.tip) }
    }
}

@Composable
private fun RuleCard(lesson: SpecialSoundLesson) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF4EBDD))
            .padding(16.dp),
    ) {
        Text(lesson.title, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        Text(
            lesson.explanation,
            modifier = Modifier.padding(top = 3.dp),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp),
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
        modifier = modifier
            .clip(RoundedCornerShape(13.dp))
            .background(Color(0xFFFFFBF5))
            .padding(horizontal = 8.dp, vertical = 13.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, fontFamily = KanaFontFamily, fontSize = 17.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun SoundRow(entry: SpecialSoundEntry, onSpeak: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE9DED1), RoundedCornerShape(15.dp))
            .clickable { onSpeak(entry.audioKey) }
            .padding(start = 14.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            entry.kana,
            modifier = Modifier.weight(0.9f),
            fontFamily = KanaFontFamily,
            fontSize = if (entry.kana.length > 5) 20.sp else 23.sp,
            fontWeight = FontWeight.Medium,
        )
        Column(modifier = Modifier.weight(1.15f)) {
            Text(
                entry.romaji,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(entry.formation, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFFF9DFDD)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Filled.VolumeUp,
                contentDescription = "Phát âm ${entry.kana}",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(21.dp),
            )
        }
    }
}

@Composable
private fun TipCard(tip: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color(0xFFFFF3DF))
            .padding(13.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            Icons.Filled.Lightbulb,
            contentDescription = null,
            tint = Color(0xFFC8843F),
            modifier = Modifier.size(22.dp),
        )
        Column {
            Text("Mẹo dễ nhớ", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF765638))
            Spacer(Modifier.height(2.dp))
            Text(tip, fontSize = 12.sp, lineHeight = 17.sp, color = Color(0xFF765638))
        }
    }
}
