package com.example.ehu.animeckecker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavAction
import androidx.navigation.Navigation
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
                R.id.action_logout -> logout(this)
            }
            true
        }
        Navigation.findNavController(this, R.id.my_nav_host_fragment)
            .addOnDestinationChangedListener { controller, destination, arguments ->
                Log.d("app_fragment", destination.label.toString())
                binding.toolbar.title = destination.label.toString()
                when (destination.label.toString()) {
                    getString(R.string.login_fragment) -> {
                        binding.toolbar.menu.setGroupVisible(0, false)
                    }
                    getString(R.string.my_notification_fragment) -> {
                        binding.toolbar.menu.setGroupVisible(0, true)
                    }
                    getString(R.string.this_season_fragment) -> {

                    }
                    getString(R.string.notification_edit_fragment) -> {

                    }
                }
            }
    }

    fun logout(context: Context) {
        val token = AppSharedPreferences(context).getToken()
        LoginRepository().logout(token)
        Toast.makeText(context, "ログアウトしました", Toast.LENGTH_SHORT).show()
        AppSharedPreferences(context).setIsLogin(false)
        AppSharedPreferences(context).setToken("")
        val nav = NavAction(R.id.nav_graph).destinationId
        Navigation.findNavController(MainActivity(), R.id.my_nav_host_fragment).navigate(nav)
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.my_nav_host_fragment).navigateUp()
}
