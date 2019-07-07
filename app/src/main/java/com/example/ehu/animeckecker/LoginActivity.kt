package com.example.ehu.animeckecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.ehu.animeckecker.databinding.ActivityLoginBinding
import com.example.ehu.animeckecker.remote.AcceseTokenModel
import com.example.ehu.animeckecker.remote.LoginSevise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val baseUrl = "https://annict.com/oauth/authorize"
    val clientId = "NqicoWdMY8vempcSSBVJ5K5Z8pN6HoIqKPBxB2DG6DM"
    val redirectUrl = "urn:ietf:wg:oauth:2.0:oob"
    val responseType = "code"
    val scope = "read"
    val clientSecret = "kiAVRykpxkSpa83qvrXDxLn7wiu2j5Ja9lKbGFHZLHc"

    var tokenModel: MutableLiveData<AcceseTokenModel> = MutableLiveData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.launchAuthorize.setOnClickListener { launchBrowser() }
        binding.login.setOnClickListener { login() }

        tokenModel.observe(this, Observer {
            Log.d("LoginActivityTAG", "TOKEN:\t" + it?.accessToken)
        })
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
        GlobalScope.launch {
            val data = loginServise.getAcceseToken(
                clientId = clientId,
                client_secret = clientSecret,
                redirectUri = redirectUrl,
                code = code
            ).execute().body()
            Log.d("LoginActivityTAG", "TOKEN:\t" + data?.accessToken)

            tokenModel.postValue(data)
        }
    }

    //Retrofitインターフェース
    private var loginServise: LoginSevise

    init {
        //okhttpのclient作成
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        //クライアント生成

        var retrofit = Retrofit.Builder()
            .baseUrl(LoginSevise.baseUri)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        loginServise = retrofit.create(LoginSevise::class.java)
    }

}
