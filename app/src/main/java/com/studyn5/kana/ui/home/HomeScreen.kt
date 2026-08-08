package com.studyn5.kana.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.studyn5.kana.data.KanaType
import com.studyn5.kana.ui.theme.KanaFontFamily

private data class HomeItem(
    val symbol: String,
    val title: String,
    val description: String,
    val action: () -> Unit,
)

@Composable
fun HomeScreen(
    onOpenList: (KanaType) -> Unit,
    onOpenPractice: () -> Unit,
    onOpenPronunciation: () -> Unit,
) {
    val items = listOf(
        HomeItem("あ", "Bảng Hiragana", "46 chữ cơ bản") { onOpenList(KanaType.HIRAGANA) },
        HomeItem("ア", "Bảng Katakana", "46 chữ phiên âm") { onOpenList(KanaType.KATAKANA) },
        HomeItem("🔊", "Phát âm", "Nghe Hiragana & Katakana", onOpenPronunciation),
        HomeItem("🎯", "Luyện tập", "Chọn chữ & học ngẫu nhiên", onOpenPractice),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "N5 Kana Cơ Bản",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Học đọc · nghe · viết Hiragana & Katakana",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Text("N5", color = Color.White, fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(Modifier.height(22.dp))

        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(13.dp),
            ) {
                rowItems.forEach { item ->
                    HomeCard(item = item, modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(13.dp))
        }

        AppInformation()
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun HomeCard(item: HomeItem, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(138.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .clickable { item.action() }
            .padding(16.dp),
    ) {
        Column {
            Text(item.symbol, fontSize = 34.sp, fontFamily = KanaFontFamily, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(item.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(item.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun AppInformation() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text("Thông tin ứng dụng", fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(8.dp))
        Text("Phiên bản 1.0.0 · Hoạt động offline · Không quảng cáo", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("© 2026 Võ Nguyễn Lộc Tính · Tài khoản: TinhVNL2", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Principal Software Engineer · IMS · FPT Software", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Hướng dẫn nét viết: KanjiVG · CC BY-SA 3.0", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(6.dp))
        Text(
            "Ứng dụng được phát triển cho mục đích học tập cá nhân, phi thương mại và không tạo doanh thu. Đây không phải sản phẩm chính thức của FPT Software.",
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
