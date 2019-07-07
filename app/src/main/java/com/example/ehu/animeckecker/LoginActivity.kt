package com.example.ehu.animeckecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ehu.animeckecker.databinding.ActivityLoginBinding
import com.example.ehu.animeckecker.repository.LoginRepository
import com.example.ehu.animeckecker.util.AppSharedPreferences
import com.example.ehu.animeckecker.util.Status
import com.example.ehu.animeckecker.viewmodel.LoginViewModel


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

        viewModel.loadAccesToken(clientId, clientSecret, redirectUrl, code)

        viewModel.tokenData.observe(this, Observer {

            when (it) {
                is Status.Logging -> {

                }
                is Status.Success -> {
                    setTokenToPrefer(it.data.accessToken)
                    intentMain()
                }
                is Status.Failure -> {
                    Toast.makeText(this, "ログインに失敗しました", Toast.LENGTH_SHORT).show()
                }
            }

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
