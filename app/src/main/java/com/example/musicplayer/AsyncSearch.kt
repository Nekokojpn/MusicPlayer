package com.example.musicplayer

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.properties.Delegates

class AsyncSearch : AsyncTask<String, Int, String> {
    var urlConnection : HttpURLConnection by Delegates.notNull<HttpURLConnection>()
    var url: String? = null
    constructor(_url: String) {
        url = _url
    }

    override fun doInBackground(vararg p0: String?): String? {
        try{
            var url: URL = URL(url)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.doOutput = true
            urlConnection.connect()
            var i = urlConnection.responseCode
            val br = BufferedReader(InputStreamReader(urlConnection.inputStream))

            var line: String? = null
            val sb = StringBuilder()

            for (line in br.readLines()) {
                line?.let { sb.append(line) }
            }

            br.close()
            return sb.toString()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}