package com.peppersquaretestapp.commons

import com.peppersquaretestapp.mvp.model.ArticleInfo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject.create

internal object ActivityFragmentCommunicator {

    private var articleListCommunicationObservable = create<ArrayList<ArticleInfo>>()


    fun postArticleList(articleInfoList: ArrayList<ArticleInfo>)
    {
        articleListCommunicationObservable.onNext(articleInfoList)
    }

    fun getObservable() : Observable<ArrayList<ArticleInfo>>
    {
         return articleListCommunicationObservable
    }


}
