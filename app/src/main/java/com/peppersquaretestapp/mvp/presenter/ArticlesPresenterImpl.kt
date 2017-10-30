package com.peppersquaretestapp.mvp.presenter

import android.support.annotation.MainThread
import android.view.View
import com.peppersquaretestapp.interfaces.ApiInterface
import com.peppersquaretestapp.mvp.contracts.ArticlesContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by smit on 29/10/17.
 */
class ArticlesPresenterImpl : ArticlesContract.ArticlesPresenter {

    private lateinit var disposable : Disposable

    private lateinit var articlesView : ArticlesContract.ArticlesView

    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.dispose()
    }

    override fun attachView(view: ArticlesContract.ArticlesView) {
        articlesView = view
    }

    override fun loadArticles(id: String, author: String, published: Boolean) {
        articlesView.showProgressBar()
        disposable = ApiInterface.create().getArticles(id,author,false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {

                            articlesList -> articlesView.showArticles(articlesList)

                            articlesView.hideProgressBar()
                        },
                        {
                            error->
                            articlesView.hideProgressBar()
                            articlesView.showError(error?.message.toString())
                        }
                )

    }
}