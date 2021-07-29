package com.tawfiq.meteo.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import com.tawfiq.meteo.R
import com.tawfiq.meteo.database.App
import com.tawfiq.meteo.domain.Weather
import com.tawfiq.meteo.domain.WeatherWrapper
import com.tawfiq.meteo.ui.openweathermap.mapOpenWeatherDataToWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {

    private lateinit var cityName: String
    private lateinit var textViewCity : TextView
    private lateinit var textViewTemperature : TextView
    private lateinit var textViewHumidity : TextView
    private lateinit var textViewPressure : TextView
    private lateinit var textViewDescription : TextView
    private lateinit var imageViewIcon : ImageView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    companion object {
        const val EXTRA_WEATHER_CITY_NAME = "com.tawfiq.meteo.extra.EXTRA_WEATHER_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fragment_weather, container, false)
        textViewCity = v.findViewById(R.id.textView_city)
        textViewDescription = v.findViewById(R.id.textview_description)
        textViewTemperature = v.findViewById(R.id.textViewTemperature)
        textViewPressure = v.findViewById(R.id.textViewPressure)
        textViewHumidity = v.findViewById(R.id.textViewHumidity)
        imageViewIcon = v.findViewById(R.id.imageViewIcon)
        swipeRefresh = v.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener { refreshWeather() }
        return v
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

        if (!swipeRefresh.isRefreshing){
            swipeRefresh.isRefreshing = true
        }
        val call = App.weatherService.getWeather(cityName)
        call.enqueue(object : Callback<WeatherWrapper>{
            override fun onResponse(call: Call<WeatherWrapper>?, response: Response<WeatherWrapper>?) {
                response?.body()?.let {
                    val weather = mapOpenWeatherDataToWeather(it)// it contient copie de la propriété à l'intérieur de let
                    updateUIWeather(weather,cityName)
                    Log.i("reponse","weather : $weather")
                    swipeRefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Toast.makeText(activity, "Please connect to internet $t", Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = false
            }
        })
    }

    private fun updateUIWeather(weather: Weather, cityName : String){
        //Picasso permet de charger des images
        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.ic_baseline_broken_image)
            .into(imageViewIcon)

        textViewCity.text = cityName
        textViewDescription.text = weather.description

        textViewTemperature.text = getString(R.string.weather_temperature,weather.temperature.toInt())
        textViewHumidity.text = getString(R.string.weather_humidity,weather.humidity)
        textViewPressure.text = getString(R.string.weather_pressure,weather.pressure)
    }

    private fun refreshWeather() {
        updateWeatherForCity(cityName)
    }
}
