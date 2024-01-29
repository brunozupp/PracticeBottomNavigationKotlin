package com.novelitech.practicebottomnavigationkotlin.datasource.localStorage

interface ILocalStorage<T> {

    val KEY_LOCAL_STORAGE_APP: String
        get() = "APP_PRACTICE_BOTTOM_NAVIGATION"

    fun save(key: String, data: T)

    fun getAll(key: String) : T?
}