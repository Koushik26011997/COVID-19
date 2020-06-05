package com.example.covid_19

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.text.NumberFormat
import java.text.SimpleDateFormat

class StatesSubDistWiseListAdapter(
    arrAyListConfirm: JSONArray
) : RecyclerView.Adapter<StatesSubDistWiseListAdapter.MyViewHolder>()
{
    var mArrAyList = arrAyListConfirm
    var simpleDateFormat = SimpleDateFormat("dd MMM, yyyy")
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            holder.header_Recycler_Layout.tooltipText = mArrAyList.getJSONObject(position).getString("district") + " is not in any ZONE"
        }
        for (i in 0 until Utils.zonesArrayList.size)
        {
            if (Utils.zonesArrayList[i].district.equals(mArrAyList.getJSONObject(position).getString("district"), true))
            {
                if (Utils.zonesArrayList[i].zone.equals("Red", true))
                {
                    holder.header_Recycler_Layout.setBackground(Utils.activity.getDrawable(R.drawable.red_zone))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        holder.header_Recycler_Layout.tooltipText = mArrAyList.getJSONObject(position).getString("district") + " is in RED ZONE\nUpdated on: "+ simpleDateFormat.format(SimpleDateFormat("dd/MM/yyyy").parse(Utils.zonesArrayList[i].lastupdated))
                    }
                }
                else if (Utils.zonesArrayList[i].zone.equals("Green", true))
                {
                    holder.header_Recycler_Layout.setBackground(Utils.activity.getDrawable(R.drawable.green_zone))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        holder.header_Recycler_Layout.tooltipText = mArrAyList.getJSONObject(position).getString("district") + " is in GREEN ZONE\nUpdated on: "+ simpleDateFormat.format(SimpleDateFormat("dd/MM/yyyy").parse(Utils.zonesArrayList[i].lastupdated))
                    }
                }
                else if (Utils.zonesArrayList[i].zone.equals("Orange", true))
                {
                    holder.header_Recycler_Layout.setBackground(Utils.activity.getDrawable(R.drawable.oranze_zone))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        holder.header_Recycler_Layout.tooltipText = mArrAyList.getJSONObject(position).getString("district") + " is in ORANGE ZONE\nUpdated on: "+ simpleDateFormat.format(SimpleDateFormat("dd/MM/yyyy").parse(Utils.zonesArrayList[i].lastupdated))
                    }
                }
            }
        }

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

        holder.distConfirm.text = NumberFormat.getInstance().format(mArrAyList.getJSONObject(position).getString("confirmed").toInt()) + "\n[+" + NumberFormat.getInstance().format(mArrAyList.getJSONObject(position).getJSONObject("delta").getString("confirmed").toInt()) +"]"
        holder.distActive.text = NumberFormat.getInstance().format(mArrAyList.getJSONObject(position).getString("active").toInt())
        holder.distRecover.text = NumberFormat.getInstance().format(mArrAyList.getJSONObject(position).getString("recovered").toInt()) + "\n[+" + NumberFormat.getInstance().format(mArrAyList.getJSONObject(position).getJSONObject("delta").getString("recovered").toInt()) +"]"
        holder.distDeath.text = NumberFormat.getInstance().format(mArrAyList.getJSONObject(position).getString("deceased").toInt()) + "\n[+" + NumberFormat.getInstance().format(mArrAyList.getJSONObject(position).getJSONObject("delta").getString("deceased").toInt()) +"]"
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val distName : TextView = itemView.findViewById(R.id.distName)
        val distConfirm : TextView = itemView.findViewById(R.id.distConfirm)
        val distActive : TextView = itemView.findViewById(R.id.distActive)
        val distRecover : TextView = itemView.findViewById(R.id.distRecover)
        val distDeath : TextView = itemView.findViewById(R.id.distDeath)
        val header_Recycler_Layout : LinearLayout = itemView.findViewById(R.id.header_Recycler_Layout)
    }
}