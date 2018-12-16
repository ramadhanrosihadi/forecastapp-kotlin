package com.ramadhanrp.forecastapp.data.repository

import androidx.lifecycle.LiveData
import com.ramadhanrp.forecastapp.data.db.CurrentWeatherDao
import com.ramadhanrp.forecastapp.data.db.WeatherLocationDao
import com.ramadhanrp.forecastapp.data.db.entity.WeatherLocation
import com.ramadhanrp.forecastapp.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.ramadhanrp.forecastapp.data.network.WeatherNetworkDataSource
import com.ramadhanrp.forecastapp.data.network.response.CurrentWeatherResponse
import com.ramadhanrp.forecastapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocation().value
        if (lastWeatherLocation == null
            || locationProvider.hasLocationChange(lastWeatherLocation)){
            fetchedCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime)){
            fetchedCurrentWeather()
        }
    }

    private suspend fun fetchedCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString(), Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastTimeFetch: ZonedDateTime):Boolean{
        return lastTimeFetch.isBefore(ZonedDateTime.now().minusMinutes(30))
    }


}