package com.studyn5.kana.ui.home

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import com.studyn5.kana.data.KanaType
import com.studyn5.kana.ui.theme.KanaBackground
import com.studyn5.kana.ui.theme.KanaCardShape
import com.studyn5.kana.ui.theme.KanaFontFamily
import com.studyn5.kana.ui.theme.KanaJade
import com.studyn5.kana.ui.theme.KanaRed
import com.studyn5.kana.ui.theme.KanaSmallShape

private data class HomeItem(
    val symbol: String,
    val title: String,
    val description: String,
    val color: Color,
    val action: () -> Unit,
)

@Composable
fun HomeScreen(
    onOpenList: (KanaType) -> Unit,
    onOpenPractice: () -> Unit,
    onOpenLessons: () -> Unit,
    onOpenPronunciation: () -> Unit,
    onOpenSpecialSounds: () -> Unit,
) {
    val items = listOf(
        HomeItem("あ", "Hiragana", "46 chữ cái nền tảng", MaterialTheme.colorScheme.primary) { onOpenList(KanaType.HIRAGANA) },
        HomeItem("ア", "Katakana", "46 chữ phiên âm", KanaRed) { onOpenList(KanaType.KATAKANA) },
        HomeItem("♪", "Phát âm", "Nghe và ghi nhớ", KanaJade, onOpenPronunciation),
        HomeItem("が", "Âm đặc biệt", "Đục · ghép · dài · ngắt", Color(0xFFB87432), onOpenSpecialSounds),
    )

    KanaBackground(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
        ) {
            AppIdentity()
            Spacer(Modifier.height(24.dp))
            Text("Hôm nay học gì?", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text(
                "Mỗi ngày một chút, Kana sẽ trở nên thật tự nhiên.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(14.dp))
            LearningOverview()
            Spacer(Modifier.height(22.dp))

            Text("Khám phá", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(10.dp))
            items.chunked(2).forEach { rowItems ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    rowItems.forEach { item -> HomeCard(item, Modifier.weight(1f)) }
                }
                Spacer(Modifier.height(12.dp))
            }

            MainActionCard(
                symbol = "練",
                title = "Luyện tập",
                description = "Tự chọn nội dung và ôn theo vòng ngẫu nhiên",
                background = MaterialTheme.colorScheme.primary,
                onClick = onOpenPractice,
            )
            Spacer(Modifier.height(12.dp))
            MainActionCard(
                symbol = "九",
                title = "Lessons",
                description = "9 bài học · 900 từ Kana chọn lọc",
                background = KanaRed,
                onClick = onOpenLessons,
            )
            Spacer(Modifier.height(20.dp))
            AppInformation()
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun AppIdentity() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(48.dp).clip(CircleShape).background(KanaRed),
            contentAlignment = Alignment.Center,
        ) {
            Text("日", color = Color.White, fontSize = 23.sp, fontFamily = KanaFontFamily, fontWeight = FontWeight.Bold)
        }
        Column(Modifier.padding(start = 12.dp).weight(1f)) {
            Text("N5 KANA", fontSize = 21.sp, letterSpacing = 1.2.sp, fontWeight = FontWeight.Black)
            Text("Japanese foundations", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Box(
            Modifier.clip(KanaSmallShape).background(MaterialTheme.colorScheme.primary).padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Text("N5", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun LearningOverview() {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(KanaCardShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(18.dp),
    ) {
        Text("LỘ TRÌNH KANA CƠ BẢN", color = Color.White.copy(alpha = .7f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Đọc · nghe · viết · phản xạ", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(15.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OverviewChip("92", "chữ cái", Modifier.weight(1f))
            OverviewChip("9", "lessons", Modifier.weight(1f))
            OverviewChip("900", "từ luyện", Modifier.weight(1f))
        }
    }
}

@Composable
private fun OverviewChip(value: String, label: String, modifier: Modifier) {
    Column(
        modifier.clip(KanaSmallShape).background(Color.White.copy(alpha = .1f)).padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(value, color = Color.White, fontWeight = FontWeight.Black, fontSize = 17.sp)
        Text(label, color = Color.White.copy(alpha = .72f), fontSize = 10.sp)
    }
}

@Composable
private fun HomeCard(item: HomeItem, modifier: Modifier = Modifier) {
    Column(
        modifier
            .height(142.dp)
            .shadow(2.dp, KanaCardShape)
            .clip(KanaCardShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape)
            .clickable(onClick = item.action)
            .padding(15.dp),
    ) {
        Box(Modifier.size(43.dp).clip(CircleShape).background(item.color.copy(alpha = .11f)), contentAlignment = Alignment.Center) {
            Text(item.symbol, fontSize = 25.sp, fontFamily = KanaFontFamily, color = item.color, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(10.dp))
        Text(item.title, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
        Text(item.description, fontSize = 11.sp, lineHeight = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun MainActionCard(symbol: String, title: String, description: String, background: Color, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(KanaCardShape).background(background).clickable(onClick = onClick).padding(17.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(49.dp).clip(CircleShape).background(Color.White.copy(alpha = .13f)), contentAlignment = Alignment.Center) {
            Text(symbol, fontFamily = KanaFontFamily, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
        Column(Modifier.padding(horizontal = 14.dp).weight(1f)) {
            Text(title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            Text(description, color = Color.White.copy(alpha = .75f), fontSize = 11.sp)
        }
        Text("›", color = Color.White, fontSize = 29.sp)
    }
}

@Composable
private fun AppInformation() {
    Column(
        Modifier.fillMaxWidth().clip(KanaCardShape).background(MaterialTheme.colorScheme.surface).border(1.dp, MaterialTheme.colorScheme.outlineVariant, KanaCardShape).padding(17.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(8.dp).clip(CircleShape).background(KanaJade))
            Text("  Thông tin ứng dụng", fontWeight = FontWeight.ExtraBold)
        }
        Spacer(Modifier.height(9.dp))
        Text("Phiên bản 1.0.0 · Offline · Không quảng cáo", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("© 2026 Võ Nguyễn Lộc Tính · TinhVNL2", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Principal Software Engineer · IMS · FPT Software", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Hướng dẫn nét viết: KanjiVG · CC BY-SA 3.0", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(7.dp))
        Text(
            "Ứng dụng phục vụ học tập cá nhân, phi thương mại và không tạo doanh thu. Đây không phải sản phẩm chính thức của FPT Software.",
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
