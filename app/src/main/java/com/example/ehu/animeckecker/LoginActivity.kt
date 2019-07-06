package com.example.ehu.animeckecker

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import com.example.ehu.animeckecker.databinding.ActivityLoginBinding
import android.content.Intent




class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val base_url = "https://annict.com/oauth/authorize"
    val client_id = "?client_id=jBqFV693ClvBF2bhIUJtzwr2WDlGmLf7YRWMlKBiU4I"
    val redirect_url = "&redirect_uri=urn:ietf:wg:oauth:2.0:oob"
    val response_type = "&response_type=code"
    val scope = "&scope=read"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.launchAuthorize.setOnClickListener { launchBrowser() }
    }

    private fun launchBrowser() {
        val uri = Uri.parse(base_url + client_id + redirect_url + response_type + scope)
        val i = Intent(Intent.ACTION_VIEW, uri)
        startActivity(i)
    }

}
