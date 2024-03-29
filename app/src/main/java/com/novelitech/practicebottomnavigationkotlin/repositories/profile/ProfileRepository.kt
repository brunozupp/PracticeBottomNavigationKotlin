package com.novelitech.practicebottomnavigationkotlin.repositories.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.novelitech.practicebottomnavigationkotlin.core.exceptions.RepositoryException
import com.novelitech.practicebottomnavigationkotlin.datasource.localStorage.ILocalStorage
import java.io.ByteArrayOutputStream


class ProfileRepository(
    private val localStorage: ILocalStorage
) : IProfileRepository {

    private val KEY_IMAGE_PROFILE = "KEY_IMAGE_PROFILE"

    override fun saveImage(uri: Uri, context: Context) {

        try {

            var bitmap: Bitmap

            if(Build.VERSION.SDK_INT < 28) {

                bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    uri
                )

            } else {

                val source = ImageDecoder.createSource(context.contentResolver, uri)

                bitmap = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.setTargetSampleSize(1)
                    decoder.isMutableRequired = true
                }
            }

            val baos = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            val b = baos.toByteArray()

            val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)

            localStorage.save<String>(KEY_IMAGE_PROFILE, encodedImage)
        } catch (e: Exception) {

            Log.d("ProfileRepository", "Error to save profile's image")

            throw RepositoryException()
        }
    }

    override fun getImage(): Bitmap? {

        try {

            val type = object : TypeToken<String>() {}.type

            val imageEncoded = localStorage.get<String>(KEY_IMAGE_PROFILE, type) ?: return null

            val b = Base64.decode(imageEncoded, Base64.DEFAULT)

            return BitmapFactory.decodeByteArray(b, 0, b.size)

        } catch (e: Exception) {

            Log.d("ProfileRepository", "Error to retrieve profile's image")

            throw RepositoryException()
        }

        return null
    }
}