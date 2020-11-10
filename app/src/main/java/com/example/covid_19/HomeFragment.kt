package com.example.covid_19

import NetworkMonitor
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.example.covid_19.states_Apis.*
import com.google.android.material.snackbar.Snackbar
import com.jacksonandroidnetworking.JacksonParserFactory
import com.rxandroidnetworking.RxAndroidNetworking
import kotlinx.android.synthetic.main.homefragment.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment()
{
    companion object
    {
        @JvmStatic
        fun newInstance() : HomeFragment
        {
            return HomeFragment()
        }
    }

    lateinit var statesList : RecyclerView
    lateinit var testCount : TextView
    lateinit var lastUpdatedime : TextView
    val arrayList = arrayListOf<StatewiseItem>()
    val testedCount = arrayListOf<TestedItem>()
    val simpleDateFormat1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    val simpleDateFormat2 = SimpleDateFormat("dd MMM, hh:mm a")
    lateinit var refreshLayout : SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.homefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        //Utils.activity.showUpdates()
        AndroidNetworking.initialize(activity!!.applicationContext)
        AndroidNetworking.setParserFactory(JacksonParserFactory())

        statesList =  view.findViewById(R.id.statesList)
        testCount = view.findViewById(R.id.testCount)
        lastUpdatedime = view.findViewById(R.id.lastUpdatedime)
        refreshLayout = view.findViewById(R.id.refreshLayout)
        refreshLayout.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))

        if (!NetworkMonitor(Utils.activity).isConnected)
        {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                }).show()
            arrayList.clear()
            testedCount.clear()
        }
        else
        {
            Utils.activity.showLoader()
            getCurrenData()
            getZonesData()
        }

        refreshLayout.setOnRefreshListener {
            if (!NetworkMonitor(Utils.activity).isConnected)
            {
                Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_LONG)
                    .setAction("SETTINGS", View.OnClickListener {
                        startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                    }).show()
                refreshLayout.isRefreshing = false
            }
            else
            {
                getCurrenData()
                getZonesData()
                refreshLayout.isRefreshing = false
            }
        }

        prepareAdapter()
    }

    private fun prepareAdapter()
    {
        var statesListAdapter = StatesListAdapter(arrayList)
        statesList.setHasFixedSize(true)
        statesList.adapter = statesListAdapter
        statesListAdapter.refreshList(arrayList)
    }

    fun getCurrenData()
    {
        Utils.activity.showLoader()
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
                    Utils.activity.hideLoader()
                }

                override fun onError(e: Throwable)
                {
                    Utils.activity.hideLoader()
                    Toast.makeText(Utils.activity.applicationContext, "Could not get the current data!", Toast.LENGTH_SHORT).show()
                }

                override fun onNext(response: ResponseTotalCases)
                {
                    arrayList.clear()
                    testedCount.clear()
                    arrayList.addAll(response.statewise)
                    testedCount.addAll(response.tested)

                    valueAnimator()

//                  confirmCase.text = NumberFormat.getInstance().format(arrayList.get(0).confirmed.toInt()) + "\n" + "[+" + arrayList.get(0).deltaconfirmed +"]"
//                  activeCase.text = arrayList.get(0).active
//                  recoveredCase.text = arrayList.get(0).recovered + "\n" + "[+" + arrayList.get(0).deltarecovered+"]"
//                  deceasedCase.text = arrayList.get(0).deaths + "\n" + "[+" + arrayList.get(0).deltadeaths+"]"
//
                    if (!testedCount.get(testedCount.size-1).totalsamplestested.equals(""))
                    {
                        var count = NumberFormat.getInstance().format(testedCount.get(testedCount.size-1).totalsamplestested.toInt())
                        testCount.text = "TESTED " + count + " ON " + simpleDateFormat2.format(simpleDateFormat1.parse(testedCount.get(testedCount.size-1).updatetimestamp))
                    }
                    else
                        testCount.text = "COUNTS OF TESTED PEOPLE WILL BE UPDATED SOON!"

                    val format = SimpleDateFormat("hh:mm")


                    var updateTime = SimpleDateFormat("hh:mm").format(simpleDateFormat1.parse(arrayList.get(0).lastupdatedtime).time)

                    var currentTime = SimpleDateFormat("hh:mm").format(Date().time)

                    val date1 = format.parse(updateTime)
                    val date2 = format.parse(currentTime)
                    val diffHours  = ((date2.time - date1.time) / (60 * 60 * 1000) % 24)
                    val differenceMin = ((date2.time - date1.time)/ (60 * 1000) % 60)

                    var text = ""

                    if (diffHours.compareTo(1) == 0)
                        text = " (" + diffHours + " hour ago)"

                    if (diffHours > 1)
                        text = " (" + diffHours + " hours ago)"

                    if (differenceMin.compareTo(1) == 0)
                        text = " (" + differenceMin + " min ago)"

                    if (differenceMin > 1)
                        text = " (" + differenceMin + " mins ago)"

                    if ((diffHours.compareTo(1) == 0) and (differenceMin.compareTo(1) == 0))
                        text = " (" + diffHours + " hour " + differenceMin + " min ago)"

                    if ((diffHours.compareTo(1) == 0) and (differenceMin > 1))
                        text = " (" + diffHours + " hour " + differenceMin + " mins ago)"

                    if ((diffHours > 1) and (differenceMin.compareTo(1) == 0))
                        text = " (" + diffHours + " hours " + differenceMin + " min ago)"

                    if ((diffHours > 1) and (differenceMin > 1))
                        text = " (" + diffHours + " hours " + differenceMin + " mins ago)"


                    lastUpdatedime.text = "UPDATED ON "+ simpleDateFormat2.format(simpleDateFormat1.parse(arrayList.get(0).lastupdatedtime)) + text

                    prepareAdapter()
                }
            })
    }

    fun valueAnimator()
    {
        // Confirm
        var valueAnimatorConfirm = ValueAnimator.ofInt(0, arrayList.get(0).confirmed.toInt())
        var valueAnimatordelteConfirm = ValueAnimator.ofInt(0, arrayList.get(0).deltaconfirmed.toInt())
        valueAnimatorConfirm.setDuration(1500)
        valueAnimatordelteConfirm.setDuration(1400)
        valueAnimatorConfirm.addUpdateListener {
            if (confirmCase != null)
                confirmCase.text = valueAnimatorConfirm.getAnimatedValue().toString() + "\n[+" + valueAnimatordelteConfirm.getAnimatedValue().toString() + "]"
        }
        valueAnimatorConfirm.start()
        valueAnimatordelteConfirm.start()

        // Recover
        var valueAnimatorRecover = ValueAnimator.ofInt(0, arrayList.get(0).recovered.toInt())
        var valueAnimatordeltaRecover = ValueAnimator.ofInt(0, arrayList.get(0).deltarecovered.toInt())
        valueAnimatorRecover.setDuration(1500)
        valueAnimatordeltaRecover.setDuration(1400)
        valueAnimatorRecover.addUpdateListener {
            if (recoveredCase != null)
                recoveredCase.text = valueAnimatorRecover.getAnimatedValue().toString() + "\n[+" + valueAnimatordeltaRecover.getAnimatedValue().toString() + "]"
        }
        valueAnimatorRecover.start()
        valueAnimatordeltaRecover.start()

        // Death
        var valueAnimatorDeath = ValueAnimator.ofInt(0, arrayList.get(0).deaths.toInt())
        var valueAnimatordeltaDeath = ValueAnimator.ofInt(0, arrayList.get(0).deltadeaths.toInt())
        valueAnimatorDeath.setDuration(1500)
        valueAnimatordeltaDeath.setDuration(1400)
        valueAnimatorDeath.addUpdateListener {
            if (deceasedCase != null)
                deceasedCase.text = valueAnimatorDeath.getAnimatedValue().toString() + "\n[+" + valueAnimatordeltaDeath.getAnimatedValue().toString() + "]"
        }
        valueAnimatorDeath.start()
        valueAnimatordeltaDeath.start()

        // Active
        var valueAnimatorActive = ValueAnimator.ofInt(0, arrayList.get(0).active.toInt())
        valueAnimatorActive.setDuration(1500)
        valueAnimatorActive.addUpdateListener {
            if (activeCase != null)
                activeCase.text = valueAnimatorActive.getAnimatedValue().toString()
        }
        valueAnimatorActive.start()
    }

    override fun onResume()
    {
        super.onResume()
        Log.i("KP","getCurrentData")
        getCurrenData()
    }

    override fun onStop()
    {
        super.onStop()
        Log.i("KP","clearData")
        arrayList.clear()
        testedCount.clear()
    }

    // Zones data...
    private fun getZonesData()
    {
        Utils.activity.showLoader()
        var rxAnrRequest= RxAndroidNetworking.get(BuildConfig.BASE_URL + "zones.json")
            .setPriority(Priority.HIGH)
            .build()
            .getObjectObservable(ResponseZones::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseZones>
            {
                override fun onCompleted()
                {
                    Utils.activity.hideLoader()
                }

                override fun onError(e: Throwable)
                {
                    Utils.activity.hideLoader()
                    Utils.zonesArrayList.clear()
                }

                override fun onNext(response: ResponseZones)
                {
                    Utils.zonesArrayList.clear()
                    if (response != null)
                        Utils.zonesArrayList.addAll(response.zones)
                }
            })
    }
}