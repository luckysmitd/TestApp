package com.peppersquaretestapp


import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.peppersquaretestapp.commons.ActivityFragmentCommunicator
import com.peppersquaretestapp.fragments.CreateFragment
import com.peppersquaretestapp.fragments.HomeFragment
import com.peppersquaretestapp.fragments.MostPopularFragment
import com.peppersquaretestapp.mvp.model.ArticleInfo
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {


    private val HOME_FRAG_TAG = "HOME_FRAG"
    private val MOST_POPULAR_FRAG_TAG = "MOST_POPULAR_FRAG"
    private val CREATE_FRAG_TAG = "CREATE_FRAG"

    private var articleInfoArrayList : ArrayList<ArticleInfo>? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                tvToolbarTitle.text = getString(R.string.home)
                loadFragment(HomeFragment.newInstance(), HOME_FRAG_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_popular -> {
                tvToolbarTitle.text = getString(R.string.most_popular)
                val mostPopularArticle = getMostPopularArticle()
                if(getMostPopularArticle() != null)
                {
                    loadFragment(MostPopularFragment.newInstance(mostPopularArticle), MOST_POPULAR_FRAG_TAG)
                }
                else
                {
                    loadFragment(MostPopularFragment.newInstance(null), MOST_POPULAR_FRAG_TAG)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_create -> {
                tvToolbarTitle.text = getString(R.string.create)
                loadFragment(CreateFragment.newInstance(), CREATE_FRAG_TAG)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun getMostPopularArticle() : ArticleInfo? {
        if(articleInfoArrayList != null && articleInfoArrayList!!.isNotEmpty())
        {
            val sortedList = articleInfoArrayList!!.sortedWith(compareBy { it.likes })
            return sortedList[sortedList.size-1]
        }
        return null
    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        tvToolbarTitle.text = getString(R.string.home)
        loadFragment(HomeFragment.newInstance(), HOME_FRAG_TAG)

       disposable = ActivityFragmentCommunicator.getObservable().subscribe {
            articleInfoArrayList -> this.articleInfoArrayList = articleInfoArrayList
        }


    }





    private fun loadFragment(fragment: Fragment, fragmentTag: String) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameContainer, fragment, fragmentTag)
        ft.commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
