package com.studyn5.kana.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.Kana

@Composable
fun KanaDetailScreen(
    kana: Kana,
    onSpeak: () -> Unit,
    onBack: () -> Unit,
) {
    var hidden by remember { mutableStateOf(false) }
    val paths = remember { mutableListOf<Path>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp),
    ) {
        // Top bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column(modifier = Modifier.padding(start = 4.dp)) {
                Text(
                    "${kana.char} — ${kana.romaji}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                )
                Text(
                    "${kana.type.label} · Hàng ${kana.group}",
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

        // Stage
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(22.dp))
                .background(Color(0xFFFBF7EF))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(22.dp)),
        ) {
            // Trace grid
            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                val step = size.width / 4
                for (i in 1 until 4) {
                    drawLine(
                        color = Color(0x1AD64545),
                        start = Offset(step * i, 0f),
                        end = Offset(step * i, size.height),
                        strokeWidth = 1.dp.toPx(),
                    )
                    drawLine(
                        color = Color(0x1AD64545),
                        start = Offset(0f, step * i),
                        end = Offset(size.width, step * i),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
            }

            // Handwriting paths
            androidx.compose.foundation.Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPath = Path().apply { moveTo(offset.x, offset.y) }
                                currentPath?.let { paths.add(it) }
                            },
                            onDrag = { change, _ ->
                                val pos = change.position
                                val path = currentPath
                                if (path != null) {
                                    path.lineTo(pos.x, pos.y)
                                }
                            },
                            onDragEnd = { currentPath = null },
                        )
                    },
            ) {
                paths.forEach { drawPath(it, Color(0xFF2563EB), style = Stroke(4.dp.toPx())) }
                currentPath?.let { drawPath(it, Color(0xFF2563EB), style = Stroke(4.dp.toPx())) }
            }

            // Big kana (hidden when toggled)
            if (!hidden) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(kana.char, fontSize = 170.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }

        Spacer(Modifier.height(13.dp))

        // Tools
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onSpeak() }
                    .padding(12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("🔊 Phát âm", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
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
                Text("👁️ Ẩn chữ", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .background(Color.White)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(13.dp))
                    .clickable { paths.clear() }
                    .padding(12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("🧹 Xóa", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
            }
        }
    }
}
