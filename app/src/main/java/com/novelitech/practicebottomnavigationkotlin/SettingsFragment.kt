package com.novelitech.practicebottomnavigationkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.indices
import com.novelitech.practicebottomnavigationkotlin.controllers.SettingsController
import com.novelitech.practicebottomnavigationkotlin.dataClasses.Settings
import com.novelitech.practicebottomnavigationkotlin.databinding.FragmentSettingsBinding


class SettingsFragment(
    private val controller: SettingsController
) : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentSettingsBinding.inflate(layoutInflater)

        getSettingsFromLocalStorage()

        binding.btnSave.setOnClickListener {
            save()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun save() {

        val settings = Settings(
            selectedOptionId = binding.rgOptions.checkedRadioButtonId,
            selectedItemsId = getItemsIdSelected(),
        )

        enableElements(false)

        controller.saveInLocalStorage(
            settings,
            onSuccess = {
                activity?.runOnUiThread {
                    Toast.makeText(binding.root.context, "Saved settings", Toast.LENGTH_LONG).show()
                    enableElements(true)
                }
            },
            onError = { errorMessage ->
                activity?.runOnUiThread {
                    Toast.makeText(binding.root.context, errorMessage, Toast.LENGTH_LONG).show()
                    enableElements(true)
                }
            }
        )
    }

    private fun enableElements(enabled: Boolean) {
        binding.btnSave.isClickable = enabled
        binding.btnSave.isEnabled = enabled

        for(i in binding.rgOptions.indices) {
            binding.rgOptions.getChildAt(i).isClickable = enabled
        }

        binding.cbItem1.isClickable = enabled
        binding.cbItem2.isClickable = enabled
        binding.cbItem3.isClickable = enabled
    }

    private fun getItemsIdSelected() : List<Int> {

        val selectedItemsId = mutableListOf<Int>()

        if(binding.cbItem1.isChecked) selectedItemsId.add(binding.cbItem1.id)
        if(binding.cbItem2.isChecked) selectedItemsId.add(binding.cbItem2.id)
        if(binding.cbItem3.isChecked) selectedItemsId.add(binding.cbItem3.id)

        return selectedItemsId
    }

    private fun getSettingsFromLocalStorage() {

        enableElements(false)

        controller.getFromLocalStorage(
            onSuccess = {settings ->

                activity?.runOnUiThread {
                    enableElements(true)

                    if(settings != null) {
                        binding.rgOptions.check(settings.selectedOptionId)

                        binding.cbItem1.isChecked = settings.selectedItemsId.contains(binding.cbItem1.id)
                        binding.cbItem2.isChecked = settings.selectedItemsId.contains(binding.cbItem2.id)
                        binding.cbItem3.isChecked = settings.selectedItemsId.contains(binding.cbItem3.id)
                    }
                }

            },
            onError = {errorMessage ->

                activity?.runOnUiThread {
                    enableElements(true)
                    Toast.makeText(binding.root.context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}