package com.studyn5.kana.audio

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.util.Log
import com.studyn5.kana.data.Kana
import java.util.Locale
import java.security.MessageDigest

/**
 * Phát âm thanh từ file mp3 bundle trong assets/audio/{romaji}.mp3 (giọng Nhật thật, offline).
 */
class AudioPlayer(context: Context) {

    private val appContext = context.applicationContext
    private var player: MediaPlayer? = null
    private var ttsReady = false
    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(appContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.let { engine ->
                    val languageResult = engine.setLanguage(Locale.JAPAN)
                    ttsReady = languageResult != TextToSpeech.LANG_MISSING_DATA &&
                        languageResult != TextToSpeech.LANG_NOT_SUPPORTED
                    engine.setSpeechRate(0.86f)
                    engine.setPitch(1.0f)
                }
            }
        }
    }

    fun play(kana: Kana) {
        play(kana.romaji)
    }

    fun play(romaji: String) {
        try {
            player?.release()
            player = null

            val newPlayer = MediaPlayer()
            player = newPlayer
            appContext.assets.openFd("audio/$romaji.mp3").use { audioFile ->
                newPlayer.setDataSource(
                    audioFile.fileDescriptor,
                    audioFile.startOffset,
                    audioFile.length,
                )
            }
            newPlayer.setOnCompletionListener { completedPlayer ->
                completedPlayer.release()
                if (player === completedPlayer) player = null
            }
            newPlayer.setOnErrorListener { failedPlayer, what, extra ->
                Log.e("AudioPlayer", "play fail romaji=$romaji what=$what extra=$extra")
                failedPlayer.release()
                if (player === failedPlayer) player = null
                true
            }
            newPlayer.prepare()
            newPlayer.start()
        } catch (e: Exception) {
            player?.release()
            player = null
            Log.e("AudioPlayer", "play fail romaji=$romaji", e)
        }
    }

    /**
     * Đọc trọn một từ/câu tiếng Nhật. Các màn nội dung mới dùng câu hoàn chỉnh,
     * không nối các file kana rời nên trường âm và âm ngắt vẫn giữ đúng nhịp.
     */
    fun speakJapanese(text: String) {
        player?.release()
        player = null
        val key = MessageDigest.getInstance("SHA-256")
            .digest(text.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
            .take(16)
        val assetPath = "audio/language_$key.mp3"
        try {
            val newPlayer = MediaPlayer()
            player = newPlayer
            appContext.assets.openFd(assetPath).use { audioFile ->
                newPlayer.setDataSource(audioFile.fileDescriptor, audioFile.startOffset, audioFile.length)
            }
            newPlayer.setOnCompletionListener { completed ->
                completed.release()
                if (player === completed) player = null
            }
            newPlayer.prepare()
            newPlayer.start()
            return
        } catch (_: Exception) {
            player?.release()
            player = null
        }
        if (!ttsReady) {
            Log.w("AudioPlayer", "Japanese TTS is not ready")
            return
        }
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "language-learning")
    }

    fun release() {
        player?.release()
        player = null
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }
}
