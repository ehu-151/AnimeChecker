package com.example.ehu.animeckecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ehu.animeckecker.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val baseUrl = "https://annict.com/oauth/authorize"
    val clientId = "NqicoWdMY8vempcSSBVJ5K5Z8pN6HoIqKPBxB2DG6DM"
    val redirectUrl = "urn:ietf:wg:oauth:2.0:oob"
    val responseType = "code"
    val scope = "read"
    val clientSecret = "kiAVRykpxkSpa83qvrXDxLn7wiu2j5Ja9lKbGFHZLHc"

    var isToken = false
    var isLogin = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        loginCheck()

        binding.launchAuthorize.setOnClickListener { launchBrowser() }
        binding.login.setOnClickListener { login() }

    }

    private fun launchBrowser() {
        val uri = Uri.parse(
            baseUrl +
                    "?client_id=" + clientId +
                    "&redirect_uri=" + redirectUrl +
                    "&response_type=" + responseType +
                    "&scope=" + scope
        )
        val i = Intent(Intent.ACTION_VIEW, uri)
        startActivity(i)
    }

    private fun login() {
        val code = binding.codeText.text.toString()
        val viewModel = LoginViewModel(LoginRepository())
        viewModel.getAccesToken(clientId, clientSecret, redirectUrl, code).observe(this, Observer {
            Log.d("LoginActivityTAG", "TOKEN:\t" + it.accessToken)
            setTokenToPrefer(it.accessToken)
            intentMain()
        })
    }

    private fun setTokenToPrefer(token: String) {
        AppSharedPreferences(this).setToken(token)
    }

    private fun loginCheck() {

    }

    private fun intentMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
