package com.peppersquaretestapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.peppersquaretestapp.commons.Utils
import com.peppersquaretestapp.mvp.model.ArticleInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_detail_layout.*

class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artcile_detail)

        val extras = intent.extras

        if(extras.containsKey(SELECTED_ARTICLE))
        {
            val selectedArticle : ArticleInfo = extras.getParcelable(SELECTED_ARTICLE)
            setValues(selectedArticle)
        }
    }

    private fun setValues(selectedArticle: ArticleInfo) {

        rlLikeLayout?.visibility = View.INVISIBLE

        ivImage?.scaleType = ImageView.ScaleType.FIT_XY

        if(selectedArticle.image != null && selectedArticle.image.isNotBlank()) {
            Picasso.with(this).load(selectedArticle.image).into(ivImage)
        }

        if(selectedArticle.title != null)
            tvTitle?.text = selectedArticle.title

        if(selectedArticle.created_at != null) {
            val formattedDate = Utils.convertDate(Constants.FROM_FORMAT, Constants.TO_FORMAT, selectedArticle.created_at)
            if (formattedDate != null) {
                tvDate?.text = formattedDate
            }
        }

        if(selectedArticle.tags != null) {
            var tag = ""
            selectedArticle.tags.forEach{
                tag += if(tag.isBlank()) it else ", "+it
            }
            tvCategoryTags?.text = tag
        }

        if(selectedArticle.description != null) {
            tvDescription?.text = selectedArticle.description

        }


        tvLikes?.text = selectedArticle.likes.toString()




    }

    companion object {
        val SELECTED_ARTICLE = "SELECTED_ARTICLE"
    }
}
