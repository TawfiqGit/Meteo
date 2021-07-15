package com.tawfiq.meteo

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tawfiq.meteo.database.App
import com.tawfiq.meteo.database.DatabaseHelper
import com.tawfiq.meteo.dialog.CityDialogFragment
import com.tawfiq.meteo.model.City

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CityFragment : Fragment() {

    private lateinit var cityList: MutableList<City>
    private lateinit var database : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        cityList = mutableListOf()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city, container, false)
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

    private fun showDialog(){
        val cityDialogFragment = CityDialogFragment()

        cityDialogFragment.listener = object : CityDialogFragment.CityDialogFragmentListener{
            override fun onDialogPositive(cityName: String) {
                saveCity(City(cityName))
            }

            override fun onDialogNegatif() {}
        }

        cityDialogFragment.show(parentFragmentManager,"CityFragmentDialog")
    }

    fun saveCity(city :City){
        if (database.createCity(city)){
            cityList.add(city)
        }else{
            Toast.makeText(context,"No cree table city", Toast.LENGTH_SHORT).show()
        }
    }
}