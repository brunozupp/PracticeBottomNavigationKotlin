package com.novelitech.practicebottomnavigationkotlin.datasource.localStorage

import java.lang.reflect.Type

interface ILocalStorage {

    val KEY_LOCAL_STORAGE_APP: String
        get() = "APP_PRACTICE_BOTTOM_NAVIGATION"

    fun<T> save(key: String, data: T)

    fun<T> get(key: String, type: Type) : T?
}