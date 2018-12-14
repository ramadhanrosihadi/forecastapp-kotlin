package com.ramadhanrp.forecastapp.data.db.entity

import com.google.gson.annotations.SerializedName

data class Location(
    var country: String,
    var lat: Double,
    var localtime: String,
    @SerializedName("localtime_epoch")
    var localtimeEpoch: Int,
    var lon: Double,
    var name: String,
    var region: String,
    @SerializedName("tz_id")
    var tzId: String
)