package com.ramadhanrp.forecastapp

import android.app.Application
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.ramadhanrp.forecastapp.data.db.ForecastDatabase
import com.ramadhanrp.forecastapp.data.network.*
import com.ramadhanrp.forecastapp.data.provider.LocationProvider
import com.ramadhanrp.forecastapp.data.provider.LocationProviderImpl
import com.ramadhanrp.forecastapp.data.provider.UnitProvider
import com.ramadhanrp.forecastapp.data.provider.UnitProviderImpl
import com.ramadhanrp.forecastapp.data.repository.ForecastRepository
import com.ramadhanrp.forecastapp.data.repository.ForecastRepositoryImpl
import com.ramadhanrp.forecastapp.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware{
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(),instance(),instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }
}