package com.peppersquaretestapp.mvp.contracts

import com.peppersquaretestapp.mvp.model.ArticleInfo

/**
 * Created by smit on 29/10/17.
 */
class ArticlesContract {

    interface ArticlesPresenter: BaseContract.BasePresenter<ArticlesView>{
        fun loadArticles(id : String, author: String, published: Boolean)
    }

    interface ArticlesView: BaseContract.BaseView
    {
        fun showArticles(articlesList : ArrayList<ArticleInfo>)
        fun showError(errorMessage : String)
    }
}