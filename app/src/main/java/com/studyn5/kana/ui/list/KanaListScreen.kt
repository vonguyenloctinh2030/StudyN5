package com.studyn5.kana.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyn5.kana.data.Kana
import com.studyn5.kana.data.KanaData
import com.studyn5.kana.data.KanaType
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaSmallShape

@Composable
fun KanaListScreen(
    type: KanaType,
    onBack: () -> Unit,
    onSelect: (Kana) -> Unit,
) {
    val list = KanaData.grid(type)

    KanaBackground(Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
            }
            Column {
                Text(
                    text = if (type == KanaType.HIRAGANA) "Hiragana" else "Katakana",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                )
                Text(
                    "Nhấn chữ để nghe & tập viết",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            verticalArrangement = Arrangement.spacedBy(9.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
        ) {
            items(list) { kana ->
                if (kana == null) {
                    Spacer(Modifier.aspectRatio(1f))
                } else {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .shadow(1.dp, KanaSmallShape)
                            .clip(KanaSmallShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaSmallShape)
                            .clickable { onSelect(kana) }
                            .padding(4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(kana.char, fontSize = 26.sp, fontFamily = KanaFontFamily)
                            Text(kana.romaji.uppercase(), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
    }
}
