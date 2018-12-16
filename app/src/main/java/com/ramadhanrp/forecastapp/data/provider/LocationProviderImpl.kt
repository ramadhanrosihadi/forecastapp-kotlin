package com.ramadhanrp.forecastapp.data.provider

import com.ramadhanrp.forecastapp.data.db.entity.WeatherLocation

class LocationProviderImpl : LocationProvider {
    override suspend fun hasLocationChange(lastWeatherLocation: WeatherLocation): Boolean {
        return true
    }

    override suspend fun getPreferredLocationString(): String {
        return "Surabaya"
    }
}