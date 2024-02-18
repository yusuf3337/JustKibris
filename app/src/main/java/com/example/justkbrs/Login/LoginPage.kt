package com.example.justkbrs.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.justkbrs.databinding.ActivityLoginPageBinding
import androidx.fragment.app.Fragment
import com.example.justkbrs.AdaPost
import com.example.justkbrs.EtkinlikAra
import com.example.justkbrs.Etkinlikler
import com.example.justkbrs.Profile
import com.example.justkbrs.R

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Etkinlikler())

        binding.bottomNavigationView2.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.etliklikler -> replaceFragment(Etkinlikler())
                R.id.etkinlikara -> replaceFragment(EtkinlikAra())
                R.id.profil -> replaceFragment(Profile())
                R.id.adaPost -> replaceFragment(AdaPost())
                else -> return@setOnNavigationItemSelectedListener false
            }
            true
        }


    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTranscation = fragmentManager.beginTransaction()
        fragmentTranscation.replace(R.id.frame_layout, fragment)
        fragmentTranscation.commit()
    }
}
