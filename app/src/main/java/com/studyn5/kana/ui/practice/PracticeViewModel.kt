package com.studyn5.kana.ui.practice

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.studyn5.kana.data.Kana
import com.studyn5.kana.data.KanaData

class PracticeViewModel : ViewModel() {

    val hiragana = KanaData.hiragana
    val katakana = KanaData.katakana

    // Danh sách chữ user đã chọn để luyện
    val selected = mutableStateListOf<Kana>()

    private val _mode = mutableStateOf("select") // "select" | "play"
    val mode = _mode

    // Kết quả random hiện tại
    private val _current = mutableStateOf<Kana?>(null)
    val current = _current

    fun toggle(kana: Kana) {
        if (selected.contains(kana)) selected.remove(kana) else selected.add(kana)
    }

    fun start() {
        if (selected.isNotEmpty()) {
            _mode.value = "play"
            next()
        }
    }

    fun next() {
        if (selected.isEmpty()) return
        _current.value = selected.random()
    }

    fun backToSelect() {
        _mode.value = "select"
        _current.value = null
    }
}
