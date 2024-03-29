package com.novelitech.practicebottomnavigationkotlin.datasource.localStorage

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.novelitech.practicebottomnavigationkotlin.core.exceptions.LocalStorageException
import java.lang.reflect.Type

class LocalStorage(
    private val activity: AppCompatActivity,
) : ILocalStorage {
    override fun<T> save(key: String, data: T) {

        try {

            val sharedPreferences = activity.getSharedPreferences(KEY_LOCAL_STORAGE_APP, Context.MODE_PRIVATE)

            val editor = sharedPreferences.edit()

            val gson = Gson()

            val dataJson = gson.toJson(data)

            editor.apply {
                putString(key, dataJson)
                apply()
            }

        } catch (e: Exception) {

            Log.d("LocalStorageException", "Erro ao salvar os dados no Local Storage")

            throw LocalStorageException()
        }
    }

    override fun<T> get(key: String, type: Type): T? {
        try {

            val sharedPreferences = activity.getSharedPreferences(KEY_LOCAL_STORAGE_APP, Context.MODE_PRIVATE)

            val dataJson = sharedPreferences.getString(key, null) ?: return null

            val gson = Gson()

            return gson.fromJson(dataJson, type)

        } catch (e: Exception) {

            Log.d("LocalStorageException", "Erro ao listar os dados do Local Storage")

            throw LocalStorageException()
        }

        return null
    }
}