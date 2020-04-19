package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import kotlin.collections.ArrayList

class StatesSubDistWiseListAdapter(
    arrAyListConfirm: JSONArray
) : RecyclerView.Adapter<StatesSubDistWiseListAdapter.MyViewHolder>()
{
    var mArrAyList = arrAyListConfirm

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_district_confirm, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
       return mArrAyList.length()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.distName.text = mArrAyList.getJSONObject(position).getString("district")
        holder.distConfirm.text = mArrAyList.getJSONObject(position).getString("confirmed")
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val distName : TextView = itemView.findViewById(R.id.distName)
        val distConfirm : TextView = itemView.findViewById(R.id.distConfirm)
    }
}