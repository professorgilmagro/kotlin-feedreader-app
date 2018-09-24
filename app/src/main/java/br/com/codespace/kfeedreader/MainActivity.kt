package br.com.codespace.kfeedreader

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {
    private lateinit var listView: RecyclerView
    private var listItems = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        listView = findViewById(R.id.rvContent)
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = ItemAdapter(listItems, this)

        PkRSS.with(this).load("https://rss.tecmundo.com.br/feed").callback(this).async()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.refresh -> {
                onResume()
            }

            R.id.exit -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onLoadFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPreload() {
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        listItems.clear()
        newArticles?.mapTo(listItems) {
            var article = it
            Item(it.title, it.author, it.date, it.source, it.enclosure.url)
        }

        listView.adapter.notifyDataSetChanged()
    }

    data class Item(val titulo: String, val autor: String, val data: Long, val link: Uri, val imagem: String)
}
