package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UpdateListAdapter(arrAyListConfirm : ArrayList<JSONObject>) : RecyclerView.Adapter<UpdateListAdapter.MyViewHolder>()
{
    private var mArrAyList = arrAyListConfirm
    var timeText : Long = 0
    var simpleDateFormat = SimpleDateFormat("dd MMM EEEE, hh:mm a")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.update_layout_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
       return mArrAyList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.updateText.text = mArrAyList.get(position).getString("update")

        timeText = mArrAyList.get(position).getLong("timestamp")
        var timestamp = Timestamp(timeText*1000)
        var updateDate  = Date(timestamp.time)

        holder.timeStamp.text = simpleDateFormat.format(updateDate)

        if (mArrAyList.size -1 == position)
            holder.viewPopup.visibility = View.GONE
    }

    fun refreshList(arraylistConfirm: ArrayList<JSONObject>)
    {
        mArrAyList  = arraylistConfirm
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val updateText : TextView = itemView.findViewById(R.id.updateText)
        val timeStamp : TextView = itemView.findViewById(R.id.timeStamp)
        val viewPopup : View = itemView.findViewById(R.id.viewPopup)
    }
}