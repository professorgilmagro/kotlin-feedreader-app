package br.com.codespace.kfeedreader

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {
    lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    var listItems = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.rvContent)
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = ItemAdapter(listItems, this)

        PkRSS.with(this).load("https://rss.tecmundo.com.br/feed").callback(this).async()
    }

    override fun onLoadFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPreload() {
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        listItems.clear()
        newArticles?.mapTo(listItems) {
            Item(it.title, it.author, it.date, it.source, it.enclosure.url)
        }

        listView.adapter.notifyDataSetChanged()
    }

    data class Item(val titulo: String, val autor: String, val data: Long, val link: Uri, val imagem: String)
}
