package com.tawfiq.meteo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tawfiq.meteo.model.City

class ListCityAdapter(private val listCity : List<City>, private val cityListenner : CityItemListenner)
    : RecyclerView.Adapter<ListCityAdapter.ViewHolder>(), View.OnClickListener {

    interface CityItemListenner {
        fun onCitySelected(city : City)
        fun onCityDeleted(city : City)
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val cardviewCity: CardView = itemView.findViewById(R.id.cardviewCity)
        val textViewNameCity: TextView = itemView.findViewById(R.id.textViewNameCity)
        val imageButtonDelete : ImageButton = itemView.findViewById(R.id.imageButtonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_city,parent,false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = listCity[position]
        with(holder){
            cardviewCity.tag = city
            cardviewCity.setOnClickListener(this@ListCityAdapter)
            imageButtonDelete.setOnClickListener(this@ListCityAdapter)
            imageButtonDelete.tag = city
            textViewNameCity.text = city.name
        }
    }

    override fun getItemCount(): Int {
        return listCity.size
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cardviewCity -> cityListenner.onCitySelected(v.tag as City)
            R.id.imageButtonDelete -> cityListenner.onCityDeleted(v.tag as City)
        }
    }
}
