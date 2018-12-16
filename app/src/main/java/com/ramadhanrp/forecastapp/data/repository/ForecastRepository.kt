package com.ramadhanrp.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.ramadhanrp.forecastapp.data.db.entity.WeatherLocation
import com.ramadhanrp.forecastapp.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository{
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}