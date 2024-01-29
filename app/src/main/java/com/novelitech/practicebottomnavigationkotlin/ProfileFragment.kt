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
import com.novelitech.practicebottomnavigationkotlin.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentProfileBinding.inflate(layoutInflater)

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

        val intentGallery = Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
        }

        launcherGallery.launch(intentGallery)
    }

    private val launcherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if(result.resultCode == Activity.RESULT_OK) {

            val intentData: Intent? = result.data

            val uri = intentData?.data

            if(uri != null) {
                binding.photoView.setImageURI(uri)
            }
        }
    }
}