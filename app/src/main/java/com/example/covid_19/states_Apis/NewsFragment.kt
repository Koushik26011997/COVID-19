package com.example.covid_19.states_Apis

import NetworkMonitor
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.common.Priority
import com.example.covid_19.NewsListAdapter
import com.example.covid_19.R
import com.example.covid_19.Utils
import com.google.android.material.snackbar.Snackbar
import com.rxandroidnetworking.RxAndroidNetworking
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewsFragment() : Fragment()
{
    var arrayListNews = arrayListOf<ArticlesItem>()
    lateinit var newsList : RecyclerView
    lateinit var swipeNews : SwipeRefreshLayout

    companion object
    {
        @JvmStatic
        fun newInstance() : NewsFragment
        {
            return NewsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        newsList = view.findViewById(R.id.newsList)
        swipeNews = view.findViewById(R.id.swipeNews)
        swipeNews.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))



        if (!NetworkMonitor(Utils.activity).isConnected) {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                }).show()
            Utils.activity.showPopup()
        }
        else
            getCurrenData(view)

        swipeNews.setOnRefreshListener {
            if (!NetworkMonitor(Utils.activity).isConnected)
            {
                Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_LONG)
                    .setAction("SETTINGS", View.OnClickListener {
                        startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                    }).show()
                swipeNews.isRefreshing = false
            }
            else
            {
                getCurrenData(view)
                swipeNews.isRefreshing = false
            }
        }
    }

    private fun getCurrenData(view: View)
    {
        Utils.activity.showLoader()
        var rxAnrRequest= RxAndroidNetworking.get("http://newsapi.org/v2/top-headlines?country=in&apiKey=6bdb68bcc65e4d34a8cf334beaee8a1a&q=covid")
            .setPriority(Priority.HIGH)
            .build()
            .getObjectObservable(ResponseCovidNews::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseCovidNews>
            {
                override fun onCompleted()
                {
                    Utils.activity.hideLoader()
                }

                override fun onError(e: Throwable)
                {
                    Utils.activity.hideLoader()
                    Toast.makeText(Utils.activity.applicationContext, "Could not get the current data!", Toast.LENGTH_SHORT).show()
                }

                override fun onNext(response: ResponseCovidNews)
                {
                    if (response.status == "ok" && response.totalResults >= 1)
                    {
                        // Result loaded successfully
                        arrayListNews.clear()
                        arrayListNews.addAll(response.articles)
                        prepareAdapter()
                    }
                    else
                    {
                        Snackbar.make(view, "No COVID-19 Related News Found", Snackbar.LENGTH_LONG).show()
                    }
                }
            })
    }

    private fun prepareAdapter()
    {
        newsList.apply {
            setHasFixedSize(true)
            var newsListAdapter = NewsListAdapter(Utils.activity, arrayListNews)
            adapter = newsListAdapter
            newsListAdapter.refreshList(arrayListNews)
        }
    }

    override fun onStart() {
        super.onStart()
        prepareAdapter()
    }

    override fun onResume() {
        super.onResume()
        prepareAdapter()
    }
}