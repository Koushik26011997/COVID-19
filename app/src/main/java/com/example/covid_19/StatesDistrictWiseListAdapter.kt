package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.CasesTimeSeriesItem
import org.json.JSONArray
import org.json.JSONObject

class StatesDistrictWiseListAdapter(arrAyListState : ArrayList<String>, arrAyListDistrict : ArrayList<JSONArray>) : RecyclerView.Adapter<StatesDistrictWiseListAdapter.MyViewHolder>()
{
    var mArrAyListDistrict = arrAyListDistrict
    var mArrAyListState = arrAyListState

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_district_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
       return 32
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
//        for (i in 0 until mArrAyListDistrict.size)
//        {
//            //holder.districtName.text = mArrAyListDistrict.get(position).getJSONObject(i).getString("district")
//            holder.confirmedDistrictCase.text = mArrAyListDistrict.get(position).getJSONObject(i).getString("confirmed")
//        }
        for (i in 0 until mArrAyListDistrict.size) {
            holder.statesName.text = mArrAyListState.get(position)
            holder.districtName.text =
                mArrAyListDistrict.get(position).getJSONObject(i).getString("district")
            holder.confirmedDistrictCase.text =
                mArrAyListDistrict.get(position).getJSONObject(i).getString("confirmed")
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var confirmedDistrictCase : TextView = itemView.findViewById(R.id.confirmedDistrictCase)
        var statesName : TextView = itemView.findViewById(R.id.statesName)
        var districtName : TextView = itemView.findViewById(R.id.districtName)
    }
}