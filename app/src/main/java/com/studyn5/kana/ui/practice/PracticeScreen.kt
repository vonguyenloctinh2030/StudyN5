package com.studyn5.kana.ui.practice

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PracticeScreen(
    viewModel: PracticeViewModel,
    onBack: () -> Unit,
) {
    val state = viewModel.state.value

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
            Column {
                Text("Luyện tập", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold))
                Text(
                    "Đoán chữ Kana ngẫu nhiên",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Câu ${state.total + 1} / 10", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Random · Hira+Kata", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(14.dp))

        // Stage
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1.05f)
                .clip(RoundedCornerShape(22.dp))
                .background(Color(0xFFFBF7EF)),
            contentAlignment = Alignment.Center,
        ) {
            Text(state.current.char, fontSize = 150.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(Modifier.height(15.dp))

        // Options
        val options = state.options
        Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            for (row in options.chunked(2)) {
                Row(horizontalArrangement = Arrangement.spacedBy(11.dp)) {
                    row.forEach { opt ->
                        val isCorrect = opt == state.current.romaji
                        val isSelected = state.selected == opt
                        val bg = when {
                            state.selected == null -> Color.White
                            isCorrect -> Color(0xFFDCFCE7)
                            isSelected -> Color(0xFFFEE2E2)
                            else -> Color.White
                        }
                        val border = when {
                            state.selected == null -> MaterialTheme.colorScheme.outline
                            isCorrect -> Color(0xFF86EFAC)
                            isSelected -> Color(0xFFFCA5A5)
                            else -> MaterialTheme.colorScheme.outline
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(bg)
                                .border(1.dp, border, RoundedCornerShape(15.dp))
                                .clickable(enabled = state.selected == null) {
                                    viewModel.answer(opt)
                                }
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(opt, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Điểm: ${state.score}/${state.total}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
            if (state.selected != null) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(13.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { viewModel.next() }
                        .padding(11.dp, 14.dp),
                ) {
                    Text("Tiếp →", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                }
            }
        }
    }
}
