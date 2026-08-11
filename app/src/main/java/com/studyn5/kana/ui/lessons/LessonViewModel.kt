package com.studyn5.kana.ui.lessons

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.studyn5.kana.data.LessonData
import com.studyn5.kana.data.LessonDefinition
import com.studyn5.kana.data.LessonItem

class LessonViewModel(private val data: LessonData) : ViewModel() {
    val lessons: List<LessonDefinition> = data.lessons
    val selectedLesson = mutableStateOf<LessonDefinition?>(null)
    val current = mutableStateOf<LessonItem?>(null)
    val position = mutableStateOf(0)

    private var roundItems: List<LessonItem> = emptyList()
    private val remaining = mutableListOf<LessonItem>()

    fun openLesson(lesson: LessonDefinition) {
        roundItems = data.items(lesson.id)
        if (roundItems.isEmpty()) return
        selectedLesson.value = lesson
        current.value = null
        position.value = 0
        remaining.clear()
        next()
    }

    fun next() {
        if (roundItems.isEmpty()) return
        if (remaining.isEmpty()) refill()
        current.value = remaining.removeAt(0)
        position.value = if (position.value >= roundItems.size) 1 else position.value + 1
    }

    fun backToLessons() {
        selectedLesson.value = null
        current.value = null
        position.value = 0
        remaining.clear()
        roundItems = emptyList()
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
