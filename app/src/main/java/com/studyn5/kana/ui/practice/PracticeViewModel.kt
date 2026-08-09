package com.studyn5.kana.ui.practice

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.studyn5.kana.data.PracticeCategory
import com.studyn5.kana.data.PracticeData
import com.studyn5.kana.data.PracticeItem
import com.studyn5.kana.data.PracticeScript

class PracticeViewModel(private val data: PracticeData) : ViewModel() {
    val mode = mutableStateOf("select")
    val script = mutableStateOf(PracticeScript.HIRAGANA)
    val category = mutableStateOf(PracticeCategory.BASIC)
    val current = mutableStateOf<PracticeItem?>(null)
    val selectedIds = mutableStateListOf<String>()

    private val remaining = mutableListOf<PracticeItem>()
    private var roundItems: List<PracticeItem> = emptyList()

    val available: List<PracticeItem>
        get() = data.items(script.value, category.value)

    val selectedCount: Int
        get() = selectedIds.size

    val practiceCount: Int
        get() = buildRoundItems().size

    fun setScript(value: PracticeScript) {
        if (script.value == value) return
        script.value = value
        resetSelectionForFilter()
    }

    fun setCategory(value: PracticeCategory) {
        if (category.value == value) return
        category.value = value
        resetSelectionForFilter()
    }

    fun toggle(item: PracticeItem) {
        if (item.id in selectedIds) selectedIds.remove(item.id) else selectedIds.add(item.id)
    }

    fun selectAllVisible() {
        val visibleIds = available.map(PracticeItem::id)
        val allSelected = visibleIds.isNotEmpty() && visibleIds.all(selectedIds::contains)
        if (allSelected) selectedIds.removeAll(visibleIds.toSet())
        else {
            selectedIds.removeAll { id -> id !in visibleIds }
            visibleIds.filterNot(selectedIds::contains).forEach(selectedIds::add)
        }
    }

    fun start() {
        roundItems = buildRoundItems()
        if (roundItems.isEmpty()) return
        mode.value = "play"
        remaining.clear()
        current.value = null
        next()
    }

    fun next() {
        if (roundItems.isEmpty()) return
        if (remaining.isEmpty()) refill()
        current.value = remaining.removeAt(0)
    }

    fun backToSelect() {
        mode.value = "select"
        current.value = null
        remaining.clear()
        roundItems = emptyList()
    }

    private fun buildRoundItems(): List<PracticeItem> {
        val selected = available.filter { it.id in selectedIds }
        return if (category.value == PracticeCategory.BASIC) data.expandBasic(selected) else selected
    }

    private fun resetSelectionForFilter() {
        selectedIds.clear()
        if (category.value != PracticeCategory.BASIC) {
            selectedIds.addAll(available.map(PracticeItem::id))
        }
    }

    private fun refill() {
        remaining.addAll(roundItems.shuffled())
        val previous = current.value
        if (remaining.size > 1 && remaining.first() == previous) {
            val differentIndex = remaining.indexOfFirst { it != previous }
            if (differentIndex > 0) {
                val first = remaining[0]
                remaining[0] = remaining[differentIndex]
                remaining[differentIndex] = first
            }
        }
    }
}
