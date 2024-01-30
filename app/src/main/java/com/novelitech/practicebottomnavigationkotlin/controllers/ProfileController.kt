package com.novelitech.practicebottomnavigationkotlin.controllers

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import com.novelitech.practicebottomnavigationkotlin.core.exceptions.RepositoryException
import com.novelitech.practicebottomnavigationkotlin.repositories.profile.IProfileRepository

class ProfileController(
    private val repository: IProfileRepository
) {

    fun getPhotoFromGallery(
        launcher: ActivityResultLauncher<Intent>
    ) {

        val intentGallery = Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
        }

        launcher.launch(intentGallery)
    }

    fun savePhotoInLocalStorage(
        uri: Uri,
        context: Context,
        onSuccess: (uri: Uri) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        Thread {
            try {
                repository.saveImage(uri, context)

                onSuccess(uri)

            } catch (e: RepositoryException) {
                onError("Error to save photo in Local Storage")
            }
        }.start()
    }

    fun getPhotoInLocalStorage(
        onSuccess: (bitmap: Bitmap?) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        Thread {

            try {

                val bitmap = repository.getImage()

                onSuccess(bitmap)

            } catch (e: RepositoryException) {
                onError("Error to get photo in Local Storage")
            }

        }.start()
    }
}