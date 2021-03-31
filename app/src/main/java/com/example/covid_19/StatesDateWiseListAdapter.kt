package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.CasesTimeSeriesItem
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StatesDateWiseListAdapter(
    arrAyListConfirm: ArrayList<CasesTimeSeriesItem>
) : RecyclerView.Adapter<StatesDateWiseListAdapter.MyViewHolder>()
{
    var mArrAyList = arrAyListConfirm
    var simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy")
    var simpleDateParse = SimpleDateFormat("yyyy-MM-dd")

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
        holder.date.text = simpleDateFormat.format(simpleDateParse.parse(mArrAyList.get(position).dateymd))
        holder.confirnmedCase.text = NumberFormat.getInstance().format(mArrAyList.get(position).dailyconfirmed.toInt())
        holder.recoveredCase.text = NumberFormat.getInstance().format(mArrAyList.get(position).dailyrecovered.toInt())
        holder.deceasedCase.text = NumberFormat.getInstance().format(mArrAyList.get(position).dailydeceased.toInt())
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