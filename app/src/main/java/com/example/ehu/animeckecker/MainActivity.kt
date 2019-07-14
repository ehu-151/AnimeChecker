package com.example.ehu.animeckecker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import com.example.ehu.animeckecker.databinding.ActivityMainBinding
import com.example.ehu.animeckecker.repository.LoginRepository
import com.example.ehu.animeckecker.util.AppSharedPreferences


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.getItemId()) {
                R.id.action_logout -> logout()
            }
            true
        }
    }

    private fun logout() {
        val token = AppSharedPreferences(this).getToken()
        LoginRepository().logout(token)
        AppSharedPreferences(this).setIsLogin(false)
        AppSharedPreferences(this).setToken("")
        intentLogin()
    }

    private fun intentLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.my_nav_host_fragment).navigateUp()
}
