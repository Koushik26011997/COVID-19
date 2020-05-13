package com.example.covid_19

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.text.NumberFormat

class WorldListAdapter(arrayList: ArrayList<JSONObject>): RecyclerView.Adapter<WorldListAdapter.MyViewHolder>(), Filterable
{
    private var mArrayList = arrayList
    private var mArrayListFull = ArrayList<JSONObject>(arrayList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.world_layout_adapter, parent, false)
        return  MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return mArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
//        if (position == 0)
//            holder.tv_country.text = "INFORMATION OF "+ (mArrayList.size-1) +" COUNTRIES"
//        else
//            holder.tv_country.text = mArrayList.get(position).getString("country") + " [#"+ position +"]"

        holder.tv_country.text = mArrayList.get(position).getString("country")
        holder.val_cases.text = NumberFormat.getInstance().format(mArrayList.get(position).getString("cases").toInt())
        if (mArrayList.get(position).getString("recovered") != "null")
            holder.val_recovered.text = NumberFormat.getInstance().format(mArrayList.get(position).getString("recovered").toInt())
        else
            holder.val_recovered.text = "N/A"
        holder.val_active.text = NumberFormat.getInstance().format(mArrayList.get(position).getString("active").toInt())
        holder.val_deaths.text = NumberFormat.getInstance().format(mArrayList.get(position).getString("deaths").toInt())
        holder.val_critical.text = NumberFormat.getInstance().format(mArrayList.get(position).getString("critical").toInt())
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tv_country : TextView = itemView.findViewById(R.id.tv_country)
        val val_cases : TextView = itemView.findViewById(R.id.val_cases)
        val val_recovered : TextView = itemView.findViewById(R.id.val_recovered)
        val val_active : TextView = itemView.findViewById(R.id.val_active)
        val val_deaths : TextView = itemView.findViewById(R.id.val_deaths)
        val val_critical : TextView = itemView.findViewById(R.id.val_critical)
    }

    override fun getFilter(): Filter
    {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter()
    {
        override fun performFiltering(constraint: CharSequence): FilterResults
        {
            var filteredList: MutableList<JSONObject> = ArrayList()

            if (constraint == null || constraint.length == 0)
            {
                filteredList.addAll(mArrayListFull)
            }
            else
            {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in mArrayList)
                {
                    if (item.getString("country").toLowerCase().startsWith(filterPattern))
                    {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults)
        {
            mArrayList.clear()
            mArrayList.addAll(results.values as List<JSONObject>)
            notifyDataSetChanged()
        }
    }
}