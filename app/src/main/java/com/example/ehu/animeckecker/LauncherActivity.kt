package com.example.ehu.animeckecker

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ehu.animeckecker.databinding.ActivityLauncherBinding
import com.example.ehu.animeckecker.util.AppSharedPreferences

class LauncherActivity : AppCompatActivity() {

    lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher)

        val isLogin = AppSharedPreferences(this).getIsLogin()
        if (isLogin) {
            intentMain()
        } else {
            intentLogin()
        }
    }


    private fun intentLogin() {
        val intent = Intent(this@LauncherActivity, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val option = ActivityOptions.makeScaleUpAnimation(binding.root, 10, 10, 100, 100)
        startActivity(intent, option.toBundle())
    }

    private fun intentMain() {
        val intent = Intent(this@LauncherActivity, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val option = ActivityOptions.makeScaleUpAnimation(binding.root, 10, 10, 100, 100)
        startActivity(intent, option.toBundle())
    }
}
