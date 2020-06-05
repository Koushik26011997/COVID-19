package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.aboutfragment.*
import kotlinx.android.synthetic.main.layout_bony.*
import okhttp3.internal.Util
import org.json.JSONArray

class AboutFragment() : Fragment()
{
    companion object
    {
        @JvmStatic
        fun newInstance() : AboutFragment
        {
            return AboutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.aboutfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        Utils.activity.update.visibility = View.GONE
        if (!NetworkMonitor(Utils.activity).isConnected)
        {
            Snackbar.make(view, "No Internet Connection!", Snackbar.LENGTH_SHORT)
                .setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                }).show()
        }
        info.setOnRippleCompleteListener {
            try {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://telegra.ph/Covid-19-Sources-03-19"))
                startActivity(Intent.createChooser(intent, "Opens With"))
            }
            catch (exp : Exception)
            {
                Snackbar.make(view, "Could not open the web browser!", Snackbar.LENGTH_SHORT).setAction("SETTINGS", View.OnClickListener {
                    startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
                }).show()
            }
        }
    }
}