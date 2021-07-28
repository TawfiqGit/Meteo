package com.tawfiq.meteo.ui.openweathermap

import com.tawfiq.meteo.domain.Weather
import com.tawfiq.meteo.domain.WeatherWrapper

fun mapOpenWeatherDataToWeather(weatherWrapper : WeatherWrapper) : Weather{
    val weatherFirst  = weatherWrapper.weather.first() //Returns first element
    return Weather(weatherFirst.description,
        weatherWrapper.main.temperature,
        weatherWrapper.main.humidity,
        weatherWrapper.main.pressure,
        "https://openweathermap.org/img/w/${weatherFirst.icon}.png"
    )
}
