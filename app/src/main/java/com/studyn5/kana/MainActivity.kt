package com.studyn5.kana

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.studyn5.kana.data.Kana
import com.studyn5.kana.data.KanaType
import com.studyn5.kana.tts.JapaneseTtsManager
import com.studyn5.kana.ui.detail.KanaDetailScreen
import com.studyn5.kana.ui.home.HomeScreen
import com.studyn5.kana.ui.list.KanaListScreen
import com.studyn5.kana.ui.practice.PracticeScreen
import com.studyn5.kana.ui.practice.PracticeViewModel
import com.studyn5.kana.ui.theme.KanaMasterTheme

class MainActivity : ComponentActivity() {

    private lateinit var tts: JapaneseTtsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = JapaneseTtsManager(this)

        setContent {
            KanaMasterTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation(
                        onSpeak = { text -> tts.speak(text) },
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
}

@Composable
private fun AppNavigation(onSpeak: (String) -> Unit) {
    var route by remember { mutableStateOf<Screen>(Screen.Home) }
    var listType by remember { mutableStateOf(KanaType.HIRAGANA) }
    var selectedKana by remember { mutableStateOf<Kana?>(null) }

    when (val r = route) {
        is Screen.Home -> HomeScreen(
            onOpenList = { type ->
                listType = type
                route = Screen.List
            },
            onOpenPractice = { route = Screen.Practice },
        )

        is Screen.List -> KanaListScreen(
            type = listType,
            onBack = { route = Screen.Home },
            onSelect = { kana ->
                selectedKana = kana
                route = Screen.Detail
            },
        )

        is Screen.Detail -> selectedKana?.let { kana ->
            KanaDetailScreen(
                kana = kana,
                onSpeak = { onSpeak(kana.char) },
                onBack = { route = Screen.List },
            )
        }

        is Screen.Practice -> PracticeScreen(
            viewModel = remember { PracticeViewModel() },
            onBack = { route = Screen.Home },
            onSpeak = { text -> onSpeak(text) },
        )
    }
}

private sealed class Screen {
    data object Home : Screen()
    data object List : Screen()
    data object Detail : Screen()
    data object Practice : Screen()
}
