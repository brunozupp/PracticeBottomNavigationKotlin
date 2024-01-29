package com.novelitech.practicebottomnavigationkotlin.repositories.settings

import com.novelitech.practicebottomnavigationkotlin.dataClasses.Settings

interface ISettingsRepository {

    val KEY_SETTINGS: String
        get() = "KEY_SETTINGS"

    fun save(settings: Settings)

    fun get(): Settings?
}