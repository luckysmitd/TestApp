package com.peppersquaretestapp.mvp.contracts

/**
 * Created by smit on 29/10/17.
 */
class BaseContract {

    interface BasePresenter<in T>
    {
        fun subscribe()
        fun unSubscribe()
        fun attachView(view :T)
    }

    interface BaseView
    {
        fun showProgressBar()
        fun hideProgressBar()
    }
}