package com.studyn5.kana.ui.kanji

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.KanjiData
import com.studyn5.kana.data.KanjiEntry
import com.studyn5.kana.ui.detail.KanaStrokeGuide
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaCardShape
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaNavy
import com.studyn5.kana.ui.theme.KanaRed
import com.studyn5.kana.ui.theme.KanaSmallShape

@Composable
fun KanjiListScreen(
    onBack: () -> Unit,
    onSelect: (KanjiEntry) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    val visible = remember(selectedCategory) {
        when (selectedCategory) {
            "Tất cả" -> KanjiData.entries
            "Đã học" -> KanjiData.entries.filter(KanjiEntry::learned)
            else -> KanjiData.entries.filter { it.category == selectedCategory }
        }
    }

    KanaBackground(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại", tint = MaterialTheme.colorScheme.primary)
                }
                Column {
                    Text("Kanji N5", fontSize = 24.sp, fontWeight = FontWeight.Black)
                    Text(
                        "${KanjiData.entries.size} chữ · 6 chữ đã học · nhấn để xem nét viết",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            Row(
                Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                KanjiData.categories.forEach { category ->
                    val selected = selectedCategory == category
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                            .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, CircleShape)
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 14.dp, vertical = 9.dp),
                    ) {
                        Text(
                            category,
                            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(visible, key = KanjiEntry::character) { kanji ->
                    KanjiCard(kanji) { onSelect(kanji) }
                }
            }
        }
    }
}

