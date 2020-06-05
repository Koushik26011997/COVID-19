package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.aboutfragment.*
import kotlinx.android.synthetic.main.layout_bony.*
import kotlinx.android.synthetic.main.world_layout.*
import okhttp3.internal.Util
import org.json.JSONArray
import org.json.JSONObject

class WorldFragment() : Fragment()
{
    var arrayListWorld = arrayListOf<JSONObject>()
    lateinit var worldList : RecyclerView
    lateinit var refreshLayout5 : SwipeRefreshLayout
    lateinit var searchText : SearchView
    lateinit var worldListAdapter : WorldListAdapter

    companion object
    {
        @JvmStatic
        fun newInstance() : WorldFragment
        {
            return WorldFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.world_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        searchText = view.findViewById(R.id.search_text)
        worldList = view.findViewById(R.id.worldList)
        refreshLayout5 = view.findViewById(R.id.refreshLayout5)
        refreshLayout5.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))

        searchText.imeOptions = EditorInfo.IME_ACTION_DONE
        searchText.queryHint = "Search by country name"

        if (!NetworkMonitor(Utils.activity).isConnected) {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                }).show()
            Utils.activity.showPopup()
        }
        else
            getCurrenData()

        refreshLayout5.setOnRefreshListener {
            if (!NetworkMonitor(Utils.activity).isConnected)
            {
                Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_LONG)
                    .setAction("SETTINGS", View.OnClickListener {
                        startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                    }).show()
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
        Utils.activity.showLoader()
        AndroidNetworking.get("https://coronavirus-19-api.herokuapp.com/countries")
        .setPriority(Priority.HIGH)
        .build()
        .getAsJSONArray(object : JSONArrayRequestListener {
            override fun onResponse(response: JSONArray)
            {
                arrayListWorld.clear()
                Utils.activity.hideLoader()
                for (i in 0 until response.length())
                {
                    arrayListWorld.add(response.getJSONObject(i))
                }
                prepareAdapter()
            }

            override fun onError(error: ANError) {
                Utils.activity.hideLoader()
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