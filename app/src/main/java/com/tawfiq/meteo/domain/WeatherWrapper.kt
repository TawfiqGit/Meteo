package com.tawfiq.meteo.domain

data class WeatherWrapper (val weather: Array<WeatherData>,
                           val main : MainData) {

}
