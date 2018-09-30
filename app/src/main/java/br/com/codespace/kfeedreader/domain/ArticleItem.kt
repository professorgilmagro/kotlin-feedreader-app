package br.com.codespace.kfeedreader.domain

import android.net.Uri
import android.os.Parcelable
import com.pkmmte.pkrss.Article
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleItem(
        val titulo: String,
        val autor: String?,
        val data: Long?,
        val link: Uri?,
        val imagem: Uri?,
        val descricao: String = ""
) : Parcelable {

    companion object {
        fun createFromArticle(item:Article): ArticleItem {
            var imgUrl = item.image?.toString()
            if (imgUrl != null && imgUrl.isEmpty()) {
                imgUrl = item.enclosure?.url
            }

            if (imgUrl == null) {
                imgUrl = ""
            }

            return ArticleItem(
                    item.title,
                    item?.author,
                    item?.date,
                    item.source,
                    Uri.parse(imgUrl)
            )
        }
    }
}
