package com.ramadhanrp.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.ramadhanrp.forecastapp.data.provider.UnitProvider
import com.ramadhanrp.forecastapp.data.repository.ForecastRepository
import com.ramadhanrp.forecastapp.internal.UnitSystem
import com.ramadhanrp.forecastapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()
    val isMetric: Boolean
        get() = UnitSystem.METRIC == unitSystem

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
