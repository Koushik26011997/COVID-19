package com.example.covid_19

import NetworkMonitor
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.covid_19.states_Apis.TestedItem
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
    val testedCount = arrayListOf<TestedItem>()

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

        val animation = AnimationUtils.loadAnimation(activity, R.anim.rotate)

        if (!NetworkMonitor(activity).isConnected)
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT).setAction("SETTINGS", View.OnClickListener {
                startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
            }).show()
        else
        {
            getCurrenData()
        }

        prepareAdapter()

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
                    testedCount.clear()
                    arrayList.addAll(response.statewise)
                    testedCount.addAll(response.tested)

                    valueAnimator()

                    //confirmedCase.text = arrayList.get(0).confirmed + "\n" + "[+" + arrayList.get(0).deltaconfirmed +"]"
                    //activeCase.text = arrayList.get(0).active
                    //recoveredCase.text = arrayList.get(0).recovered + "\n" + "[+" + arrayList.get(0).deltarecovered+"]"
                    //deceasedCase.text = arrayList.get(0).deaths + "\n" + "[+" + arrayList.get(0).deltadeaths+"]"

                    testCount.text = "TESTED " + testedCount.get(testedCount.size-1).totalindividualstested + " ON: " +testedCount.get(testedCount.size-1).updatetimestamp + " IST"
                    lastUpdatedime.text = "LAST UPDATED ON: "+ arrayList.get(0).lastupdatedtime + " IST"
                    prepareAdapter()
                }
            })
    }

    private fun valueAnimator()
    {
        // Confirm
        var valueAnimatorConfirm = ValueAnimator.ofInt(0, arrayList.get(0).confirmed.toInt())
        var valueAnimatordelteConfirm = ValueAnimator.ofInt(0, arrayList.get(0).deltaconfirmed.toInt())
        valueAnimatorConfirm.setDuration(1500)
        valueAnimatordelteConfirm.setDuration(1400)
        valueAnimatorConfirm.addUpdateListener {
            confirmedCase.text = valueAnimatorConfirm.getAnimatedValue().toString() + "\n[+" + valueAnimatordelteConfirm.getAnimatedValue().toString() + "]"
        }
        valueAnimatorConfirm.start()
        valueAnimatordelteConfirm.start()

        // Recover
        var valueAnimatorRecover = ValueAnimator.ofInt(0, arrayList.get(0).recovered.toInt())
        var valueAnimatordeltaRecover = ValueAnimator.ofInt(0, arrayList.get(0).deltarecovered.toInt())
        valueAnimatorRecover.setDuration(1500)
        valueAnimatordeltaRecover.setDuration(1400)
        valueAnimatorRecover.addUpdateListener {
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
            deceasedCase.text = valueAnimatorDeath.getAnimatedValue().toString() + "\n[+" + valueAnimatordeltaDeath.getAnimatedValue().toString() + "]"
        }
        valueAnimatorDeath.start()
        valueAnimatordeltaDeath.start()

        // Active
        var valueAnimatorActive = ValueAnimator.ofInt(0, arrayList.get(0).active.toInt())
        valueAnimatorActive.setDuration(1500)
        valueAnimatorActive.addUpdateListener {
            activeCase.text = valueAnimatorActive.getAnimatedValue().toString()
        }
        valueAnimatorActive.start()
    }
}