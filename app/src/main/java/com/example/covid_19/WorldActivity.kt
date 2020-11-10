package com.example.covid_19

import NetworkMonitor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_world.*
import org.json.JSONArray
import org.json.JSONObject

class WorldActivity : AppCompatActivity() {

    var arrayListWorld = arrayListOf<JSONObject>()
    lateinit var worldList : RecyclerView
    lateinit var refreshLayout5 : SwipeRefreshLayout
    lateinit var searchText : SearchView
    lateinit var worldListAdapter : WorldListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        searchText = findViewById(R.id.search_text1)
        worldList = findViewById(R.id.worldList1)
        refreshLayout5 = findViewById(R.id.refreshLayoutworld)
        refreshLayout5.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))

        searchText.imeOptions = EditorInfo.IME_ACTION_DONE
        searchText.queryHint = "Search by country name"

        if (!NetworkMonitor(Utils.activity).isConnected) {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
            Utils.activity.showPopup()
        }
        else
            getCurrenData()

        refreshLayout5.setOnRefreshListener {
            if (!NetworkMonitor(Utils.activity).isConnected)
            {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
                refreshLayout5.isRefreshing = false
            }
            else
            {
                getCurrenData()
                refreshLayout5.isRefreshing = false
            }
        }

        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                worldListAdapter.filter.filter(newText)
                return false
            }
        })
    }


    private fun getCurrenData()
    {
        loaderLayoutWorld.visibility = View.VISIBLE
        AndroidNetworking.get("https://coronavirus-19-api.herokuapp.com/countries")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray)
                {
                    arrayListWorld.clear()
                    loaderLayoutWorld.visibility = View.GONE
                    for (i in 0 until response.length())
                    {
                        arrayListWorld.add(response.getJSONObject(i))
                    }
                    prepareAdapter()
                }

                override fun onError(error: ANError) {
                    loaderLayoutWorld.visibility = View.GONE
                }
            })
    }

    private fun prepareAdapter()
    {
        worldListAdapter = WorldListAdapter(arrayListWorld)
        worldList.adapter = worldListAdapter
        worldListAdapter.notifyDataSetChanged()
        worldList.setHasFixedSize(true)
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