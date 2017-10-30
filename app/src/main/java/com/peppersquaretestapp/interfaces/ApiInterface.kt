package com.peppersquaretestapp.interfaces

import com.peppersquaretestapp.Constants
import com.peppersquaretestapp.Constants.BASE_URL
import com.peppersquaretestapp.mvp.model.ArticleInfo
import io.reactivex.Observable
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by smit on 29/10/17.
 */
interface ApiInterface {

    @GET("api/v1/article")
    fun getArticles(@Query("id")id: String, @Query("author") author: String, @Query("published") published : Boolean) :
            Observable<ArrayList<ArticleInfo>>

    @POST("api/v1/article")
    fun postArticle(@Body request: RequestBody) :
            Observable<JSONObject>


    companion object {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(ApiInterface::class.java);
        }
    }
}