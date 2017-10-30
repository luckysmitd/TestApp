package com.peppersquaretestapp.mvp.contracts

import com.peppersquaretestapp.mvp.model.ArticleInfo

/**
 * Created by smit on 29/10/17.
 */
class CreateArticlesContract {

    interface CreateArticlesPresenter: BaseContract.BasePresenter<CreateArticlesView> {
        fun addArticles(title: String?, description : String?, tags: String?, author: String?, imageUrl: String?)
    }

    interface CreateArticlesView: BaseContract.BaseView
    {
        fun showError(errorMessage : String)
        fun showSuccess()
    }
}