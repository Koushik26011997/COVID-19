package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_bony.*
import org.json.JSONArray

class DistrictFragment() : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(): DistrictFragment {
            return DistrictFragment()
        }
    }

    val arrayListState = arrayListOf<String>()

    val arrayListDistrict = arrayListOf<JSONArray>()

    lateinit var districtList: RecyclerView

    lateinit var refreshLayout : SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bony, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        districtList = view.findViewById(R.id.districtList)
        refreshLayout = view.findViewById(R.id.refreshLayout2)
        refreshLayout.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))

        if (!NetworkMonitor(Utils.activity).isConnected) {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
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

    private fun getCurrenData() {
        Utils.activity.showLoader()
        AndroidNetworking.get(BuildConfig.BASE_URL + "v2/state_district_wise.json")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {

                    arrayListState.clear()
                    arrayListDistrict.clear()

                    Utils.activity.hideLoader()
                    for (i in 0 until response.length()) {
                        arrayListState.add(response.getJSONObject(i).getString("state"))
                        arrayListDistrict.add(
                            response.getJSONObject(i).getJSONArray("districtData")
                        )
                    }
                    prepareAdapter()
                }

                override fun onError(error: ANError) {
                    Utils.activity.hideLoader()
                }
            })
    }

    private fun prepareAdapter() {
        val statesDistrictWiseListAdapter =
            StatesDistrictWiseListAdapter(arrayListState, arrayListDistrict)
        districtList.adapter = statesDistrictWiseListAdapter
        statesDistrictWiseListAdapter.notifyDataSetChanged()
        districtList.setHasFixedSize(true)
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