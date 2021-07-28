package com.tawfiq.meteo.ui.openweathermap

import com.tawfiq.meteo.domain.WeatherWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY ="0d0ae3faed829283fb03eab3afda7dd7"

interface OpenWeatherService {

    @GET(value = "data/2.5/weather?units=metric&lang=fr")
    fun getWeather(
        @Query(value = "q") cityName:String,
        @Query(value = "appid") apiKey :String = API_KEY) : Call<WeatherWrapper>
}