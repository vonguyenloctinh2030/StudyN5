package com.studyn5.kana.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.R
import com.studyn5.kana.data.Kana

private val HandFont = FontFamily(Font(R.font.kleeone))

@Composable
fun KanaDetailScreen(
    kanas: List<Kana>,
    index: Int,
    onSpeak: (Kana) -> Unit,
    onBack: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit,
) {
    val kana = kanas.getOrElse(index) { kanas.first() }
    var hidden by remember(index) { mutableStateOf(false) }

    // Mỗi nét = 1 mutableStateListOf<Offset> (trigger recompose khi add point)
    val strokes = remember(index) { mutableStateListOf<MutableList<Offset>>() }
    // Nét đang vẽ: dùng mutableStateListOf để mỗi point thêm vào đều recompose
    var current by remember { mutableStateOf<MutableList<Offset>?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column(modifier = Modifier.padding(start = 4.dp)) {
                Text(
                    "${kana.char} — ${kana.romaji}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold),
                )
                Text(
                    "${kana.type.label} · Hàng ${kana.group} · ${index + 1}/${kanas.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { hidden = !hidden }) {
                Text(if (hidden) "👁️" else "🙈", fontSize = 22.sp)
            }
        }

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(22.dp))
                .background(Color(0xFFFBF7EF))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(22.dp)),
        ) {
            // Grid
            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                val step = size.width / 4
                for (i in 1 until 4) {
                    drawLine(Color(0x1AD64545), Offset(step * i, 0f), Offset(step * i, size.height), strokeWidth = 1.dp.toPx())
                    drawLine(Color(0x1AD64545), Offset(0f, step * i), Offset(size.width, step * i), strokeWidth = 1.dp.toPx())
                }
            }

            // Chữ nền font KleeOne (nét bút), mờ
            if (!hidden) {
                val ctx = LocalContext.current
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    val paint = android.graphics.Paint().apply {
                        typeface = try {
                            android.graphics.Typeface.createFromAsset(ctx.assets, "fonts/kleeone.ttf")
                        } catch (e: Exception) {
                            android.graphics.Typeface.DEFAULT
                        }
                        textSize = size.width * 0.7f
                        color = Color(0x33999999).toArgb()
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    val fm = paint.fontMetrics
                    val y = size.height / 2f - (fm.ascent + fm.descent) / 2f
                    drawContext.canvas.nativeCanvas.drawText(kana.char, size.width / 2f, y, paint)
                }
            }

            // Vẽ tay realtime
            val allStrokes = strokes + (current?.let { listOf(it) } ?: emptyList())
            androidx.compose.foundation.Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val list = mutableStateListOf(offset)
                                current = list
                                strokes.add(list)
                            },
                            onDrag = { change, _ ->
                                current?.add(change.position)
                            },
                            onDragEnd = { current = null },
                        )
                    },
            ) {
                allStrokes.forEach { stroke ->
                    for (i in 1 until stroke.size) {
                        drawLine(Color(0xFF2563EB), stroke[i - 1], stroke[i], strokeWidth = 5.dp.toPx())
                    }
                }
            }
        }

        Spacer(Modifier.height(13.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onSpeak(kana) }
                    .padding(12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("🔊 Phát âm", color = Color.White, fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold, fontSize = 13.sp)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(Color.White)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(13.dp))
                    .clickable { hidden = !hidden }
                    .padding(12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("👁️ Ẩn chữ", color = MaterialTheme.colorScheme.secondary, fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold, fontSize = 13.sp)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(Color.White)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(13.dp))
                    .clickable { strokes.clear() }
                    .padding(12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("🧹 Xóa", color = MaterialTheme.colorScheme.onSurface, fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(13.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(Color.White)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(13.dp))
                    .clickable { onPrev() }
                    .padding(13.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("← Lùi", color = MaterialTheme.colorScheme.onSurface, fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold, fontSize = 14.sp)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onNext() }
                    .padding(13.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("Tiếp →", color = Color.White, fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold, fontSize = 14.sp)
            }
        }
    }
}