@Composable
private fun KanjiCard(kanji: KanjiEntry, onClick: () -> Unit) {
    Column(
        Modifier
            .height(132.dp)
            .shadow(1.dp, KanaSmallShape)
            .clip(KanaSmallShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, if (kanji.learned) KanaRed.copy(alpha = .5f) else MaterialTheme.colorScheme.outlineVariant, KanaSmallShape)
            .clickable(onClick = onClick)
            .padding(9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${kanji.strokeCount} nét", fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (kanji.learned) Text("ĐÃ HỌC", color = KanaRed, fontSize = 8.sp, fontWeight = FontWeight.Black)
        }
        Text(kanji.character, fontFamily = KanaFontFamily, fontSize = 42.sp, lineHeight = 46.sp, color = KanaNavy)
        Text(kanji.meaning, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, maxLines = 1)
        Text("ON ${kanji.onyomi}", fontSize = 8.sp, color = MaterialTheme.colorScheme.primary, maxLines = 1)
        Text("KUN ${kanji.kunyomi}", fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
    }
}

@Composable
fun KanjiDetailScreen(
    kanjis: List<KanjiEntry>,
    index: Int,
    onBack: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onSpeak: (String) -> Unit,
) {
    val kanji = kanjis.getOrElse(index) { kanjis.first() }
    var hidden by remember { mutableStateOf(false) }
    val strokes = remember(index) { mutableStateListOf<MutableList<Offset>>() }
    var current by remember(index) { mutableStateOf<MutableList<Offset>?>(null) }

    KanaBackground(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 18.dp, vertical = 12.dp),
        ) {
            Box(Modifier.fillMaxWidth()) {
                IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                }
                Text("HỌC KANJI", modifier = Modifier.align(Alignment.Center), color = KanaRed, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                Text("${index + 1}/${kanjis.size}", modifier = Modifier.align(Alignment.CenterEnd), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Row(
                Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.size(92.dp).clip(KanaSmallShape).background(MaterialTheme.colorScheme.primary.copy(alpha = .08f)), contentAlignment = Alignment.Center) {
                    Text(kanji.character, fontFamily = KanaFontFamily, fontSize = 62.sp, color = KanaNavy)
                }
                Column(Modifier.padding(start = 16.dp).weight(1f)) {
                    Text(kanji.meaning, fontSize = 23.sp, fontWeight = FontWeight.Black)
                    Text("${kanji.strokeCount} nét · ${if (kanji.learned) "Đã học" else kanji.category}", fontSize = 11.sp, color = KanaRed, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(7.dp))
                    Text("ON   ${kanji.onyomi}", fontSize = 13.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("KUN  ${kanji.kunyomi}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                ReadingButton("Âm On", kanji.onyomi, kanji.onAudio != null, Modifier.weight(1f)) { kanji.onAudio?.let(onSpeak) }
                ReadingButton("Âm Kun", kanji.kunyomi, kanji.kunAudio != null, Modifier.weight(1f)) { kanji.kunAudio?.let(onSpeak) }
            }

            Spacer(Modifier.height(12.dp))
            Text("TẬP VIẾT THEO THỨ TỰ NÉT", fontSize = 11.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(7.dp))
            Box(
                Modifier.fillMaxWidth().aspectRatio(1f).clip(KanaCardShape).background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outline, KanaCardShape),
            ) {
                androidx.compose.foundation.Canvas(Modifier.fillMaxSize()) {
                    val step = size.width / 4
                    for (i in 1 until 4) {
                        drawLine(KanaRed.copy(alpha = .12f), Offset(step * i, 0f), Offset(step * i, size.height), strokeWidth = 1.dp.toPx())
                        drawLine(KanaRed.copy(alpha = .12f), Offset(0f, step * i), Offset(size.width, step * i), strokeWidth = 1.dp.toPx())
                    }
                }
                if (!hidden) KanaStrokeGuide(kanji.character, Modifier.fillMaxSize())
                androidx.compose.foundation.Canvas(
                    Modifier.fillMaxSize().pointerInput(index) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val line = mutableStateListOf(offset)
                                current = line
                                strokes.add(line)
                            },
                            onDrag = { change, _ -> current?.add(change.position) },
                            onDragEnd = { current = null },
                            onDragCancel = { current = null },
                        )
                    },
                ) {
                    strokes.forEach { stroke ->
                        if (stroke.size == 1) {
                            drawCircle(KanaNavy, 5.dp.toPx(), stroke.first())
                        } else {
                            val path = Path().apply {
                                moveTo(stroke.first().x, stroke.first().y)
                                for (i in 1 until stroke.size) {
                                    val previous = stroke[i - 1]
                                    val point = stroke[i]
                                    quadraticBezierTo(previous.x, previous.y, (previous.x + point.x) / 2f, (previous.y + point.y) / 2f)
                                }
                                lineTo(stroke.last().x, stroke.last().y)
                            }
                            drawPath(path, KanaNavy.copy(alpha = .15f), style = Stroke(13.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
                            drawPath(path, KanaNavy, style = Stroke(9.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                DetailAction(if (hidden) "👁 Hiện chữ" else "🙈 Ẩn chữ", Modifier.weight(1f)) { hidden = !hidden }
                DetailAction("🧹 Xóa nét", Modifier.weight(1f)) { strokes.clear() }
            }

            Spacer(Modifier.height(14.dp))
            Text("MẸO GHI NHỚ", fontSize = 11.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            Text(
                kanji.memoryHint,
                Modifier.fillMaxWidth().padding(top = 6.dp).clip(KanaSmallShape).background(MaterialTheme.colorScheme.primary.copy(alpha = .07f)).padding(12.dp),
                fontSize = 13.sp,
                lineHeight = 19.sp,
            )

            Spacer(Modifier.height(14.dp))
            Text("VÍ DỤ", fontSize = 11.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
            kanji.examples.forEach { example ->
                Row(
                    Modifier.fillMaxWidth().padding(top = 8.dp).clip(KanaSmallShape).background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaSmallShape).clickable { onSpeak(example.japanese) }.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(example.japanese, fontFamily = KanaFontFamily, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(example.reading, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                        Text(example.meaning, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    SpeakerGlyph(KanaRed, Modifier.size(22.dp))
                }
            }

            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DetailAction("← Chữ trước", Modifier.weight(1f), onPrev)
                Box(
                    Modifier.weight(1f).clip(KanaSmallShape).background(KanaRed).clickable(onClick = onNext).padding(13.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Chữ tiếp →", color = Color.White, fontWeight = FontWeight.ExtraBold)
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ReadingButton(title: String, reading: String, enabled: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.clip(KanaSmallShape).background(if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
            .clickable(enabled = enabled, onClick = onClick).padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SpeakerGlyph(if (enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, Modifier.size(15.dp))
            Text(title, color = if (enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Text(reading, color = if (enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun DetailAction(text: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier.clip(KanaSmallShape).background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outline, KanaSmallShape)
            .clickable(onClick = onClick).padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
    }
}

@Composable
private fun SpeakerGlyph(color: Color, modifier: Modifier = Modifier) {
    androidx.compose.foundation.Canvas(modifier) {
        val speaker = Path().apply {
            moveTo(size.width * .08f, size.height * .38f)
            lineTo(size.width * .30f, size.height * .38f)
            lineTo(size.width * .56f, size.height * .16f)
            lineTo(size.width * .56f, size.height * .84f)
            lineTo(size.width * .30f, size.height * .62f)
            lineTo(size.width * .08f, size.height * .62f)
            close()
        }
        drawPath(speaker, color)
        drawArc(color, -48f, 96f, false, topLeft = Offset(size.width * .43f, size.height * .29f), size = androidx.compose.ui.geometry.Size(size.width * .34f, size.height * .42f), style = Stroke(width = size.width * .08f, cap = StrokeCap.Round))
        drawArc(color, -48f, 96f, false, topLeft = Offset(size.width * .38f, size.height * .15f), size = androidx.compose.ui.geometry.Size(size.width * .54f, size.height * .70f), style = Stroke(width = size.width * .07f, cap = StrokeCap.Round))
    }
}
