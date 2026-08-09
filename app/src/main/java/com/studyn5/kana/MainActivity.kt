package com.studyn5.kana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.studyn5.kana.audio.AudioPlayer
import com.studyn5.kana.data.Kana
import com.studyn5.kana.data.KanaData
import com.studyn5.kana.data.KanaType
import com.studyn5.kana.data.PracticeData
import com.studyn5.kana.ui.detail.KanaDetailScreen
import com.studyn5.kana.ui.home.HomeScreen
import com.studyn5.kana.ui.list.KanaListScreen
import com.studyn5.kana.ui.practice.PracticeScreen
import com.studyn5.kana.ui.practice.PracticeViewModel
import com.studyn5.kana.ui.pronunciation.PronunciationScreen
import com.studyn5.kana.ui.special.SpecialSoundsScreen
import com.studyn5.kana.ui.theme.KanaMasterTheme

class MainActivity : ComponentActivity() {

    private lateinit var audio: AudioPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audio = AudioPlayer(this)

        setContent {
            KanaMasterTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation(
                        onSpeak = { kana -> audio.play(kana) },
                        onSpeakKey = { audioKey -> audio.play(audioKey) },
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audio.release()
    }
}

@Composable
private fun AppNavigation(
    onSpeak: (Kana) -> Unit,
    onSpeakKey: (String) -> Unit,
) {
    var route by remember { mutableStateOf<Screen>(Screen.Home) }
    var listType by remember { mutableStateOf(KanaType.HIRAGANA) }
    var selectedList by remember { mutableStateOf<List<Kana>>(emptyList()) }
    var selectedIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val practiceViewModel = remember { PracticeViewModel(PracticeData(context)) }

    BackHandler(enabled = route !is Screen.Home) {
        route = when (route) {
            is Screen.Detail -> Screen.List
            is Screen.List, is Screen.Pronunciation, is Screen.SpecialSounds -> Screen.Home
            is Screen.Practice -> {
                if (practiceViewModel.mode.value == "play") {
                    practiceViewModel.backToSelect()
                    Screen.Practice
                } else {
                    Screen.Home
                }
            }
            is Screen.Home -> Screen.Home
        }
    }

    when (val r = route) {
        is Screen.Home -> HomeScreen(
            onOpenList = { type ->
                listType = type
                selectedList = if (type == KanaType.HIRAGANA) KanaData.hiragana else KanaData.katakana
                selectedIndex = 0
                route = Screen.List
            },
            onOpenPractice = { route = Screen.Practice },
            onOpenPronunciation = { route = Screen.Pronunciation },
            onOpenSpecialSounds = { route = Screen.SpecialSounds },
        )

        is Screen.List -> KanaListScreen(
            type = listType,
            onBack = { route = Screen.Home },
            onSelect = { kana ->
                selectedList = if (listType == KanaType.HIRAGANA) KanaData.hiragana else KanaData.katakana
                selectedIndex = selectedList.indexOf(kana).coerceAtLeast(0)
                route = Screen.Detail
            },
        )

        is Screen.Detail -> KanaDetailScreen(
            kanas = selectedList,
            index = selectedIndex,
            onSpeak = { kana -> onSpeak(kana) },
            onBack = { route = Screen.List },
            onPrev = { if (selectedIndex > 0) selectedIndex-- },
            onNext = { if (selectedIndex < selectedList.size - 1) selectedIndex++ },
        )

        is Screen.Practice -> PracticeScreen(
            viewModel = practiceViewModel,
            onBack = { route = Screen.Home },
            onSpeak = onSpeakKey,
        )

        is Screen.Pronunciation -> PronunciationScreen(
            onBack = { route = Screen.Home },
            onSpeak = { kana -> onSpeak(kana) },
        )

        is Screen.SpecialSounds -> SpecialSoundsScreen(
            onBack = { route = Screen.Home },
            onSpeak = onSpeakKey,
        )
    }
}

private sealed class Screen {
    data object Home : Screen()
    data object List : Screen()
    data object Detail : Screen()
    data object Practice : Screen()
    data object Pronunciation : Screen()
    data object SpecialSounds : Screen()
}
