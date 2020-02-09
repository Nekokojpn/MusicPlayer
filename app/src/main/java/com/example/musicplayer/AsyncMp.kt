package com.example.musicplayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
import android.util.Log

class AsyncMp : AsyncTask<String, Int, MediaPlayer> {
    var url = ""

    constructor(_url : String){
        url = _url
    }

    override fun doInBackground(vararg p0: String?): MediaPlayer? {
        try {
            val me = MediaPlayer()
            me.setAudioStreamType(AudioManager.STREAM_MUSIC)
            me.setDataSource(this.url)
            me.prepare()
            return me
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}