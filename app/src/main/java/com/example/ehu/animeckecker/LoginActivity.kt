package com.example.ehu.animeckecker

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ehu.animeckecker.databinding.ActivityLoginBinding
import android.content.Intent


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val baseUrl = "https://annict.com/oauth/authorize"
    val clientId = "?client_id=NqicoWdMY8vempcSSBVJ5K5Z8pN6HoIqKPBxB2DG6DM"
    val redirectUrl = "&redirect_uri=urn:ietf:wg:oauth:2.0:oob"
    val responseType = "&response_type=code"
    val scope = "&scope=read"
    val clientSecret="kiAVRykpxkSpa83qvrXDxLn7wiu2j5Ja9lKbGFHZLHc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.launchAuthorize.setOnClickListener { launchBrowser() }
        binding.login.setOnClickListener { login() }
    }

    private fun launchBrowser() {
        val uri = Uri.parse(baseUrl + clientId + redirectUrl + responseType + scope)
        val i = Intent(Intent.ACTION_VIEW, uri)
        startActivity(i)
    }

    private fun login() {
        val code = binding.codeText.text
    }

}
