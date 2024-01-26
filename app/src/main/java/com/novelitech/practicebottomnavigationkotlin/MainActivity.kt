package com.novelitech.practicebottomnavigationkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.novelitech.practicebottomnavigationkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val resume = ResumeFragment()
        val settings = SettingsFragment()
        val profile = ProfileFragment()

        setCurrentFragment(resume)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.miResume -> setCurrentFragment(resume)
                R.id.miSettings -> setCurrentFragment(settings)
                R.id.miProfile -> setCurrentFragment(profile)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.pages, fragment)
            commit()
        }
    }
}