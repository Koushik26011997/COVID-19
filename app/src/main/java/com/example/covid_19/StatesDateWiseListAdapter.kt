package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.CasesTimeSeriesItem
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class StatesDateWiseListAdapter(
    arrAyListConfirm: ArrayList<CasesTimeSeriesItem>
) : RecyclerView.Adapter<StatesDateWiseListAdapter.MyViewHolder>()
{
    var mArrAyList = arrAyListConfirm

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.statesdatewise_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
       return mArrAyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.date.text = mArrAyList.get(position).date.substring(0,6) + ", 2020"
        holder.confirnmedCase.text = mArrAyList.get(position).dailyconfirmed
        holder.recoveredCase.text = mArrAyList.get(position).dailyrecovered
        holder.deceasedCase.text = mArrAyList.get(position).dailydeceased
    }

    fun refreshList(arraylistConfirm: ArrayList<CasesTimeSeriesItem>)
    {
        mArrAyList  = arraylistConfirm
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val date : TextView = itemView.findViewById(R.id.dateChange)
        val confirnmedCase : TextView = itemView.findViewById(R.id.confirmedCaseChange)
        val recoveredCase : TextView = itemView.findViewById(R.id.recoveredCaseChange)
        val deceasedCase : TextView = itemView.findViewById(R.id.deceasedCaseChange)
    }
}