package com.novelitech.practicebottomnavigationkotlin.repositories.profile

import android.content.Context
import android.graphics.Bitmap

interface IProfileRepository {

    fun saveImage(uri: String, context: Context)

    fun getImage() : Bitmap?
}