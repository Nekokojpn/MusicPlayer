package com.example.musicplayer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import kotlin.properties.Delegates

class AsyncImage : AsyncTask<String, Int, Bitmap> {
    var urlConnection: HttpURLConnection by Delegates.notNull<HttpURLConnection>()
    var url = ""
    constructor(_url : String){
        url = _url
    }

    override fun doInBackground(vararg p0: String?): Bitmap? {
        try{
            var url: URL = URL(url)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.instanceFollowRedirects = false
            urlConnection.connect()
            var i = urlConnection.responseCode
            //val br = BufferedReader(InputStreamReader(urlConnection!!.inputStream))

            if(i == HttpURLConnection.HTTP_OK){
                var inp =urlConnection.inputStream
                var bmp = BitmapFactory.decodeStream(inp)
                inp.close()
                return bmp
            }


        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}