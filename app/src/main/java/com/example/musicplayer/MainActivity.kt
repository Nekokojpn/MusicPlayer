package com.example.musicplayer

import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    val ip = "192.168.1.12"
    val search_url = "http://${ip}/musicplayer/search.php"
    val thumb_url = "http://${ip}/musicplayer/thumb/"
    val music_url = "http://${ip}/musicplayer/music/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_btn.setOnClickListener {
            clearElements()
            var res = AsyncSearch(search_url + "?title=" + title_in.text.toString()).execute().get()
            Log.d("", res)
            val pattern = """::title : .*?end;::"""
            val regex = Regex(pattern)
            if (res != null) {
                var finds = regex.findAll(res, 0)
                if (finds != null) {
                    for (t in finds) {
                        val tex = addElementText(regexSearch("""title : .*?end;""", t, "title : ", "end;"))
                        val asIm = AsyncImage(thumb_url + regexSearch("""img_path : .*?end;""", t, "img_path : ", "end;"))
                        addElementImage(asIm.execute().get())
                        tex.setOnClickListener {
                            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
                            intent.putExtra("path", music_url + regexSearch(
                                """path : .*?end;""",
                                t,
                                "path : ",
                                "end;"
                            ))
                            intent.putExtra("img", thumb_url + regexSearch("""img_path : .*?end;""", t, "img_path : ", "end;"))
                            intent.putExtra("title", regexSearch("""title : .*?end;""", t, "title : ", "end;"))
                            startActivity(intent)
                        }
                    }
                } else
                    displayEmpty()
            } else
                displayEmpty()
        }
    }
    private fun displayEmpty(){
        clearElements()
        addElementText("NO RESULTS...")
    }
    private fun addElementText(text: String): TextView {
        val tex = TextView(this)
        tex.text = text
        tex.textSize = 24f
        l2.addView(tex)
        return tex
    }
    private fun addElementImage(bitmap: Bitmap) {
        val img = ImageView(this)
        img.setImageBitmap(bitmap)
        l1.addView(img)
    }
    private fun clearElements() {
        l1.removeAllViews()
        l2.removeAllViews()
    }
    private fun regexSearch( pattern: String, t: MatchResult, start_oldvalue: String, end_oldvalue: String) : String {
        return Regex(pattern)?.find(t.groupValues[0])!!.groupValues[0].replace(
            start_oldvalue,
            ""
        ).replace(end_oldvalue, "")
    }
}
