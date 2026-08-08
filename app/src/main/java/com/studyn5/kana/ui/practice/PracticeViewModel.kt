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

    // Bộ chữ còn lại trong một vòng luyện tập. Mỗi chữ chỉ xuất hiện một lần/vòng.
    private val remaining = mutableListOf<Kana>()

    fun toggle(kana: Kana) {
        if (selected.contains(kana)) selected.remove(kana) else selected.add(kana)
    }

    fun start() {
        if (selected.isNotEmpty()) {
            _mode.value = "play"
            remaining.clear()
            _current.value = null
            next()
        }
    }

    fun next() {
        if (selected.isEmpty()) return
        if (remaining.isEmpty()) refill()
        _current.value = remaining.removeAt(0)
    }

    private fun refill() {
        remaining.addAll(selected.shuffled())

        // Tránh lặp đúng chữ cuối vòng trước ở đầu vòng mới nếu có từ 2 chữ trở lên.
        val previous = _current.value
        if (remaining.size > 1 && remaining.first() == previous) {
            val differentIndex = remaining.indexOfFirst { it != previous }
            if (differentIndex > 0) {
                val first = remaining[0]
                remaining[0] = remaining[differentIndex]
                remaining[differentIndex] = first
            }
        }
    }

    fun backToSelect() {
        _mode.value = "select"
        _current.value = null
        remaining.clear()
    }
}
