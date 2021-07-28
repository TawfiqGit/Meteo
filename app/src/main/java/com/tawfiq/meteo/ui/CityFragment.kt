package com.tawfiq.meteo.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tawfiq.meteo.R
import com.tawfiq.meteo.database.App
import com.tawfiq.meteo.database.DatabaseHelper
import com.tawfiq.meteo.ui.weather.dialog.CityDialogFragment
import com.tawfiq.meteo.ui.weather.dialog.DeleteCityDialogFragment
import com.tawfiq.meteo.domain.City
import com.tawfiq.meteo.ui.utils.toast

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CityFragment : Fragment(), ListCityAdapter.CityItemListenner {

    interface CityFragmentListenner{
        fun onCityListenner (city : City)
    }

    private lateinit var cityList: MutableList<City>
    private lateinit var database : DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var listCityAdapter: ListCityAdapter
    lateinit var listenner: CityFragmentListenner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        cityList = mutableListOf()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_city, container, false)
        with(v){
            recyclerView = findViewById(R.id.recycleViewCity)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityList = database.getAllCity()
        listCityAdapter = ListCityAdapter(cityList,this)
        recyclerView.adapter = listCityAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_ajouter ->{
                showDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCitySelected(city: City) {
        listenner.onCityListenner(city)
    }

    override fun onCityDeleted(city: City) {
        showDialogDelete(city)
    }

    private fun showDialog(){
        val cityDialogFragment = CityDialogFragment()

        cityDialogFragment.listener = object : CityDialogFragment.CityDialogFragmentListener{
            override fun onDialogPositive(cityName: String) {
                val c = City(cityName)
                Log.i("city_create","dialog c = ${c.name}")
                saveCity(c)
            }
            override fun onDialogNegatif() {}
        }
        cityDialogFragment.show(parentFragmentManager,"CityFragmentDialog")
    }

    fun saveCity(city :City){
        if (database.createCity(city)){
            cityList.add(city)
            listCityAdapter.notifyDataSetChanged()
        }else{
            context?.toast("No create table city")
        }
    }

    private fun showDialogDelete(city: City){
        val deleteDialog = DeleteCityDialogFragment.newInstance(city.name)

        deleteDialog.listener = object : DeleteCityDialogFragment.DeleteCityDialogFragmentListener {
            override fun onDialogPositive() {
                deleteCity(city)
            }

            override fun onDialogNegatif() {}
        }
        deleteDialog.show(parentFragmentManager,"CityFragmentDialog")
    }

    fun deleteCity(city :City){
        if (database.deleteCity(city)){
            cityList.remove(city)
            listCityAdapter.notifyDataSetChanged()
        }else{
            context?.toast("NO DELETE ")
        }
    }
}