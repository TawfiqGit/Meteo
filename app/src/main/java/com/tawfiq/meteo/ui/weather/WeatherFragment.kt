package com.tawfiq.meteo.ui.weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tawfiq.meteo.R
import com.tawfiq.meteo.database.App
import com.tawfiq.meteo.domain.WeatherWrapper
import com.tawfiq.meteo.ui.openweathermap.mapOpenWeatherDataToWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {

    private lateinit var cityName: String

    companion object {
        const val EXTRA_WEATHER_CITY_NAME = "com.tawfiq.meteo.extra.EXTRA_WEATHER_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity?.intent!!.hasExtra(EXTRA_WEATHER_CITY_NAME)){
            updateWeatherForCity(requireActivity().intent.getStringExtra(EXTRA_WEATHER_CITY_NAME))
        }
    }

    private fun updateWeatherForCity(cityName: String) {
        this.cityName = cityName
        Log.i("reponse","city $cityName")

        val call = App.weatherService.getWeather("$cityName,fr")
        call.enqueue(object : Callback<WeatherWrapper>{
            override fun onResponse(call: Call<WeatherWrapper>?, response: Response<WeatherWrapper>?) {
                response?.body()?.let {
                    val weather = mapOpenWeatherDataToWeather(it)// it contient copie de la propriété à l'intérieur de let
                    Log.i("reponse","weather : $weather")
                }
            }

            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Toast.makeText(activity, "Could not load city weather $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}