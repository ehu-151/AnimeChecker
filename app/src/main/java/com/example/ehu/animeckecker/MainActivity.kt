package com.example.ehu.animeckecker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ehu.animeckecker.databinding.ActivityMainBinding
import com.example.ehu.animeckecker.repository.LoginRepository
import com.example.ehu.animeckecker.util.AppSharedPreferences


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setupWithNavController(findNavController(this, R.id.my_nav_host_fragment))
        binding.toolbar.setOnMenuItemClickListener {
            when (it.getItemId()) {
                R.id.action_logout -> logout()
            }
            true
        }
        Navigation.findNavController(this, R.id.my_nav_host_fragment)
            .addOnDestinationChangedListener { controller, destination, arguments ->
                Log.d("app_fragment", destination.label.toString())
            }
    }

    private fun logout() {
        val token = AppSharedPreferences(this).getToken()
        LoginRepository().logout(token)
        AppSharedPreferences(this).setIsLogin(false)
        AppSharedPreferences(this).setToken("")
        val nav = NavAction(R.id.nav_graph).destinationId
        Navigation.findNavController(this, R.id.my_nav_host_fragment).navigate(nav)
    }
}
