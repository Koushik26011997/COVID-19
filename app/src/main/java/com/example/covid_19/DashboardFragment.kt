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
import androidx.fragment.app.Fragment
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

    val arrayListPie = arrayListOf<StatewiseItem>()
    var  pieEntry = arrayListOf<PieEntry>()
    lateinit var pieChart : PieChart
    lateinit var confirmChart : BarChart
    val arrayListLine = arrayListOf<CasesTimeSeriesItem>()

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

        pieChart = view.findViewById(R.id.pieChart)
       // confirmChart = view.findViewById(R.id.barChart)

        if (!NetworkMonitor(Utils.activity).isConnected)
        {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT).setAction("SETTINGS", View.OnClickListener {
                startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
            }).show()
            Utils.activity.showPopup()
        }
        else
            getCurrenData()
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

                    pieEntry.add(PieEntry(arrayListPie.get(0).confirmed.toFloat(), "Confirmed"))
                    pieEntry.add(PieEntry(arrayListPie.get(0).active.toFloat(), "Active"))
                    pieEntry.add(PieEntry(arrayListPie.get(0).recovered.toFloat(), "Recovered"))
                    pieEntry.add(PieEntry(arrayListPie.get(0).deaths.toFloat(), "Death"))

//                    confirmedCase.text = arrayListPie.get(0).confirmed + "\n" + "[+" + arrayListPie.get(0).deltaconfirmed +"]"
//                    activeCase.text = arrayListPie.get(0).active
//                    recoveredCase.text = arrayListPie.get(0).recovered + "\n" + "[+" + arrayListPie.get(0).deltarecovered+"]"
//                    deceasedCase.text = arrayListPie.get(0).deaths + "\n" + "[+" + arrayListPie.get(0).deltadeaths+"]"

                    valueAnimator()

                    lastUpdatedime.text = "LAST UPDATED ON: "+ arrayListPie.get(0).lastupdatedtime + " IST"

                    preparePieChart()
                   // prepareConfirmedLineChart()
                }
            })
    }

    private fun preparePieChart()
    {
        pieChart.animateXY(2000, 2000)
        var pieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.setColors(ColorTemplate.createColors(ColorTemplate.COLORFUL_COLORS))
        var pieData = PieData(pieDataSet)
        pieChart.data = pieData
        var description = Description()
        description.text = "COVID-19 Growth Chart"
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