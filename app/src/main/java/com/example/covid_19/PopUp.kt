package com.example.covid_19

import NetworkMonitor
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_dialog.*
import java.lang.reflect.Type

class PopUp(mainActivity: MainActivity) : DialogFragment()
{
    lateinit var customView : View
    var activity = mainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return customView
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
        customView = activity!!.layoutInflater.inflate(R.layout.popup_dialog,null)
        builder.setView(customView)
        return builder.create()
    }

    override fun onStart()
    {
        Log.i("LOL", "onStartPopUp")
        super.onStart()
        if(NetworkMonitor(activity).isConnected)
        {
            dismiss()
            //HomeFragment(activity).getCurrenData()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        tv_cancel_ripple_confirmation.setOnRippleCompleteListener {
            dismiss()
        }
        img_cross_confirmation.setOnClickListener(){
            dismiss()
        }
        tv_save_ripple_confirmation.setOnRippleCompleteListener {
            startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
        }
    }

    fun enableData()
    {
        var connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
        var commonClass = Class.forName(connectivityManager.javaClass.name)
        var connectivityManagerField = commonClass.getDeclaredField("mService")
        connectivityManagerField.isAccessible = true
        var connectivityManagers = connectivityManagerField.get(connectivityManager)
        var connectivityManagerClass = Class.forName(connectivityManagers.javaClass.name)
        var setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled")
        setMobileDataEnabledMethod.isAccessible = true
        setMobileDataEnabledMethod.invoke(connectivityManagers)
    }
}