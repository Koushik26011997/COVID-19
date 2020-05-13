package com.example.covid_19

import android.animation.ValueAnimator
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.states_Apis.StatewiseItem
import java.text.NumberFormat

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            if (!mArrayList.get(position+1).statenotes.toString().equals(""))
            {
                holder.statesName.text = mArrayList.get(position+1).state + "*"
                holder.statesName.tooltipText = Html.fromHtml(mArrayList.get(position+1).statenotes.toString())
            }
            else
            {
                holder.statesName.text = mArrayList.get(position+1).state
                holder.statesName.tooltipText = mArrayList.get(position+1).statenotes.toString()
            }
        }

        if (!mArrayList.get(position+1).deltaconfirmed.equals("0"))
            holder.confirnmedCase.text = NumberFormat.getInstance().format(mArrayList.get(position+1).confirmed.toInt()) + "\n[+"+NumberFormat.getInstance().format(mArrayList.get(position+1).deltaconfirmed.toInt())+"]"
        else
            holder.confirnmedCase.text = NumberFormat.getInstance().format(mArrayList.get(position+1).confirmed.toInt())

        holder.activeCase.text = NumberFormat.getInstance().format(mArrayList.get(position+1).active.toInt())

        if (!mArrayList.get(position+1).deltarecovered.equals("0"))
            holder.recoveredCase.text = NumberFormat.getInstance().format(mArrayList.get(position+1).recovered.toInt()) + "\n[+"+NumberFormat.getInstance().format(mArrayList.get(position+1).deltarecovered.toInt())+"]"
        else
            holder.recoveredCase.text = NumberFormat.getInstance().format(mArrayList.get(position+1).recovered.toInt())

        if (!mArrayList.get(position+1).deltadeaths.equals("0"))
            holder.deceasedCase.text = NumberFormat.getInstance().format(mArrayList.get(position+1).deaths.toInt()) + "\n[+"+NumberFormat.getInstance().format(mArrayList.get(position+1).deltadeaths.toInt())+"]"
        else
            holder.deceasedCase.text = NumberFormat.getInstance().format(mArrayList.get(position+1).deaths.toInt())
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