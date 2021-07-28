package com.tawfiq.meteo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tawfiq.meteo.R
import com.tawfiq.meteo.domain.City
import com.tawfiq.meteo.ui.weather.WeatherActivity
import com.tawfiq.meteo.ui.weather.WeatherFragment

class CityActivity : AppCompatActivity() , CityFragment.CityFragmentListenner {

    private lateinit var cityFragment: CityFragment
    private var currentCity : City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        setSupportActionBar(findViewById(R.id.toolbar))

        cityFragment = supportFragmentManager.findFragmentById(R.id.fragment_city) as CityFragment
        cityFragment.listenner = this
    }

    override fun onCityListenner(city: City) {
        currentCity = city
        startWeatherActivity(city)
    }

    private fun startWeatherActivity(city: City) {
        val intent = Intent(this,WeatherActivity::class.java)
        intent.putExtra(WeatherFragment.EXTRA_WEATHER_CITY_NAME,city.name)
        startActivity(intent)
    }
}