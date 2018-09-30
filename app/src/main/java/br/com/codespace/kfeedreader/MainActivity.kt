package br.com.codespace.kfeedreader

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import br.com.codespace.kfeedreader.adapter.ArticleItemAdapter
import br.com.codespace.kfeedreader.domain.ArticleItem
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {
    private lateinit var listArticleView: RecyclerView
    private lateinit var prefs: SharedPreferences
    private lateinit var sourceFeeds: Array<String>
    private var listArticleItems = arrayListOf<ArticleItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        sourceFeeds = resources.getStringArray(R.array.source_feeds)
    }

    override fun onResume() {
        listArticleView = findViewById(R.id.rvArticle)
        listArticleView.layoutManager = LinearLayoutManager(this)
        listArticleView.adapter = ArticleItemAdapter(listArticleItems, this)

        val url = prefs.getString("sourceUrl", "https://rss.tecmundo.com.br/feed")
        PkRSS.with(this).load(url).callback(this).async()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        with(menu) {
            this!!.clear()
            for ((index, item) in sourceFeeds.withIndex()) {
                val title = item.split("->")[0]
                add(0, index, Menu.NONE, title.trim())
            }

            add(0, sourceFeeds.size, Menu.NONE, R.string.exit)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * Cria um menu dinâmico com base numa lista de sites provedores de feed de notícias
     * A lista encontra-se no resource string lists
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        for (itemIndex in 0 until sourceFeeds.count()) {
            if (item?.itemId == itemIndex) {
                val url = sourceFeeds[itemIndex].split("->")[1]
                prefs.edit().putString("sourceUrl", url.trim()).apply()
                onResume()
                break
            }
        }

        if (item?.itemId == sourceFeeds.count()) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onLoadFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPreload() {
        listArticleItems.clear()
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        newArticles?.mapTo(listArticleItems) {
            ArticleItem.createFromArticle(it)
        }

        listArticleView.adapter.notifyDataSetChanged()
    }
}
