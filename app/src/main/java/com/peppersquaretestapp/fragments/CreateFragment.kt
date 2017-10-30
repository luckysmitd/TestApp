package com.peppersquaretestapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peppersquaretestapp.Constants
import com.peppersquaretestapp.R
import com.peppersquaretestapp.commons.BaseFragment
import com.peppersquaretestapp.commons.inflate
import com.peppersquaretestapp.mvp.contracts.CreateArticlesContract
import com.peppersquaretestapp.mvp.presenter.CreateArticlesPresenterImpl
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.layout_progress_bar.*

class CreateFragment : BaseFragment() , CreateArticlesContract.CreateArticlesView{

    lateinit var mPresenter: CreateArticlesContract.CreateArticlesPresenter

    override fun showSuccess() {
        showAlertDialog("Article added successfully!")
    }



    override fun showProgressBar() {
        showProgressBar(progressBar)
    }

    override fun hideProgressBar() {
        hideProgressBar(progressBar)
    }


    override fun showError(errorMessage: String) {
        showAlertDialog(errorMessage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = CreateArticlesPresenterImpl()
        mPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return container?.inflate(R.layout.fragment_create)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvPublish?.setOnClickListener {

            if(isConnectedToInternet(context)) {
                mPresenter.addArticles(etTitle?.text.toString(), etDescription?.text.toString(), etCategoryTags?.text.toString(),
                        etAuthor?.text.toString(), etImageUrl?.text.toString())
            }
            else
            {
                showError(Constants.CHECK_INTERNET_CONNECTION)
            }

        }
    }


    companion object {
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }


}