package br.com.codespace.kfeedreader

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.TextView
import br.com.codespace.kfeedreader.adapter.ArticleItemAdapter
import br.com.codespace.kfeedreader.domain.ArticleItem
import java.text.SimpleDateFormat
import java.util.*

class ReaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reader)

        val article:ArticleItem = intent.getParcelableExtra(ArticleItemAdapter.NEWSTEXT)
        val txtArea = findViewById<TextView>(R.id.txtReaderView)
        val txtTitle = findViewById<TextView>(R.id.txtReaderTitle)
        val btnLink = findViewById<Button>(R.id.btnReaderArticleLink)

        btnLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, article.link))
            finish()
        }

        txtTitle.text = article.titulo
        txtArea.text = article.descricao.replace("Leia mais...", "").trim()

        var font:Typeface = Typeface.createFromAsset(assets, "fonts/GothamMedium.ttf")
        txtTitle.typeface = font; btnLink.typeface = font

        font = Typeface.createFromAsset(assets, "fonts/Gotham-XLight.otf")
        txtArea.typeface = font

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val sourceName = prefs.getString("sourceName", getTitle().toString())
        val publishedAt =  SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(article.data)
        title = "$sourceName | $publishedAt"
    }
}
