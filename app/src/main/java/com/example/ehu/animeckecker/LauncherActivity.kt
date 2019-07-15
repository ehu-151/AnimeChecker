package com.example.ehu.animeckecker

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ehu.animeckecker.databinding.ActivityLauncherBinding
import com.example.ehu.animeckecker.util.AppSharedPreferences

class LauncherActivity : AppCompatActivity() {

    lateinit var option: ActivityOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        option = ActivityOptions.makeCustomAnimation(this, 0, android.R.anim.fade_out)

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
        startActivity(intent, option.toBundle())
    }

    private fun intentMain() {
        val intent = Intent(this@LauncherActivity, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent, option.toBundle())
    }
}
