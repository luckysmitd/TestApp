package com.peppersquaretestapp.adapters

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.peppersquaretestapp.Constants
import com.peppersquaretestapp.R
import com.peppersquaretestapp.commons.Utils
import com.peppersquaretestapp.commons.inflate
import com.peppersquaretestapp.interfaces.ItemClickListener
import com.peppersquaretestapp.mvp.model.ArticleInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_detail_layout.view.*


class ArticlesAdapter(private val articleInfoList : List<ArticleInfo>,private val listener : ItemClickListener) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        return ViewHolder(parent?.inflate(R.layout.home_list_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articleInfoList[position], listener)
    }

    override fun getItemCount(): Int {
        return articleInfoList.size
    }

    class ViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(articleInfo: ArticleInfo, listener: ItemClickListener)
        {

            if(articleInfo.image != null && articleInfo.image.isNotBlank()) {
                Picasso.with(itemView.context).load(articleInfo.image).into(itemView?.ivImage)
            }

            if(articleInfo.title != null)
            itemView?.tvTitle?.text = articleInfo.title

            if(articleInfo.created_at != null) {
                val formattedDate = Utils.convertDate(Constants.FROM_FORMAT, Constants.TO_FORMAT, articleInfo.created_at)
                if (formattedDate != null) {
                    itemView?.tvDate?.text = formattedDate
                }
            }

            if(articleInfo.tags != null) {
                var tag = ""
                articleInfo.tags.forEach{
                    tag += if(tag.isBlank()) it else ", "+it
                }
                itemView?.tvCategoryTags?.text = tag
            }

            if(articleInfo.description != null) {
                itemView?.tvDescription?.text = articleInfo.description
                itemView?.tvDescription?.maxLines = 2
            }


                itemView?.tvLikes?.text = articleInfo.likes.toString()


            itemView?.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

            if(articleInfo.isLiked)
            {
                itemView?.ivLike?.setImageResource(R.drawable.liked)
            }
            else
            {
                itemView?.ivLike?.setImageResource(R.drawable.not_liked)
            }

            itemView?.ivLike?.setOnClickListener {
                    if(!articleInfo.isLiked)
                    {
                        listener.onLikeClicked(adapterPosition)
                    }


            }
        }
    }

}