package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import android.os.Build
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

class NotificationFragment() : Fragment()
{
    companion object
    {
        @JvmStatic
        fun newInstance() : NotificationFragment
        {
            return NotificationFragment()
        }
    }

    var isSorted = false
    var isConfirmSorted = false
    var isRecoverSorted = false
    var isDeathSorted = false

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

        if (!NetworkMonitor(Utils.activity).isConnected)
        {
            confirmedTotal.text = "0"
            recoveredTotal.text = "0"
            deceasedTotal.text = "0"

            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                }).show()
            Utils.activity.showPopup()
        }
        else
        {
            getCurrenData()
        }

        dateChange.setOnClickListener(){
            if (isSorted)
            {
                sortdescArrayList()
                isSorted = false
            }
            else
            {
                sortascArrayList()
                isSorted = true
            }
            prepareAdapter()
        }

        confirmedCaseChange.setOnClickListener(){
            if (isConfirmSorted)
            {
                sortdescConfirmArrayList()
                isConfirmSorted = false
            }
            else
            {
                sortascConfirmArrayList()
                isConfirmSorted = true
            }
            prepareAdapter()
        }

        recoveredCaseChange.setOnClickListener(){
            if (isRecoverSorted)
            {
                sortdescRecoverArrayList()
                isRecoverSorted = false
            }
            else
            {
                sortascRecoverArrayList()
                isRecoverSorted = true
            }
            prepareAdapter()
        }

        deceasedCaseChange.setOnClickListener(){
            if (isDeathSorted)
            {
                sortdescDeathArrayList()
                isDeathSorted = false
            }
            else
            {
                sortascDeathArrayList()
                isDeathSorted = true
            }
            prepareAdapter()
        }
    }

    private fun getCurrenData()
    {
        Utils.activity.showLoader()
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
                    Utils.activity.hideLoader()
                }

                override fun onError(e: Throwable)
                {
                    Utils.activity.hideLoader()
                    //Toast.makeText(Utils.activity.applicationContext, "Could not get the current data!", Toast.LENGTH_SHORT).show()
                }

                override fun onNext(response: ResponseDailyChanges)
                {
                    arrayList.clear()
                    arrayListTotal.clear()

                    arrayListTotal.addAll(response.statewise)
                    arrayList.addAll(response.casesTimeSeries)

                    sortdescArrayList()
                    prepareAdapter()

                    // Set Today's Values..
                    todayConfirmed.text = "[+" + arrayListTotal.get(0).deltaconfirmed +"]"
                    todayRecovered.text = "[+" + arrayListTotal.get(0).deltarecovered +"]"
                    todayDeath.text = "[+" + arrayListTotal.get(0).deltadeaths+"]"

                    // Set Total Values...
                    totalTxt.text = "TOTAL (" + arrayList.size.toString() + " DAYS+)*"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        totalTxt.tooltipText = "This is including today"
                    }
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

    private fun sortdescArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id
            var dateFormat = SimpleDateFormat("dd MMMM")
            var date1 = dateFormat.parse(o1.date)
            var date2 = dateFormat.parse(o2.date)
            return@Comparator date2.compareTo(date1)
        })
    }

    private fun sortascArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id
            var dateFormat = SimpleDateFormat("dd MMMM")
            var date1 = dateFormat.parse(o1.date)
            var date2 = dateFormat.parse(o2.date)
            return@Comparator date1.compareTo(date2)
        })
    }

    private fun sortdescConfirmArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id

            return@Comparator ((o2.dailyconfirmed.toInt()).compareTo(o1.dailyconfirmed.toInt()))
        })
    }

    private fun sortascConfirmArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id

            return@Comparator ((o1.dailyconfirmed.toInt()).compareTo(o2.dailyconfirmed.toInt()))
        })
    }

    private fun sortdescRecoverArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id

            return@Comparator ((o2.dailyrecovered.toInt()).compareTo(o1.dailyrecovered.toInt()))
        })
    }

    private fun sortascRecoverArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id

            return@Comparator ((o1.dailyrecovered.toInt()).compareTo(o2.dailyrecovered.toInt()))
        })
    }

    private fun sortdescDeathArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id

            return@Comparator ((o2.dailydeceased.toInt()).compareTo(o1.dailydeceased.toInt()))
        })
    }

    private fun sortascDeathArrayList()
    {
        arrayList.sortWith(Comparator { o1, o2 -> id

            return@Comparator ((o1.dailydeceased.toInt()).compareTo(o2.dailydeceased.toInt()))
        })
    }

    override fun onStart()
    {
        super.onStart()
        prepareAdapter()
    }
}