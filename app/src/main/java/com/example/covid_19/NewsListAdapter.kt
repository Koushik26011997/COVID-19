package com.example.covid_19

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.ArticlesItem

class NewsListAdapter(context: MainActivity, arrayList: ArrayList<ArticlesItem>) :
    RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {
    private var mArrayList = arrayList
    private var mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rows_news_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.news_img.setImageResource(R.drawable.icon)
        holder.news_source_text.text = mArrayList[position].source.name
        holder.dateTime.text = (mArrayList[position].publishedAt.replace("T", " ")).replace("Z", "")
        holder.news_title_text.text = mArrayList[position].title
        holder.news_description.text = mArrayList[position].description
        holder.full_story_btn.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(mArrayList[position].url))
            mContext.startActivity(intent)
        }
    }

    fun refreshList(arrayList: ArrayList<ArticlesItem>) {
        mArrayList = arrayList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var news_source_text = itemView.findViewById<TextView>(R.id.news_source_text)
        var news_title_text = itemView.findViewById<TextView>(R.id.news_title_text)
        var news_description = itemView.findViewById<TextView>(R.id.news_description)
        var full_story_btn = itemView.findViewById<TextView>(R.id.full_story_btn)
        var dateTime = itemView.findViewById<TextView>(R.id.dateTime)
        var news_img = itemView.findViewById<ImageView>(R.id.news_img)
    }
}