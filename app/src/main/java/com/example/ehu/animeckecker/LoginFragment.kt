package com.example.ehu.animeckecker


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.FragmentLoginBinding
import com.example.ehu.animeckecker.repository.LoginRepository
import com.example.ehu.animeckecker.util.AppSharedPreferences
import com.example.ehu.animeckecker.util.Status
import com.example.ehu.animeckecker.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    val baseUrl = "https://annict.com/oauth/authorize"
    val clientId = "NqicoWdMY8vempcSSBVJ5K5Z8pN6HoIqKPBxB2DG6DM"
    val redirectUrl = "urn:ietf:wg:oauth:2.0:oob"
    val responseType = "code"
    val scope = "read"
    val clientSecret = "kiAVRykpxkSpa83qvrXDxLn7wiu2j5Ja9lKbGFHZLHc"

    var isToken = false
    var isLogin = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // loginしているなら、main画面へintent
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
                    setLoginInfoToPrefer(it.data.accessToken)
                    Navigation.findNavController(binding.root).navigate(R.id.action_login_go_back)
                }
                is Status.Failure -> {
                    Toast.makeText(context, "ログインに失敗しました", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setLoginInfoToPrefer(token: String) {
        AppSharedPreferences(context!!).setToken(token)
        AppSharedPreferences(context!!).setIsLogin(true)
    }

    private fun loginCheck() {
        isLogin = AppSharedPreferences(context!!).getIsLogin()
        if (isLogin) {
            Navigation.findNavController(binding.root).navigate(R.id.action_login_go_back)
        }
    }
}
