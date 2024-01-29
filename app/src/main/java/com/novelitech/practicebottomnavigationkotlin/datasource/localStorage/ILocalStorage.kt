package com.novelitech.practicebottomnavigationkotlin.datasource.localStorage

interface ILocalStorage {

    val KEY_LOCAL_STORAGE_APP: String
        get() = "APP_PRACTICE_BOTTOM_NAVIGATION"

    fun<T> save(key: String, data: T)

    fun<T> get(key: String) : T?
}