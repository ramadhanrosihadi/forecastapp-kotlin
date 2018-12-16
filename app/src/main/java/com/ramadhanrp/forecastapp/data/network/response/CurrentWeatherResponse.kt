package com.ramadhanrp.forecastapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.ramadhanrp.forecastapp.data.db.entity.CurrentWeatherEntry
import com.ramadhanrp.forecastapp.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    @SerializedName("current")
    var currentWeatherEntry: CurrentWeatherEntry,
    var location: WeatherLocation
)