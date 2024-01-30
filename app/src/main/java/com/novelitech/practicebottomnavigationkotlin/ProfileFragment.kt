package com.novelitech.practicebottomnavigationkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.novelitech.practicebottomnavigationkotlin.controllers.ProfileController
import com.novelitech.practicebottomnavigationkotlin.databinding.FragmentProfileBinding

class ProfileFragment(
    private val controller: ProfileController
) : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentProfileBinding.inflate(layoutInflater)

        getImageFromLocalStorage()

        binding.btnGetPhoto.setOnClickListener {
            getPhotoFromGallery()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getPhotoFromGallery() {

        controller.getPhotoFromGallery(
            launcher = launcher,
        )
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if(result.resultCode == Activity.RESULT_OK) {

            val intentData: Intent? = result.data

            val uri = intentData?.data

            if(uri != null) {

                controller.savePhotoInLocalStorage(
                    uri = uri,
                    context = requireContext(),
                    onSuccess = {uri ->
                        activity?.runOnUiThread {
                            binding.photoView.setImageURI(uri)
                        }
                    },
                    onError = {errorMessage ->
                        activity?.runOnUiThread {
                            Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
        }
    }

    private fun getImageFromLocalStorage() {

        controller.getPhotoInLocalStorage(
            onSuccess = {bitmap ->
                if(bitmap != null) {
                    activity?.runOnUiThread {
                        binding.photoView.setImageBitmap(bitmap)
                    }
                }
            },
            onError = {errorMessage ->
                Toast.makeText(this.context, errorMessage, Toast.LENGTH_LONG).show()
            }
        )
    }
}