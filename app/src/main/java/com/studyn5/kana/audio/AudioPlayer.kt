package com.studyn5.kana.audio

import android.content.Context
import android.media.MediaPlayer
import com.studyn5.kana.data.Kana

/**
 * Phát âm thanh từ file mp3 được bundle sẵn trong assets/audio/{romaji}.mp3
 * (giọng Nhật thật, offline). Thay thế TextToSpeech.
 */
class AudioPlayer(context: Context) {

    private val appContext = context.applicationContext
    private var player: MediaPlayer? = null

    fun play(kana: Kana) {
        play(kana.romaji)
    }

    fun play(romaji: String) {
        try {
            player?.release()
            player = MediaPlayer().apply {
                setDataSource(appContext.assets.openFd("$romaji.mp3"))
                setOnPreparedListener { it.start() }
                prepareAsync()
            }
        } catch (e: Exception) {
            // File không tồn tại -> bỏ qua (không crash)
            e.printStackTrace()
        }
    }

    fun release() {
        player?.release()
        player = null
    }
}
