package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.example.covid_19.states_Apis.CasesTimeSeriesItem
import com.example.covid_19.states_Apis.ResponseDailyChanges
import com.example.covid_19.states_Apis.StatewiseItem
import com.google.android.material.snackbar.Snackbar
import com.jacksonandroidnetworking.JacksonParserFactory
import com.rxandroidnetworking.RxAndroidNetworking
import kotlinx.android.synthetic.main.notificationfragment.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class NotificationFragment(mainActivity: MainActivity) : Fragment()
{
    val activity = mainActivity
    val arrayList = arrayListOf<CasesTimeSeriesItem>()
    val arrayListTotal = arrayListOf<StatewiseItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notificationfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        AndroidNetworking.initialize(activity!!.applicationContext)
        AndroidNetworking.setParserFactory(JacksonParserFactory())

        if (!NetworkMonitor(activity).isConnected)
        {
            confirmedTotal.text = "0"
            recoveredTotal.text = "0"
            deceasedTotal.text = "0"

            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                }).show()
        }
        else
        {
            getCurrenData()
        }
    }

    private fun getCurrenData()
    {
        activity.showLoader()
        var rxAnrRequest= RxAndroidNetworking.get(BuildConfig.BASE_URL + "data.json")
            .setPriority(Priority.HIGH)
            .build()
            .getObjectObservable(ResponseDailyChanges::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseDailyChanges>
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

                override fun onNext(response: ResponseDailyChanges)
                {
                    arrayList.clear()
                    arrayListTotal.clear()

                    arrayListTotal.addAll(response.statewise)
                    arrayList.addAll(response.casesTimeSeries)

                    sortArrayList()

                    prepareAdapter()

                    // Set Today's Values..
                    todayConfirmed.text = "[+" + arrayListTotal.get(0).deltaconfirmed +"]"
                    todayRecovered.text = "[+" + arrayListTotal.get(0).deltarecovered +"]"
                    todayDeath.text = "[+" + arrayListTotal.get(0).deltadeaths+"]"

                    // Set Total Values...
                    confirmedTotal.text = arrayListTotal.get(0).confirmed
                    recoveredTotal.text = arrayListTotal.get(0).recovered
                    deceasedTotal.text = arrayListTotal.get(0).deaths
                }
            })
    }

    private fun prepareAdapter()
    {
        var statesListAdapter = StatesDateWiseListAdapter(arrayList)
        stateDayWiseList.setHasFixedSize(true)
        stateDayWiseList.adapter = statesListAdapter
        statesListAdapter.refreshList(arrayList)
    }

    private fun sortArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id
            var dateFormat = SimpleDateFormat("dd MMMM")
            var date1 = dateFormat.parse(o1.date)
            var date2 = dateFormat.parse(o2.date)
            return@Comparator date2.compareTo(date1)
        })
    }
}