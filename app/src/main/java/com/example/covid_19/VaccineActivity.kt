package com.example.covid_19

import NetworkMonitor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.androidnetworking.common.Priority
import com.example.covid_19.states_Apis.ResponseDailyChanges
import com.example.covid_19.states_Apis.TestedItem
import com.rxandroidnetworking.RxAndroidNetworking
import kotlinx.android.synthetic.main.activity_vaccine.*
import kotlinx.android.synthetic.main.notificationfragment.*
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.NumberFormat
import java.util.*

class VaccineActivity : AppCompatActivity()
{
    var vaccineList = arrayListOf<TestedItem>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccine)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        swipeVaccine.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))

        if (!NetworkMonitor(Utils.activity).isConnected) {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
            Utils.activity.showPopup()
        } else
            getCurrenData()

        swipeVaccine.setOnRefreshListener {
            if (!NetworkMonitor(Utils.activity).isConnected)
            {
                swipeVaccine.isRefreshing = false
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                getCurrenData()
                swipeVaccine.isRefreshing = false
            }
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
                    vaccineList.clear()

                    vaccineList.addAll(response.tested)

                    vaccineList.reverse()

                    prepareAdapter()
                }
            })
    }

    private fun prepareAdapter() {
        vaccineListing.apply {
            setHasFixedSize(true)
            adapter = VaccineListAdapter(vaccineList)
        }
    }
}