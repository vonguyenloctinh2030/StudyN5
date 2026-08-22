package com.studyn5.kana.ui.vocabulary

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.LanguageLearningData
import com.studyn5.kana.data.VocabularyEntry
import com.studyn5.kana.data.VocabularyLibraryItem
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaCardShape
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaJade
import com.studyn5.kana.ui.theme.KanaRed
import com.studyn5.kana.ui.theme.KanaSmallShape
import java.text.Normalizer
import java.util.Locale

@Composable
fun VocabularyLibraryScreen(onBack: () -> Unit, onSpeak: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf("Tất cả") }
    val queryKey = query.searchKey()
    val visibleItems = LanguageLearningData.vocabularyLibrary.filter { item ->
        val matchesGroup = selectedGroup == "Tất cả" || item.group == selectedGroup
        val entry = item.entry
        val searchable = listOf(
            entry.japanese,
            entry.referenceJapanese.orEmpty(),
            entry.romaji,
            entry.meaning,
            entry.exampleJapanese,
            entry.exampleRomaji,
            entry.exampleMeaning,
        ).joinToString(" ").searchKey()
        matchesGroup && (queryKey.isBlank() || queryKey in searchable)
    }

    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Row(
                Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Quay lại", tint = MaterialTheme.colorScheme.primary)
                }
                Column {
                    Text("Từ vựng", fontSize = 22.sp, fontWeight = FontWeight.Black)
                    Text("Tìm nhanh · học theo nhóm · nghe ví dụ", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = KanaSmallShape,
                leadingIcon = { Text("⌕", fontSize = 25.sp, color = MaterialTheme.colorScheme.primary) },
                trailingIcon = if (query.isNotEmpty()) {
                    { Text("×", Modifier.clip(CircleShape).clickable { query = "" }.padding(8.dp), fontSize = 20.sp) }
                } else null,
                placeholder = { Text("Tìm chữ Nhật, romaji hoặc nghĩa…", fontSize = 12.sp) },
            )
            Spacer(Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                items(LanguageLearningData.vocabularyGroups) { group ->
                    val selected = group == selectedGroup
                    val count = if (group == "Tất cả") {
                        LanguageLearningData.vocabularyLibrary.size
                    } else {
                        LanguageLearningData.vocabularyLibrary.count { it.group == group }
                    }
                    Text(
                        "$group · $count",
                        Modifier.clip(CircleShape)
                            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                            .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, CircleShape)
                            .clickable { selectedGroup = group }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                if (visibleItems.isEmpty()) "Không tìm thấy từ phù hợp" else "${visibleItems.size} từ phù hợp",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(
                    items = visibleItems,
                    key = { "${it.group}|${it.entry.japanese}|${it.entry.meaning}" },
                ) { item -> LibraryVocabularyCard(item, onSpeak) }
                item { Spacer(Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
private fun LibraryVocabularyCard(item: VocabularyLibraryItem, onSpeak: (String) -> Unit) {
    val entry = item.entry
    Column(
        Modifier.fillMaxWidth().shadow(1.dp, KanaCardShape).clip(KanaCardShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape)
            .padding(14.dp),
    ) {
        Text(
            item.group.uppercase(),
            color = KanaJade,
            fontSize = 8.sp,
            letterSpacing = .6.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Spacer(Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1.25f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        entry.japanese,
                        fontFamily = KanaFontFamily,
                        fontSize = vocabularyFontSize(entry.japanese),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    entry.referenceJapanese?.let { reference ->
                        Text(
                            reference,
                            Modifier.padding(start = 8.dp).clip(CircleShape).background(Color(0xFFFFE7C2))
                                .padding(horizontal = 7.dp, vertical = 3.dp),
                            fontFamily = KanaFontFamily,
                            color = Color(0xFF9A5A13),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Text(entry.romaji, color = KanaRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
            Text(entry.meaning, Modifier.weight(.75f), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            SpeakerButton { onSpeak(entry.japanese) }
        }

        Spacer(Modifier.height(9.dp))
        Column(
            Modifier.fillMaxWidth().clip(KanaSmallShape)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .55f)).padding(11.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("VÍ DỤ", color = KanaJade, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.weight(1f))
                SpeakerButton(34) { onSpeak(entry.exampleJapanese) }
            }
            Text(entry.exampleJapanese, fontFamily = KanaFontFamily, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(entry.exampleRomaji, fontSize = 9.sp, color = KanaRed)
            Text(entry.exampleMeaning, fontSize = 10.sp)
        }
    }
}

private fun vocabularyFontSize(text: String) = when {
    text.length > 9 -> 15.sp
    text.length > 6 -> 17.sp
    text.length > 4 -> 20.sp
    else -> 25.sp
}

private fun String.searchKey(): String = Normalizer.normalize(lowercase(Locale.ROOT), Normalizer.Form.NFD)
    .replace("\\p{Mn}+".toRegex(), "")
    .replace('đ', 'd')

@Composable
private fun SpeakerButton(size: Int = 42, onClick: () -> Unit) {
    Box(
        Modifier.size(size.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
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
