package br.com.codespace.kfeedreader.task

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.widget.ImageView
import java.net.URL

class DownloadImageTask(val imageView: ImageView) : AsyncTask<Uri, Void, Bitmap>() {
    override fun doInBackground(vararg params: Uri?): Bitmap {
        val url = params[0]
        val stream = URL(url.toString()).openStream()
        val bitmap = BitmapFactory.decodeStream(stream)

        return bitmap
    }

    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }

}