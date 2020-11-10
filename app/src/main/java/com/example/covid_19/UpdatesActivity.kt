package com.example.covid_19

import NetworkMonitor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import org.json.JSONArray
import org.json.JSONObject

class UpdatesActivity : AppCompatActivity() {

    lateinit var updateListAdapter: UpdateListAdapter
    lateinit var updateList: RecyclerView
    lateinit var refreshLayout : SwipeRefreshLayout
    var arrayList = arrayListOf<JSONObject>()
    lateinit var loaderPopup : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updates)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        updateList = findViewById(R.id.updateList)
        refreshLayout = findViewById(R.id.refreshLayout4)
        loaderPopup = findViewById(R.id.loaderPopup)
        refreshLayout.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))

        if (!NetworkMonitor(Utils.activity).isConnected) {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
            Utils.activity.showPopup()
        } else
            getCurrenData()

        refreshLayout.setOnRefreshListener {
            if (!NetworkMonitor(Utils.activity).isConnected)
            {
                refreshLayout.isRefreshing = false
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
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
        loaderPopup.visibility = View.VISIBLE
        AndroidNetworking.get(BuildConfig.BASE_URL + "updatelog/log.json")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener
            {
                override fun onResponse(response: JSONArray)
                {
                    loaderPopup.visibility = View.GONE
                    arrayList.clear()
                    for (i in response.length()-1 downTo 0)
                    {
                        arrayList.add(response.getJSONObject(i))
                    }
                    prepareAdapter()
                }

                override fun onError(error: ANError)
                {
                    loaderPopup.visibility = View.GONE
                    Toast.makeText(Utils.activity, "Could not get the current updates!", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun prepareAdapter() {
        updateList.apply {
            updateListAdapter = UpdateListAdapter(arrayList)
            setHasFixedSize(true)
            adapter = updateListAdapter
            updateListAdapter.refreshList(arrayList)
        }
    }
}
