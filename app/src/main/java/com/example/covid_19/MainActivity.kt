package com.example.covid_19

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    var isDoubleBackPressed = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        if (supportFragmentManager.findFragmentById(R.id.main_container) == null)
        {
           loadFragment(HomeFragment(this))
        }

        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //loadFragment(HomeFragment(this))

        bottom_nav_view.setOnNavigationItemSelectedListener { when(it.itemId)
        {
            R.id.navigation_home ->
            {
                isDoubleBackPressed = true
                loadFragment(HomeFragment(this))
                return@setOnNavigationItemSelectedListener true
            }

            R.id.navigation_dashboard ->
            {
                isDoubleBackPressed = false
                loadFragment(NotificationFragment(this))
                return@setOnNavigationItemSelectedListener true
            }

            R.id.navigation_district->
            {
                isDoubleBackPressed = false
                loadFragment(DistrictFragment(this))
                return@setOnNavigationItemSelectedListener true
            }

            R.id.navigation_notification->
            {
                isDoubleBackPressed = false
                loadFragment(DashboardFragment(this))
                return@setOnNavigationItemSelectedListener true
            }

            R.id.navigation_about ->
            {
                isDoubleBackPressed = false
                loadFragment(AboutFragment(this))
                return@setOnNavigationItemSelectedListener true
            }
        }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun loadFragment(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.commit()
    }

    override fun onBackPressed()
    {
        if (isDoubleBackPressed)
        {
            super.onBackPressed()
        }
        else
        {
            loadFragment(HomeFragment(this))
            bottom_nav_view.menu.findItem(R.id.navigation_home).setChecked(true)
            Toast.makeText(applicationContext, "Press BACK button again to exit!", Toast.LENGTH_SHORT).show()
            isDoubleBackPressed = true
        }
    }

    fun showLoader()
    {
        loaderLayout.visibility = View.VISIBLE
    }

    fun hideLoader()
    {
        loaderLayout.visibility = View.GONE
    }

//    private fun sendNotification()
//    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            var notificationChannel = NotificationChannel("Notification_123", "MyNotification", NotificationManager.IMPORTANCE_HIGH)
//            var notificationManager = getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//        FirebaseMessaging.getInstance().subscribeToTopic("general")
//            .addOnCompleteListener {
//                if (it.isSuccessful)
//                    Toast.makeText(applicationContext, "New Notification Arrived Now!", Toast.LENGTH_SHORT).show()
//            }
//    }

//    override fun onRestart()
//    {
//        super.onRestart()
//        Log.i("KP", "onRestart")
//    }
//
//    override fun onResumeFragments()
//    {
//        Log.i("KP", "onResume")
//    }
//
//    override fun onStart()
//    {
//        Log.i("KP", "onStart")
//        super.onStart()
//    }

    override fun onResume() {
        Log.i("KP", "onResume")
        super.onResume()
    }

    override fun onStop() {
        Log.i("KP", "onStop")
        super.onStop()
    }
}