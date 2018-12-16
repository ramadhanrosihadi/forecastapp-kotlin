package com.ramadhanrp.forecastapp.data.provider

import com.ramadhanrp.forecastapp.internal.UnitSystem

interface UnitProvider{
    fun getUnitSystem(): UnitSystem
}