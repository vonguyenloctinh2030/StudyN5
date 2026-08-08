package com.studyn5.kana.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

/**
 * Quản lý TextToSpeech tiếng Nhật.
 * Dùng Locale("ja","JP"); fallback nếu thiết bị thiếu giọng Nhật.
 */
class JapaneseTtsManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var ready = false

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Ưu tiên giọng Nhật; nếu thiếu thì setLocale vẫn chạy ở một số máy
                val ja = Locale("ja", "JP")
                val result = tts?.setLanguage(ja)
                ready = result != TextToSpeech.LANG_MISSING_DATA &&
                    result != TextToSpeech.LANG_NOT_SUPPORTED
                if (!ready) {
                    // Thử lại với Locale.JAPANESE
                    val r2 = tts?.setLanguage(Locale.JAPANESE)
                    ready = r2 != TextToSpeech.LANG_MISSING_DATA &&
                        r2 != TextToSpeech.LANG_NOT_SUPPORTED
                }
                tts?.setPitch(1.0f)
                tts?.setSpeechRate(0.85f)
            }
        }
    }

    fun isReady(): Boolean = ready

    fun speak(text: String) {
        if (!ready) return
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "kana_${System.currentTimeMillis()}")
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
