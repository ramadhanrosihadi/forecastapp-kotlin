package com.ramadhanrp.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.ramadhanrp.forecastapp.data.db.CurrentWeatherDao
import com.ramadhanrp.forecastapp.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.ramadhanrp.forecastapp.data.network.WeatherNetworkDataSource
import com.ramadhanrp.forecastapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadCurrentWeather.observeForever {newCurrentWeather->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchedCurrentWeather()
        }
    }

    private suspend fun fetchedCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("Surabaya", Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastTimeFetch: ZonedDateTime):Boolean{
        return lastTimeFetch.isBefore(ZonedDateTime.now().minusMinutes(30))
    }
}