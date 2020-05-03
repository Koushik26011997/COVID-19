package com.example.covid_19

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.ResponseZones
import com.example.covid_19.states_Apis.ZonesItem
import org.json.JSONArray
import java.util.stream.Collector
import java.util.stream.Collectors

class StatesSubDistWiseListAdapter(
    arrAyListConfirm: JSONArray
) : RecyclerView.Adapter<StatesSubDistWiseListAdapter.MyViewHolder>()
{
    var mArrAyList = arrAyListConfirm
    var zonesMap = mapOf<String, List<ResponseZones>>()
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
//        if (Utils.zonesArrayList.get(position).district.equals(mArrAyList.getJSONObject(position).getString("district"), ignoreCase = true))
//        {
//            if (Utils.zonesArrayList.get(position).zone.equals("Red"))
//            {
//                holder.header_Recycler_Layout.setBackgroundColor(Utils.activity.resources.getColor(R.color.red))
//            }
//        }

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

        val header_Recycler_Layout : LinearLayout = itemView.findViewById(R.id.header_Recycler_Layout)
    }
}