package com.example.covid_19

import NetworkMonitor
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.updates_popup_dialog.*
import org.json.JSONArray
import org.json.JSONObject

class UpdatesPopUp(mainActivity: MainActivity) : DialogFragment()
{
    lateinit var customView: View
    var activity = mainActivity
    lateinit var updateListAdapter: UpdateListAdapter
    lateinit var updateList: RecyclerView
    lateinit var refreshLayout : SwipeRefreshLayout
    var arrayList = arrayListOf<JSONObject>()
    lateinit var loaderPopup : RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return customView
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
        customView = activity!!.layoutInflater.inflate(R.layout.updates_popup_dialog, null)
        builder.setView(customView)
        return builder.create()
    }

    override fun onStart() {
        Log.i("LOL", "onStartPopUp")
        super.onStart()
        if (!NetworkMonitor(activity).isConnected) {
            dismiss()

        }
        var decorView = dialog?.window?.decorView

        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            decorView,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f)
        )
        scaleDown.duration = 1000
        scaleDown.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateList = view.findViewById(R.id.updateList)
        refreshLayout = view.findViewById(R.id.refreshLayout4)
        loaderPopup = view.findViewById(R.id.loaderPopup)
        refreshLayout.setColorSchemeColors(resources.getColor(R.color.red), resources.getColor(R.color.blue), resources.getColor(R.color.green), resources.getColor(R.color.grey))
        isCancelable = false
        img_cancel.setOnClickListener() {
            dismiss()
        }

        if (!NetworkMonitor(Utils.activity).isConnected) {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                }).show()
            Utils.activity.showPopup()
        } else
            getCurrenData()

        refreshLayout.setOnRefreshListener {
            if (!NetworkMonitor(Utils.activity).isConnected)
            {
                refreshLayout.isRefreshing = false
                Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                    .setAction("SETTINGS", View.OnClickListener {
                        startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                    }).show()
            }
            else
            {
                getCurrenData()
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun getCurrenData() {
        loaderPopup.visibility = View.VISIBLE
        AndroidNetworking.get(BuildConfig.BASE_URL + "updatelog/log.json")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
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
                    Utils.activity.hideLoader()
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