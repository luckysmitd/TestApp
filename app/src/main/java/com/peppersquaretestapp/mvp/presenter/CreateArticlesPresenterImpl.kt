package com.peppersquaretestapp.mvp.presenter

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.peppersquaretestapp.Constants
import com.peppersquaretestapp.interfaces.ApiInterface
import com.peppersquaretestapp.mvp.contracts.CreateArticlesContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody


class CreateArticlesPresenterImpl : CreateArticlesContract.CreateArticlesPresenter {

    private lateinit var createArticlesView : CreateArticlesContract.CreateArticlesView

    private lateinit var disposable : Disposable


    override fun attachView(view: CreateArticlesContract.CreateArticlesView) {
        createArticlesView = view
    }

    override fun addArticles(title: String?, description : String?, tags: String?, author: String?, imageUrl: String?) {

        when
        {
            title.isNullOrBlank() -> createArticlesView.showError(Constants.ENTER_TITLE)
            description.isNullOrBlank() -> createArticlesView.showError(Constants.ENTER_DESCRIPTION)
            tags.isNullOrBlank() -> createArticlesView.showError(Constants.ENTER_TAGS)
            author.isNullOrBlank() -> createArticlesView.showError(Constants.ENTER_AUTHOR)
            imageUrl.isNullOrBlank() -> createArticlesView.showError(Constants.ENTER_IMAGE_URL)
            else ->
            {
                createArticlesView.showProgressBar()
              /* val params =  hashMapOf(Constants.TITLE to title,
                       Constants.DESCRIPTION to description,
                       Constants.TAGS to tags,
                       Constants.AUTHOR to author,
                       Constants.IMAGE to imageUrl,
                       Constants.PUBLISHED to false
                       )*/
                val tagArray = JsonArray()
                if(tags != null && tags.contains(","))
                {
                    for(tag in tags.split(","))
                    {
                        tagArray.add(tag)
                    }
                }
                else
                {
                    if(tags != null)
                    tagArray.add(tags)
                }


                val jsonObject = JsonObject()
                jsonObject.addProperty(Constants.TITLE , title)
                jsonObject.addProperty(Constants.DESCRIPTION , description)
                jsonObject.add(Constants.TAGS , tagArray)
                jsonObject.addProperty(Constants.AUTHOR , author)
                jsonObject.addProperty(Constants.IMAGE , imageUrl)
                jsonObject.addProperty(Constants.PUBLISHED , false)

                val body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

                disposable = ApiInterface.create().postArticle(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    createArticlesView.hideProgressBar()
                                    createArticlesView.showSuccess()
                                },
                                {
                                    error->
                                    createArticlesView.hideProgressBar()
                                    createArticlesView.showError( error?.message.toString())
                                }
                        )
            }
        }



    }



    override fun subscribe() {

    }

    override fun unSubscribe() {
        disposable.dispose()
    }



}