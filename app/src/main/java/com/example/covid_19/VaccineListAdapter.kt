package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.CasesTimeSeriesItem
import com.example.covid_19.states_Apis.TestedItem
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VaccineListAdapter(
    arrAyListConfirm: ArrayList<TestedItem>
) : RecyclerView.Adapter<VaccineListAdapter.MyViewHolder>()
{
    var mArrAyList = arrAyListConfirm
    var simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy")
    var simpleDateParse = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vaccine_list_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
       return mArrAyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        //holder.date.text = simpleDateFormat.format(simpleDateParse.parse(mArrAyList.get(position).dateymd))
        if (mArrAyList.get(position).totalindividualsvaccinated != "")
            holder.date.text = "Total Individual Vaccinated: " + NumberFormat.getInstance().format(mArrAyList.get(position).totalindividualsvaccinated.toInt())
//        else
//            holder.date.text = "Total Individual Vaccinated: 0"
    }

    fun refreshList(arraylistConfirm: ArrayList<TestedItem>)
    {
        mArrAyList  = arraylistConfirm
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val date : TextView = itemView.findViewById(R.id.totalApplication)
    }
}