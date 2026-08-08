package com.studyn5.kana.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.studyn5.kana.data.Kana

/**
 * Phát âm thanh từ file mp3 bundle trong assets/audio/{romaji}.mp3 (giọng Nhật thật, offline).
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
            player = MediaPlayer.create(
                appContext,
                Uri.parse("file:///android_asset/$romaji.mp3"),
            )
            player?.setOnCompletionListener { it.release(); if (player == it) player = null }
            player?.start()
        } catch (e: Exception) {
            android.util.Log.e("AudioPlayer", "play fail: $romaji", e)
        }
    }

    fun release() {
        player?.release()
        player = null
    }
}
