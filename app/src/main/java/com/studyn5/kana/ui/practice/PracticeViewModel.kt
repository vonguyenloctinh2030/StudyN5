package com.studyn5.kana.ui.practice

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.studyn5.kana.data.Kana
import com.studyn5.kana.data.KanaData
import com.studyn5.kana.data.KanaType
import kotlin.random.Random

data class QuizState(
    val current: Kana,
    val options: List<String>,
    val selected: String? = null,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val total: Int = 0,
    val useKatakana: Boolean = true,
)

class PracticeViewModel : ViewModel() {

    private val all: List<Kana> = KanaData.hiragana + KanaData.katakana

    private val _state = mutableStateOf(generate())
    val state = _state

    private fun generate(): QuizState {
        val current = all.random()
        // Tạo 4 lựa chọn romaji, đảm bảo có đáp án đúng
        val distractors = all.filter { it.romaji != current.romaji }
            .shuffled()
            .take(3)
            .map { it.romaji }
        val options = (listOf(current.romaji) + distractors).shuffled()
        return QuizState(current = current, options = options)
    }

    fun answer(romaji: String) {
        val s = _state.value
        val correct = romaji == s.current.romaji
        _state.value = s.copy(
            selected = romaji,
            isCorrect = correct,
            score = s.score + if (correct) 1 else 0,
            total = s.total + 1,
        )
    }

    fun next() {
        _state.value = generate()
    }

    fun reset() {
        _state.value = generate().copy(score = 0, total = 0)
    }
}
