package com.peppersquaretestapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peppersquaretestapp.ArticleDetailActivity
import com.peppersquaretestapp.Constants
import com.peppersquaretestapp.R
import com.peppersquaretestapp.adapters.ArticlesAdapter
import com.peppersquaretestapp.commons.ActivityFragmentCommunicator
import com.peppersquaretestapp.commons.BaseFragment
import com.peppersquaretestapp.commons.inflate
import com.peppersquaretestapp.interfaces.ItemClickListener
import com.peppersquaretestapp.mvp.contracts.ArticlesContract
import com.peppersquaretestapp.mvp.model.ArticleInfo
import com.peppersquaretestapp.mvp.presenter.ArticlesPresenterImpl
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_progress_bar.*


class HomeFragment : BaseFragment() , ArticlesContract.ArticlesView, ItemClickListener{

    private lateinit var mPresenter: ArticlesContract.ArticlesPresenter

    private lateinit var articlesList : ArrayList<ArticleInfo>
    private lateinit var adapter : ArticlesAdapter

    override fun showProgressBar() {
        showProgressBar(progressBar)
    }

    override fun hideProgressBar() {
        hideProgressBar(progressBar)
    }

    override fun showArticles(articlesList: ArrayList<ArticleInfo>) {

        if(articlesList.isNotEmpty()) {
            ActivityFragmentCommunicator.postArticleList(articlesList)
            tvNoArticles.visibility = View.INVISIBLE
            this.articlesList = articlesList
            adapter = ArticlesAdapter(articlesList, this)
            rvArticles.adapter = adapter
        }
        else
        {
            tvNoArticles.visibility = View.VISIBLE
        }
    }

    override fun showError(errorMessage: String) {
        showAlertDialog(errorMessage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = ArticlesPresenterImpl()
        mPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return container?.inflate(R.layout.fragment_home)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()
        rvArticles?.layoutManager = LinearLayoutManager(context)
        if(isConnectedToInternet(context)) {
            mPresenter.loadArticles("", "Smit", false)
        }
        else
        {
            showError(Constants.CHECK_INTERNET_CONNECTION)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unSubscribe()
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onItemClick(position: Int)  {

        val intent = Intent(activity, ArticleDetailActivity::class.java)
        intent.putExtra(ArticleDetailActivity.SELECTED_ARTICLE, articlesList[position])
        startActivity(intent)

    }

    override fun onLikeClicked(position: Int) {
        val article = articlesList[position]
        article.isLiked = true
        article.likes+=1
        adapter.notifyDataSetChanged()
    }

}


