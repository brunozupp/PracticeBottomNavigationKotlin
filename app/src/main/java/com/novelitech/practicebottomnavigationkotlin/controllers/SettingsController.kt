package com.novelitech.practicebottomnavigationkotlin.controllers

import com.novelitech.practicebottomnavigationkotlin.core.exceptions.RepositoryException
import com.novelitech.practicebottomnavigationkotlin.dataClasses.Settings
import com.novelitech.practicebottomnavigationkotlin.repositories.settings.ISettingsRepository

class SettingsController(
    private val repository: ISettingsRepository,
) {
    fun saveInLocalStorage(
        settings: Settings,
        onSuccess: () -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {

        Thread {

            try {

                repository.save(settings)

                onSuccess()

            } catch (e: RepositoryException) {
                onError("Error to save settings")
            }

        }.start()
    }

    fun getFromLocalStorage(
        onSuccess: (settings: Settings?) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {

        Thread {

            try {

                val settings = repository.get()

                onSuccess(settings)

            } catch (e: RepositoryException) {
                onError("Error to get settings")
            }

        }.start()

    }
}