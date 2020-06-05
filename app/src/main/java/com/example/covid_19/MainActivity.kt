package com.example.covid_19

import NetworkMonitor
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private var isDoubleBackPressed = true
    lateinit var update : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Utils.activity = this
        update = findViewById(R.id.updates)

        bottom_nav_view.menu.findItem(R.id.navigation_home).isChecked = true
        loadFragment(HomeFragment.newInstance())

        if (!NetworkMonitor(this).isConnected)
        {
            showPopup()
        }

        update.setOnClickListener(){
            var supportFragment = supportFragmentManager
            if (!NetworkMonitor(this).isConnected)
                showPopup()
            else
                UpdatesPopUp(this).show(supportFragment, "show")
        }

        bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    isDoubleBackPressed = true
                    if (HomeFragment.newInstance() != null)
                        loadFragment(HomeFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_dashboard -> {
                    isDoubleBackPressed = false
                    if (NotificationFragment.newInstance() != null)
                        loadFragment(NotificationFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_district -> {
                    isDoubleBackPressed = false
                    if (DistrictFragment.newInstance() != null)
                        loadFragment(DistrictFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_notification -> {
                    isDoubleBackPressed = false
                    if (DashboardFragment.newInstance() != null)
                        loadFragment(DashboardFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_about -> {
                    isDoubleBackPressed = false
                    if (AboutFragment.newInstance() != null)
                    //if (WorldFragment.newInstance() != null)
                        loadFragment(AboutFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        var id = item.itemId
        when (id)
        {
            R.id.navigation_updates ->
            {
                Toast.makeText(applicationContext, "Updates!", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: Fragment)
    {
        update.visibility = View.VISIBLE
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

    override fun onRestart() {
        super.onRestart()
        Log.i("KP", "onRestart")
    }

    override fun onStart()
    {
        super.onStart()
        Log.i("KP", "onStart")
    }

    override fun onResume() {
        super.onResume()
        bottom_nav_view.menu.findItem(R.id.navigation_home).isChecked = true
        loadFragment(HomeFragment.newInstance())
        Log.i("KP", "onResume")

    }

    fun showPopup() {
        val fm = this.supportFragmentManager
        PopUp(this).also {
            it.show(fm, "confirm")
        }
    }

    fun showUpdates()
    {
        update.visibility = View.VISIBLE
    }
}