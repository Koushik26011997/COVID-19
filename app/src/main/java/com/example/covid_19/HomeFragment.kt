package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.example.covid_19.states_Apis.ResponseTotalCases
import com.example.covid_19.states_Apis.StatewiseItem
import com.google.android.material.snackbar.Snackbar
import com.jacksonandroidnetworking.JacksonParserFactory
import com.rxandroidnetworking.RxAndroidNetworking
import kotlinx.android.synthetic.main.homefragment.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HomeFragment(mainActivity: MainActivity) : Fragment()
{
    val activity = mainActivity
    val arrayList = arrayListOf<StatewiseItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.homefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        AndroidNetworking.initialize(activity!!.applicationContext)
        AndroidNetworking.setParserFactory(JacksonParserFactory())

        if (!NetworkMonitor(activity).isConnected)
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT).setAction("SETTINGS", View.OnClickListener {
                startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
            }).show()
        else
            getCurrenData()

        prepareAdapter()
        val animation = AnimationUtils.loadAnimation(activity, R.anim.rotate)
        refreshBtn.setOnClickListener(){
            refreshBtn.startAnimation(animation)
            getCurrenData()
        }
    }

    private fun prepareAdapter()
    {
        var statesListAdapter = StatesListAdapter(arrayList)
        statesList.setHasFixedSize(true)
        statesList.adapter = statesListAdapter
        statesListAdapter.refreshList(arrayList)
    }

    private fun getCurrenData()
    {
        activity.showLoader()
        var rxAnrRequest= RxAndroidNetworking.get(BuildConfig.BASE_URL + "data.json")
            .setPriority(Priority.HIGH)
            .build()
            .getObjectObservable(ResponseTotalCases::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseTotalCases>
            {
                override fun onCompleted()
                {
                    activity.hideLoader()
                }

                override fun onError(e: Throwable)
                {
                    activity.hideLoader()
                    Toast.makeText(activity.applicationContext, "Could not get the current data!", Toast.LENGTH_SHORT).show()
                }

                override fun onNext(response: ResponseTotalCases)
                {
                    arrayList.clear()
                    arrayList.addAll(response.statewise)
                    confirmedCase.text = arrayList.get(0).confirmed + "\n" + "[+" + arrayList.get(0).deltaconfirmed +"]"
                    activeCase.text = arrayList.get(0).active
                    recoveredCase.text = arrayList.get(0).recovered + "\n" + "[+" + arrayList.get(0).deltarecovered+"]"
                    deceasedCase.text = arrayList.get(0).deaths + "\n" + "[+" + arrayList.get(0).deltadeaths+"]"
                    lastUpdatedime.text = "LAST UPDATED ON: "+ arrayList.get(0).lastupdatedtime + " IST"
                    prepareAdapter()
                }
            })
    }
}