package com.ramadhanrp.forecastapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramadhanrp.forecastapp.data.network.response.CurrentWeatherResponse
import com.ramadhanrp.forecastapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadCurrentWeather = MutableLiveData<CurrentWeatherResponse>( )
    override val downloadCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchCurrentWeather = apixuWeatherApiService.getCurrentWeather(
                location,languageCode
            ).await()
            _downloadCurrentWeather.postValue(fetchCurrentWeather)
        }catch (e: NoConnectivityException){
            Log.e("Connectivity","No Internet Connection", e)
        }
    }
}