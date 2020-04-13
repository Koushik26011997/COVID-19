package com.example.covid_19

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_dashboard.*

class DashFragment(mainActivity: MainActivity) : Fragment()
{
    val activity = mainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bony, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        //tabAdapter()
    }

    private fun tabAdapter()
    {
        val pagerAdapter = PagerAdapter(activity.supportFragmentManager, activity)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}