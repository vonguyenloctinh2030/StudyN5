package com.studyn5.kana.ui.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color as CColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.KanaType

@Composable
fun PracticeScreen(
    viewModel: PracticeViewModel,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
) {
    if (viewModel.mode.value == "select") {
        SelectScreen(viewModel = viewModel, onBack = onBack)
    } else {
        PlayScreen(viewModel = viewModel, onSpeak = onSpeak)
    }
}

@Composable
private fun SelectScreen(viewModel: PracticeViewModel, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column {
                Text("Chọn chữ luyện tập", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold))
                Text("Nhấn chữ để chọn, rồi bấm Bắt đầu", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(Modifier.height(12.dp))

        val list = viewModel.hiragana + viewModel.katakana
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            verticalArrangement = Arrangement.spacedBy(9.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            items(list) { kana ->
                val isSel = viewModel.selected.contains(kana)
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSel) MaterialTheme.colorScheme.primary else CColor.White)
                        .border(1.dp, if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, RoundedCornerShape(14.dp))
                        .clickable { viewModel.toggle(kana) }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(kana.char, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = if (isSel) CColor.White else MaterialTheme.colorScheme.onSurface)
                        Text(kana.romaji, fontSize = 10.sp, color = if (isSel) CColor.White else MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(if (viewModel.selected.isNotEmpty()) MaterialTheme.colorScheme.primary else CColor(0xFFE2E8F0))
                .clickable(enabled = viewModel.selected.isNotEmpty()) { viewModel.start() }
                .padding(15.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "▶ Bắt đầu (${viewModel.selected.size} chữ)",
                color = if (viewModel.selected.isNotEmpty()) CColor.White else CColor.Gray,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
            )
        }
    }
}

@Composable
private fun PlayScreen(viewModel: PracticeViewModel, onSpeak: (String) -> Unit) {
    val kana = viewModel.current.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewModel.backToSelect() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column {
                Text("Luyện tập", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold))
                Text("Random chữ đã chọn · nhấn loa nếu không biết", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1.1f)
                .clip(RoundedCornerShape(22.dp))
                .background(CColor(0xFFFBF7EF))
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(kana?.char ?: "?", fontSize = 170.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Loa: phát âm chữ hiện tại
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { kana?.let { onSpeak(it.char) } }
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("🔊", fontSize = 26.sp)
            }
            // Kế tiếp: random chữ kế
            Box(
                modifier = Modifier
                    .weight(2f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(CColor.White)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                    .clickable { viewModel.next() }
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("Tiếp theo →", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }
        }
    }
}
