package com.example.ehu.animeckecker

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
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
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(parent, name, context, attrs)
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
