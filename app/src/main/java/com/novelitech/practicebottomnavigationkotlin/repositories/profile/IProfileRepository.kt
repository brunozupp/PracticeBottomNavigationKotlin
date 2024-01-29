package com.novelitech.practicebottomnavigationkotlin.repositories.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

interface IProfileRepository {

    fun saveImage(uri: Uri, context: Context)

    fun getImage() : Bitmap?
}