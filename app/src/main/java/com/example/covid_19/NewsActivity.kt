package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.common.Priority
import com.example.covid_19.states_Apis.ArticlesItem
import com.example.covid_19.states_Apis.ResponseCovidNews
import com.google.android.material.snackbar.Snackbar
import com.rxandroidnetworking.RxAndroidNetworking
import kotlinx.android.synthetic.main.activity_news.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewsActivity : AppCompatActivity() {

    var arrayListNews = arrayListOf<ArticlesItem>()
    lateinit var newsList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        newsList = findViewById(R.id.newsList)


        if (!NetworkMonitor(Utils.activity).isConnected) {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
            Utils.activity.showPopup()
        }
        else
            getCurrenData()

//        swipeNews.setOnRefreshListener {
//            if (!NetworkMonitor(Utils.activity).isConnected)
//            {
//                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
//                swipeNews.isRefreshing = false
//            }
//            else
//            {
//                getCurrenData()
//                swipeNews.isRefreshing = false
//            }
//        }
    }

    private fun getCurrenData()
    {
        loaderLayout.visibility = View.VISIBLE
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
                    loaderLayout.visibility = View.GONE
                }

                override fun onError(e: Throwable)
                {
                    loaderLayout.visibility = View.GONE
                    Toast.makeText(applicationContext, "Could not get the current data!", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(applicationContext, "No COVID-19 Related News Found", Toast.LENGTH_SHORT).show()
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
