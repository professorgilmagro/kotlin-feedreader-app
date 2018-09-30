package br.com.codespace.kfeedreader.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.com.codespace.kfeedreader.task.DownloadImageTask
import br.com.codespace.kfeedreader.R
import br.com.codespace.kfeedreader.domain.ArticleItem
import java.text.SimpleDateFormat
import java.util.*

class ArticleItemAdapter(private val list: ArrayList<ArticleItem>, private val context: Context) :
        RecyclerView.Adapter<ArticleItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.txtTitulo)
        val autor: TextView = view.findViewById(R.id.txtAutor)
        val data: TextView = view.findViewById(R.id.txtPublishedDate)
        val imagem = view.findViewById(R.id.imgArea) as ImageView
        val btnVerMais = view.findViewById(R.id.btnSeeMore) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent?.context).inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.titulo.text = item.titulo
        holder.autor.text = item.autor
        holder.data.text = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(item.data)

        holder.btnVerMais.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, item.link)
            context.startActivity(intent)
        }

        if (item.imagem != null && !item.imagem.path.isEmpty()) {
            DownloadImageTask(holder.imagem).execute(item.imagem)
        }
    }

    override fun getItemCount(): Int = list.size
}