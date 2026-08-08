package com.studyn5.kana.ui.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.studyn5.kana.data.KanaData
import com.studyn5.kana.data.KanaType

class KanaListViewModel : ViewModel() {

    /** Nhóm (hàng) có sẵn, ví dụ a, k, s, ... */
    val groups: List<String> = buildList {
        addAll(
            (KanaData.hiragana + KanaData.katakana)
                .map { it.group }
                .distinct()
                .sorted()
        )
    }

    private val _selectedType = androidx.compose.runtime.mutableStateOf(KanaType.HIRAGANA)
    var selectedType = _selectedType
        private set

    private val _selectedGroup = androidx.compose.runtime.mutableStateOf(groups.first())
    var selectedGroup = _selectedGroup
        private set

    fun setType(type: KanaType) {
        _selectedType.value = type
    }

    fun setGroup(group: String) {
        _selectedGroup.value = group
    }

    fun currentList(): List<com.studyn5.kana.data.Kana> {
        val source = if (_selectedType.value == KanaType.HIRAGANA) KanaData.hiragana else KanaData.katakana
        return source.filter { it.group == _selectedGroup.value }
    }
}
