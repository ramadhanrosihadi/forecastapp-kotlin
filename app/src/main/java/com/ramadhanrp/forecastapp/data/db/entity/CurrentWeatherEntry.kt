package com.ramadhanrp.forecastapp.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class  CurrentWeatherEntry(
    @Embedded(prefix = "condition_")
    var condition: Condition,
    @SerializedName("feelslike_c")
    var feelslikeC: Double,
    @SerializedName("feelslike_f")
    var feelslikeF: Double,
    @SerializedName("is_day")
    var isDay: Int,
    @SerializedName("last_updated")
    var lastUpdated: String,
    @SerializedName("precip_in")
    var precipIn: Double,
    @SerializedName("precip_mm")
    var precipMm: Double,
    @SerializedName("temp_c")
    var tempC: Double,
    @SerializedName("temp_f")
    var tempF: Double,
    var uv: Double,
    @SerializedName("vis_km")
    var visKm: Double,
    @SerializedName("vis_miles")
    var visMiles: Double,
    @SerializedName("wind_degree")
    var windDegree: Double,
    @SerializedName("wind_dir")
    var windDir: String,
    @SerializedName("wind_kph")
    var windKph: Double,
    @SerializedName("wind_mph")
    var windMph: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}