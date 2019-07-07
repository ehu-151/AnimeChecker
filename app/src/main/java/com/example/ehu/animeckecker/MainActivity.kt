package com.example.ehu.animeckecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ehu.animeckecker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // fragmentのset
        val maneger = supportFragmentManager
        val transition = maneger.beginTransaction()
        transition.replace(binding.container.id, ThisSeasonFragment())
        transition.commit()
    }
}
