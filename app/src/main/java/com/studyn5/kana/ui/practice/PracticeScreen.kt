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
import com.studyn5.kana.data.PracticeCategory
import com.studyn5.kana.data.PracticeItem
import com.studyn5.kana.data.PracticeScript
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaCardShape
import com.studyn5.kana.ui.theme.KanaRed
import com.studyn5.kana.ui.theme.KanaSmallShape

@Composable
fun PracticeScreen(
    viewModel: PracticeViewModel,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
) {
    if (viewModel.mode.value == "select") {
        SelectScreen(viewModel, onBack)
    } else {
        PlayScreen(viewModel, onSpeak)
    }
}

@Composable
private fun SelectScreen(viewModel: PracticeViewModel, onBack: () -> Unit) {
    val list = viewModel.available
    val isBasic = viewModel.category.value == PracticeCategory.BASIC

    KanaBackground(Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại", tint = MaterialTheme.colorScheme.primary)
            }
            Column {
                Text("Luyện tập", fontWeight = FontWeight.Black, fontSize = 22.sp)
                Text(
                    if (isBasic) "Chọn Kana cần học · tự thêm âm đục và bán đục"
                    else "Danh sách được chọn sẵn · mỗi mục chỉ xuất hiện một lần/vòng",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(Modifier.height(10.dp))
        FilterRow(
            values = PracticeScript.entries,
            selected = viewModel.script.value,
            label = PracticeScript::label,
            onSelect = viewModel::setScript,
        )
        Spacer(Modifier.height(8.dp))
        FilterRow(
            values = PracticeCategory.entries,
            selected = viewModel.category.value,
            label = PracticeCategory::label,
            onSelect = viewModel::setCategory,
        )
        Spacer(Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isBasic) 5 else 2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f),
        ) {
            items(list, key = PracticeItem::id) { item ->
                PracticeChoice(
                    item = item,
                    selected = item.id in viewModel.selectedIds,
                    compact = isBasic,
                    onClick = { viewModel.toggle(item) },
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ActionButton(
                text = if (list.isNotEmpty() && list.all { it.id in viewModel.selectedIds }) "Bỏ chọn" else "✓ Chọn tất cả",
                enabled = list.isNotEmpty(),
                filled = false,
                modifier = Modifier.weight(1f),
                onClick = viewModel::selectAllVisible,
            )
            ActionButton(
                text = "▶ Bắt đầu (${viewModel.practiceCount})",
                enabled = viewModel.selectedCount > 0,
                filled = true,
                modifier = Modifier.weight(1.45f),
                onClick = viewModel::start,
            )
        }
    }
    }
}

@Composable
private fun <T> FilterRow(
    values: List<T>,
    selected: T,
    label: (T) -> String,
    onSelect: (T) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        values.forEach { value ->
            val active = value == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(KanaSmallShape)
                    .background(if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                    .border(1.dp, if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, KanaSmallShape)
                    .clickable { onSelect(value) }
                    .padding(horizontal = 3.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    label(value),
                    color = if (active) Color.White else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun PracticeChoice(item: PracticeItem, selected: Boolean, compact: Boolean, onClick: () -> Unit) {
    val shape = KanaSmallShape
    Box(
        modifier = Modifier
            .then(if (compact) Modifier.aspectRatio(1f) else Modifier.height(78.dp))
            .clip(shape)
            .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
            .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, shape)
            .clickable(onClick = onClick)
            .padding(5.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                item.text,
                fontSize = if (compact) 24.sp else 21.sp,
                fontFamily = KanaFontFamily,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
            )
            Text(
                item.romaji,
                fontSize = if (compact) 9.sp else 11.sp,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    enabled: Boolean,
    filled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val background = when {
        !enabled -> Color(0xFFE2E8F0)
        filled -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surface
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(background)
            .border(1.dp, if (enabled) MaterialTheme.colorScheme.primary else Color(0xFFE2E8F0), RoundedCornerShape(15.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text,
            color = if (!enabled) Color.Gray else if (filled) Color.White else MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PlayScreen(viewModel: PracticeViewModel, onSpeak: (String) -> Unit) {
    val item = viewModel.current.value ?: return
    var showMeaning by remember(item.id) { mutableStateOf(false) }
    val isWord = item.meaning != null

    KanaBackground(Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize().padding(18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = viewModel::backToSelect) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại", tint = MaterialTheme.colorScheme.primary)
            }
            Column {
                Text("Luyện ${viewModel.category.value.label}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Text("Không lặp lại cho đến khi hết vòng", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(Modifier.height(18.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1.02f)
                .shadow(3.dp, KanaCardShape)
                .clip(KanaCardShape)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape)
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    item.text,
                    fontSize = if (isWord) 55.sp else 170.sp,
                    lineHeight = if (isWord) 68.sp else 180.sp,
                    fontFamily = KanaFontFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            }
        }

        if (showMeaning) {
            Spacer(Modifier.height(10.dp))
            Column(
                Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.tertiaryContainer).padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(item.romaji, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                Text(item.meaning.orEmpty(), fontSize = 16.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onTertiaryContainer)
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ActionButton("🔊 Nghe", true, true, Modifier.weight(1f)) { onSpeak(item.audioKey) }
            if (isWord) {
                ActionButton(
                    if (showMeaning) "Ẩn nghĩa" else "Hiện nghĩa",
                    true,
                    false,
                    Modifier.weight(1f),
                ) { showMeaning = !showMeaning }
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier.fillMaxWidth().clip(KanaSmallShape).background(KanaRed).clickable(onClick = viewModel::next).padding(15.dp),
            contentAlignment = Alignment.Center,
        ) { Text("Tiếp theo  →", color = Color.White, fontWeight = FontWeight.ExtraBold) }
    }
    }
}
