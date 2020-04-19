package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_bony.*
import org.json.JSONArray

class DistrictFragment(mainActivity: MainActivity) : Fragment()
{
    val activity = mainActivity

    val arrayListState = arrayListOf<String>()

    val arrayListDistrict = arrayListOf<JSONArray>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bony, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        if (!NetworkMonitor(activity).isConnected)
        {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                }).show()
        }

       getCurrenData()
    }

    private fun getCurrenData()
    {
        activity.showLoader()
        AndroidNetworking.get(BuildConfig.BASE_URL + "v2/state_district_wise.json")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener
            {
                override fun onResponse(response: JSONArray)
                {
                    activity.hideLoader()
                    for (i in 0 until response.length())
                    {
                        arrayListState.add(response.getJSONObject(i).getString("state"))
                        arrayListDistrict.add(response.getJSONObject(i).getJSONArray("districtData"))
                    }
                    prepareAdapter()
                }

                override fun onError(error: ANError)
                {
                    activity.hideLoader()
                }
            })
    }

    private fun prepareAdapter()
    {
        districtList.apply {
            val statesDistrictWiseListAdapter = StatesDistrictWiseListAdapter(arrayListState, arrayListDistrict)
            adapter = statesDistrictWiseListAdapter
            setHasFixedSize(true)
        }
    }
}