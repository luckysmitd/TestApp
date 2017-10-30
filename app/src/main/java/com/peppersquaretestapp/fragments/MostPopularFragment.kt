package com.peppersquaretestapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peppersquaretestapp.ArticleDetailActivity
import com.peppersquaretestapp.R
import com.peppersquaretestapp.adapters.ArticlesAdapter
import com.peppersquaretestapp.commons.BaseFragment
import com.peppersquaretestapp.commons.inflate
import com.peppersquaretestapp.interfaces.ItemClickListener
import com.peppersquaretestapp.mvp.model.ArticleInfo
import kotlinx.android.synthetic.main.fragment_home.*

class MostPopularFragment : BaseFragment() , ItemClickListener {

    private lateinit var articlesList : ArrayList<ArticleInfo>
    private lateinit var adapter : ArticlesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return container?.inflate(R.layout.fragment_home)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvArticles?.layoutManager = LinearLayoutManager(context)

        if(arguments != null && arguments.containsKey(MOST_POPULAR_ARTICLE))
        {
            articlesList = ArrayList()
            articlesList.add(arguments.getParcelable(MOST_POPULAR_ARTICLE))
            adapter = ArticlesAdapter(articlesList, this)
            rvArticles.adapter = adapter
            tvNoArticles.visibility = View.INVISIBLE
        }
        else
        {
            tvNoArticles.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        val MOST_POPULAR_ARTICLE = "MOST_POPULAR_ARTICLE"
        fun newInstance(articleInfo: ArticleInfo?): MostPopularFragment {
            val frag = MostPopularFragment()
            if(articleInfo != null) {

                val bundle = Bundle()
                bundle.putParcelable(MOST_POPULAR_ARTICLE, articleInfo)
                frag.arguments = bundle
            }

            return frag
        }
    }

    override fun onItemClick(position: Int)  {

        val intent = Intent(activity, ArticleDetailActivity::class.java)
        intent.putExtra(ArticleDetailActivity.SELECTED_ARTICLE, articlesList[position])
        startActivity(intent)

    }

    override fun onLikeClicked(position: Int) {

    }

}