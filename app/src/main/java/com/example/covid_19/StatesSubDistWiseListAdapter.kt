package com.example.covid_19

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

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
        if (mArrAyList.getJSONObject(position).getString("notes") != "")
        {
            holder.distName.text = mArrAyList.getJSONObject(position).getString("district") + "*"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                holder.distName.tooltipText = mArrAyList.getJSONObject(position).getString("notes")
            }
        }
        else
        {
            holder.distName.text = mArrAyList.getJSONObject(position).getString("district")
        }
        holder.distConfirm.text = mArrAyList.getJSONObject(position).getString("confirmed") + "\n[+" + mArrAyList.getJSONObject(position).getJSONObject("delta").getString("confirmed") +"]"
        holder.distActive.text = mArrAyList.getJSONObject(position).getString("active")
        holder.distRecover.text = mArrAyList.getJSONObject(position).getString("recovered") + "\n[+" + mArrAyList.getJSONObject(position).getJSONObject("delta").getString("recovered") +"]"
        holder.distDeath.text = mArrAyList.getJSONObject(position).getString("deceased") + "\n[+" + mArrAyList.getJSONObject(position).getJSONObject("delta").getString("deceased") +"]"
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val distName : TextView = itemView.findViewById(R.id.distName)
        val distConfirm : TextView = itemView.findViewById(R.id.distConfirm)
        val distActive : TextView = itemView.findViewById(R.id.distActive)
        val distRecover : TextView = itemView.findViewById(R.id.distRecover)
        val distDeath : TextView = itemView.findViewById(R.id.distDeath)
    }
}