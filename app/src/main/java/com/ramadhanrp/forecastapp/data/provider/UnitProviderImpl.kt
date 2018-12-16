package com.ramadhanrp.forecastapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.ramadhanrp.forecastapp.internal.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : UnitProvider {
    private val appCOntext = context.applicationContext
    private val preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appCOntext)

    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}