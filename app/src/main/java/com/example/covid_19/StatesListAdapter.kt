package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.StatewiseItem

class StatesListAdapter(arrayList: ArrayList<StatewiseItem>): RecyclerView.Adapter<StatesListAdapter.MyViewHolder>()
{
    private var mArrayList = arrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.states_layout, parent, false)
        return  MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return mArrayList.size-1
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.statesName.text = mArrayList.get(position+1).state

        if (!mArrayList.get(position+1).deltaconfirmed.equals("0"))
            holder.confirnmedCase.text = mArrayList.get(position+1).confirmed + "\n[+"+mArrayList.get(position+1).deltaconfirmed+"]"
        else
            holder.confirnmedCase.text = mArrayList.get(position+1).confirmed

        holder.activeCase.text = mArrayList.get(position+1).active

        if (!mArrayList.get(position+1).deltarecovered.equals("0"))
            holder.recoveredCase.text = mArrayList.get(position+1).recovered + "\n[+"+mArrayList.get(position+1).deltarecovered+"]"
        else
            holder.recoveredCase.text = mArrayList.get(position+1).recovered

        if (!mArrayList.get(position+1).deltadeaths.equals("0"))
            holder.deceasedCase.text = mArrayList.get(position+1).deaths + "\n[+"+mArrayList.get(position+1).deltadeaths+"]"
        else
            holder.deceasedCase.text = mArrayList.get(position+1).deaths
    }

    fun refreshList(arrayList: ArrayList<StatewiseItem>)
    {
        mArrayList = arrayList
        notifyDataSetChanged()
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val statesName : TextView = itemView.findViewById(R.id.statesName)
        val confirnmedCase : TextView = itemView.findViewById(R.id.confirmedCase)
        val activeCase : TextView = itemView.findViewById(R.id.activeCase)
        val recoveredCase : TextView = itemView.findViewById(R.id.recoveredCase)
        val deceasedCase : TextView = itemView.findViewById(R.id.deceasedCase)
    }
}