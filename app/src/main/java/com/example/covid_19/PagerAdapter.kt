package com.example.covid_19

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter : FragmentPagerAdapter
{
    val activity : MainActivity

    constructor(fm: FragmentManager, mainActivity: MainActivity) : super(fm)
    {
        activity = mainActivity
    }

    override fun getItem(position: Int): Fragment
    {
        when(position)
        {
            0 ->
            {
                return NotificationFragment(activity)
            }
            1 ->
            {
                return DashboardFragment(activity)
            }
        }
        return NotificationFragment(activity)

    }

    override fun getCount(): Int
    {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence?
    {
        when(position)
        {
            0 ->
            {
                return "DAILY"
            }
            1 ->
            {
                return "DISTRICT"
            }
        }
        return null
    }
}