package com.novelitech.practicebottomnavigationkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.novelitech.practicebottomnavigationkotlin.dataClasses.Settings
import com.novelitech.practicebottomnavigationkotlin.databinding.FragmentSettingsBinding
import com.novelitech.practicebottomnavigationkotlin.repositories.settings.ISettingsRepository


class SettingsFragment(
    private val settingsRepository: ISettingsRepository
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

        settingsRepository.save(settings)
    }

    private fun getItemsIdSelected() : List<Int> {

        val selectedItemsId = mutableListOf<Int>()

        if(binding.cbItem1.isChecked) selectedItemsId.add(binding.cbItem1.id)
        if(binding.cbItem2.isChecked) selectedItemsId.add(binding.cbItem2.id)
        if(binding.cbItem3.isChecked) selectedItemsId.add(binding.cbItem3.id)

        return selectedItemsId
    }

    private fun getSettingsFromLocalStorage() {
        val settings = settingsRepository.get() ?: return

        binding.rgOptions.check(settings.selectedOptionId)

        binding.cbItem1.isChecked = settings.selectedItemsId.contains(binding.cbItem1.id)
        binding.cbItem2.isChecked = settings.selectedItemsId.contains(binding.cbItem2.id)
        binding.cbItem3.isChecked = settings.selectedItemsId.contains(binding.cbItem3.id)
    }
}