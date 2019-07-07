package com.example.ehu.animeckecker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.remote.AcceseTokenModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val tokenData: MutableLiveData<AcceseTokenModel> = MutableLiveData()
    fun getAccesToken(
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
        code: String
    ): LiveData<AcceseTokenModel> {
        if (tokenData.value == null) {
            GlobalScope.launch {
                val response = repository.getAccesToken(clientId, clientSecret, redirectUrl, code)
                if (response.isSuccessful) {
                    tokenData.postValue(response.body())
                }
            }
        }
        return tokenData
    }
}