package com.novelitech.practicebottomnavigationkotlin.repositories.settings

import android.util.Log
import com.google.gson.reflect.TypeToken
import com.novelitech.practicebottomnavigationkotlin.dataClasses.Settings
import com.novelitech.practicebottomnavigationkotlin.datasource.localStorage.ILocalStorage

class SettingsRepository(
    private val localStorage: ILocalStorage,
) : ISettingsRepository {
    override fun save(settings: Settings) {

        try {

            localStorage.save<Settings>(KEY_SETTINGS, settings)

        } catch (e: Exception) {

            Log.d("SettingsRepository", "Error to save the Settings")
        }
    }

    override fun get(): Settings? {
        try {

            val type = object : TypeToken<Settings>() {}.type

            return localStorage.get<Settings>(KEY_SETTINGS, type)

        } catch (e: Exception) {

            Log.d("SettingsRepository", "Error to retrieve the Settings")
            Log.d("SettingsRepository", e.stackTraceToString())
        }

        return null
    }
}