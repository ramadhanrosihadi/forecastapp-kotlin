package com.ramadhanrp.forecastapp.data.network

 import androidx.lifecycle.LiveData
import com.ramadhanrp.forecastapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource{
    val downloadCurrentWeather: LiveData<CurrentWeatherResponse>
    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}