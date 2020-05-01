package com.example.covid_19

import NetworkMonitor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView


class SplashActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val icon : ImageView = findViewById(R.id.icon)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)

        val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
        icon.startAnimation(animation)
    }


    override fun onRestart() {
        super.onRestart()
        Log.i("KP", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("KP", "onResume")
    }
}