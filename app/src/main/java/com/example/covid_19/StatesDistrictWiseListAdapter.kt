package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class StatesDistrictWiseListAdapter(
    arrAyListState: ArrayList<String>,
    arrAyListDistrict: ArrayList<JSONArray>
) : RecyclerView.Adapter<StatesDistrictWiseListAdapter.MyViewHolder>() {
    var mArrAyListDistrict = arrAyListDistrict
    var mArrAyListState = arrAyListState

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_district_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mArrAyListState.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var isClicked = false
        var isSorted = false

        holder.statesName.text = mArrAyListState.get(position)

        holder.listing.apply {
            setHasFixedSize(true)
            adapter = StatesSubDistWiseListAdapter(mArrAyListDistrict.get(position))
        }

        holder.handleBtn.setOnClickListener() {
            if (isClicked) {
                holder.handleBtn.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp)
                holder.listing.visibility = View.GONE
                holder.headingLayout.visibility = View.GONE
                isClicked = false
            } else {
                holder.handleBtn.setImageResource(R.drawable.ic_add_circle_outline_black_24dp)
                holder.listing.visibility = View.VISIBLE
                holder.headingLayout.visibility = View.VISIBLE
                isClicked = true
            }
        }

        // Sorting
//        holder.distConfirmed.setOnClickListener() {
//            if (isSorted) {
//
//                mArrAyListDistrict.sortWith(Comparator { o1, o2 ->
//
//                    return@Comparator (o1.getJSONObject(position).getString("confirmed").toInt().compareTo(o2.getJSONObject(position).getString("confirmed").toInt()))
//                })
//                holder.listing.apply {
//                    setHasFixedSize(true)
//                    adapter = StatesSubDistWiseListAdapter(mArrAyListDistrict.get(position))
//                }
//
//                isSorted = false
//            } else {
//
//                mArrAyListDistrict.sortWith(Comparator { o1, o2 ->
//
//                    return@Comparator (o2.getJSONObject(position).getString("confirmed").toInt().compareTo(o1.getJSONObject(position).getString("confirmed").toInt()))
//                })
//                holder.listing.apply {
//                    setHasFixedSize(true)
//                    adapter = StatesSubDistWiseListAdapter(mArrAyListDistrict.get(position))
//                }
//
//                isSorted = true
//            }
//        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var statesName: TextView = itemView.findViewById(R.id.stateName)
        var listing: RecyclerView = itemView.findViewById(R.id.districtStateList)
        var handleBtn: ImageView = itemView.findViewById(R.id.handleBtn)
        var headingLayout: LinearLayout = itemView.findViewById(R.id.headingLayout)

        var distConfirmed: TextView = itemView.findViewById(R.id.distConfirmed)
    }
}