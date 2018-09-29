package br.com.codespace.kfeedreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import br.com.codespace.kfeedreader.adapter.ArticleItemAdapter
import br.com.codespace.kfeedreader.domain.ArticleItem
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {
    private lateinit var listArticleView: RecyclerView
    private var listArticleItems = arrayListOf<ArticleItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        listArticleView = findViewById(R.id.rvArticle)
        listArticleView.layoutManager = LinearLayoutManager(this)
        listArticleView.adapter = ArticleItemAdapter(listArticleItems, this)

        PkRSS.with(this).load("https://olhardigital.com.br/rss").callback(this).async()
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
        listArticleItems.clear()
        newArticles?.mapTo(listArticleItems) {
            ArticleItem.createFromArticle(it)
        }

        listArticleView.adapter.notifyDataSetChanged()
    }
}
