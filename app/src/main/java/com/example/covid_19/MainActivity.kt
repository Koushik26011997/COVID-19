package com.example.covid_19

import NetworkMonitor
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private var isDoubleBackPressed = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        loadFragment(HomeFragment.newInstance())
        Utils.activity = this

        if (!NetworkMonitor(this).isConnected)
        {
            showPopup()
        }

        bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    isDoubleBackPressed = true
                    loadFragment(HomeFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_dashboard -> {
                    isDoubleBackPressed = false
                    loadFragment(NotificationFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_district -> {
                    isDoubleBackPressed = false
                    loadFragment(DistrictFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_notification -> {
                    isDoubleBackPressed = false
                    loadFragment(DashboardFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_about -> {
                    isDoubleBackPressed = false
                    loadFragment(AboutFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (isDoubleBackPressed) {
            super.onBackPressed()
        } else {
            loadFragment(HomeFragment.newInstance())
            bottom_nav_view.menu.findItem(R.id.navigation_home).isChecked = true
            Toast.makeText(
                applicationContext,
                "Press BACK button again to exit!",
                Toast.LENGTH_SHORT
            ).show()
            isDoubleBackPressed = true
        }
    }

    fun showLoader() {
        loaderLayout.visibility = View.VISIBLE
    }

    fun hideLoader() {
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

    override fun onRestart() {
        super.onRestart()
        Log.i("KP", "onRestart")
    }

    override fun onStart() {
        Log.i("KP", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i("KP", "onResume")
        super.onResume()
    }

    fun showPopup()
    {
        val fm = this.supportFragmentManager
        PopUp(this).also {
            it.show(fm, "confirm")
        }
    }
}