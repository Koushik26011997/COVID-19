package com.example.covid_19

import NetworkMonitor
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.example.covid_19.states_Apis.CasesTimeSeriesItem
import com.example.covid_19.states_Apis.ResponseTotalCases
import com.example.covid_19.states_Apis.StatewiseItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.snackbar.Snackbar
import com.jacksonandroidnetworking.JacksonParserFactory
import com.rxandroidnetworking.RxAndroidNetworking
import com.tuyenmonkey.mkloader.model.Line
import kotlinx.android.synthetic.main.dashboardfragment.*
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.LineChartData
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment(): Fragment()
{
    companion object
    {
        @JvmStatic
        fun newInstance() : DashboardFragment
        {
            return DashboardFragment()
        }
    }
    val simpleDateFormat1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    val simpleDateFormat2 = SimpleDateFormat("dd MMM, hh:mm a")
    val arrayListPie = arrayListOf<StatewiseItem>()
    var  pieEntry = arrayListOf<PieEntry>()
    lateinit var pieChart : PieChart
    lateinit var confirmChart : BarChart
    val arrayListLine = arrayListOf<CasesTimeSeriesItem>()
    lateinit var refreshLayout : SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboardfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        AndroidNetworking.initialize(activity!!.applicationContext)
        AndroidNetworking.setParserFactory(JacksonParserFactory())

        refreshLayout = view.findViewById(R.id.refreshLayout3)
        refreshLayout.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))

        pieChart = view.findViewById(R.id.pieChart)
       // confirmChart = view.findViewById(R.id.barChart)

        if (!NetworkMonitor(Utils.activity).isConnected)
        {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_LONG).setAction("SETTINGS", View.OnClickListener {
                startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
            }).show()
            Utils.activity.showPopup()
        }
        else
            getCurrenData()

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
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun getCurrenData()
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
                }

                override fun onNext(response: ResponseTotalCases)
                {
                    arrayListPie.clear()
                    arrayListPie.addAll(response.statewise)

                    arrayListLine.clear()
                    arrayListLine.addAll(response.casesTimeSeries)

                    pieEntry.clear()

                    pieEntry.add(PieEntry(arrayListPie.get(0).confirmed.toFloat(), "Confirmed"))
                    pieEntry.add(PieEntry(arrayListPie.get(0).active.toFloat(), "Active"))
                    pieEntry.add(PieEntry(arrayListPie.get(0).recovered.toFloat(), "Recovered"))
                    pieEntry.add(PieEntry(arrayListPie.get(0).deaths.toFloat(), "Death"))

//                    confirmedCase.text = arrayListPie.get(0).confirmed + "\n" + "[+" + arrayListPie.get(0).deltaconfirmed +"]"
//                    activeCase.text = arrayListPie.get(0).active
//                    recoveredCase.text = arrayListPie.get(0).recovered + "\n" + "[+" + arrayListPie.get(0).deltarecovered+"]"
//                    deceasedCase.text = arrayListPie.get(0).deaths + "\n" + "[+" + arrayListPie.get(0).deltadeaths+"]"

                    valueAnimator()

                    var updateTime = SimpleDateFormat("hh:mm").format(simpleDateFormat1.parse(arrayListPie.get(0).lastupdatedtime).time)

                    var currentTime = SimpleDateFormat("hh:mm").format(Date().time)

                    val format = SimpleDateFormat("hh:mm")
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

                    lastUpdatedime.text = "UPDATED ON "+ simpleDateFormat2.format(simpleDateFormat1.parse(arrayListPie.get(0).lastupdatedtime)) + text

                    preparePieChart()
                   // prepareConfirmedLineChart()
                }
            })
    }

    private fun preparePieChart()
    {
        pieChart.animateXY(1800, 1800)
        var pieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))
        var pieData = PieData(pieDataSet)
        pieChart.data = pieData
        var description = Description()
        description.text = "COVID-19 Pie Chart"
        pieChart.description = description
        pieChart.invalidate()
    }

    private fun prepareConfirmedLineChart()
    {
        confirmChart.setDrawBarShadow(false)
        confirmChart.setDrawValueAboveBar(true)
        confirmChart.setMaxVisibleValueCount(5000)
        confirmChart.setPinchZoom(false)
        confirmChart.setDrawGridBackground(true)

       val entry = arrayListOf<BarEntry>()
        for (i in 0..arrayListPie.size)
        {
            entry.add(BarEntry(i.toFloat(), arrayListPie.get(i).confirmed.toFloat()))
        }
        val barDataSet = BarDataSet(entry, "Data")
        barDataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))

        val barData = BarData(barDataSet)
        barData.barWidth = 0.3f
        confirmChart.data = barData
    }

    override fun onStart() {
        super.onStart()
        preparePieChart()
    }

    fun valueAnimator()
    {
        // Confirm
        var valueAnimatorConfirm = ValueAnimator.ofInt(0, arrayListPie.get(0).confirmed.toInt())
        var valueAnimatordelteConfirm = ValueAnimator.ofInt(0, arrayListPie.get(0).deltaconfirmed.toInt())
        valueAnimatorConfirm.setDuration(1500)
        valueAnimatordelteConfirm.setDuration(1400)
        valueAnimatorConfirm.addUpdateListener {
            if (confirmedCase != null)
                confirmedCase.text = valueAnimatorConfirm.getAnimatedValue().toString() + "\n[+" + valueAnimatordelteConfirm.getAnimatedValue().toString() + "]"
        }
        valueAnimatorConfirm.start()
        valueAnimatordelteConfirm.start()

        // Recover
        var valueAnimatorRecover = ValueAnimator.ofInt(0, arrayListPie.get(0).recovered.toInt())
        var valueAnimatordeltaRecover = ValueAnimator.ofInt(0, arrayListPie.get(0).deltarecovered.toInt())
        valueAnimatorRecover.setDuration(1500)
        valueAnimatordeltaRecover.setDuration(1400)
        valueAnimatorRecover.addUpdateListener {
            if (recoveredCase != null)
                recoveredCase.text = valueAnimatorRecover.getAnimatedValue().toString() + "\n[+" + valueAnimatordeltaRecover.getAnimatedValue().toString() + "]"
        }
        valueAnimatorRecover.start()
        valueAnimatordeltaRecover.start()

        // Death
        var valueAnimatorDeath = ValueAnimator.ofInt(0, arrayListPie.get(0).deaths.toInt())
        var valueAnimatordeltaDeath = ValueAnimator.ofInt(0, arrayListPie.get(0).deltadeaths.toInt())
        valueAnimatorDeath.setDuration(1500)
        valueAnimatordeltaDeath.setDuration(1400)
        valueAnimatorDeath.addUpdateListener {
            if (deceasedCase != null)
                deceasedCase.text = valueAnimatorDeath.getAnimatedValue().toString() + "\n[+" + valueAnimatordeltaDeath.getAnimatedValue().toString() + "]"
        }
        valueAnimatorDeath.start()
        valueAnimatordeltaDeath.start()

        // Active
        var valueAnimatorActive = ValueAnimator.ofInt(0, arrayListPie.get(0).active.toInt())
        valueAnimatorActive.setDuration(1500)
        valueAnimatorActive.addUpdateListener {
            if (activeCase != null)
                activeCase.text = valueAnimatorActive.getAnimatedValue().toString()
        }
        valueAnimatorActive.start()
    }
}