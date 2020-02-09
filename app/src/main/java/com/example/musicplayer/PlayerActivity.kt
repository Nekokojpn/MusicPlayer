package com.example.musicplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        thumb.setImageBitmap(AsyncImage(intent.getStringExtra("img")).execute().get())
        title_view.text = intent.getStringExtra("title")
        var mp: MediaPlayer? = null
        var b = true

        play_btn.setOnClickListener {
            if(b) {
                if (mp != null)
                    mp!!.pause()
                mp = AsyncMp(intent.getStringExtra("path")).execute().get()
                mp!!.start()
                b = false
                play_btn.setImageResource(R.drawable.stopbtn)
            }
            else{
                if (mp != null)
                    mp!!.pause()
                b = true
                play_btn.setImageResource(R.drawable.playbtn)
            }
        }
        play_btn.performClick()
        minus.setOnClickListener {
            mp!!.seekTo(mp!!.currentPosition - 10000)
        }
        plus.setOnClickListener {
            mp!!.seekTo(mp!!.currentPosition + 10000)
        }
    }
}