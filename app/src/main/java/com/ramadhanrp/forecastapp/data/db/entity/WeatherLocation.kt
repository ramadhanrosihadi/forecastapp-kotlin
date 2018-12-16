package com.ramadhanrp.forecastapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    var name: String,
    var region: String,
    var country: String,
    var lat: Double,
    var lon: Double,
    var localtime: String,
    @SerializedName("tz_id")
    var tzId: String,
    @SerializedName("localtime_epoch")
    var localtimeEpoch: Long
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID

    val zonedDateTime: ZonedDateTime
    get() {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(localtimeEpoch), ZoneId.of(tzId))
    }
}