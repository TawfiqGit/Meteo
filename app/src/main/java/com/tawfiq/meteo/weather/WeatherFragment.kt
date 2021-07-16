package com.tawfiq.meteo.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawfiq.meteo.R

class WeatherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    companion object {
        const val EXTRA_WEATHER_CITY_NAME = "com.tawfiq.meteo.extra.EXTRA_WEATHER_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }
}