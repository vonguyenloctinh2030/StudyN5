package com.studyn5.kana.audio

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
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

    fun release() {
        player?.release()
        player = null
    }
}
