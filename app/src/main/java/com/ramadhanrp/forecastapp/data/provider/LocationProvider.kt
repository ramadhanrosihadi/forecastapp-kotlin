package com.ramadhanrp.forecastapp.data.provider

import com.ramadhanrp.forecastapp.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChange(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString():String
}