package com.example.ehu.animeckecker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.remote.AcceseTokenModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val _tokenData: MutableLiveData<Status<AcceseTokenModel>> = MutableLiveData()
    val tokenData: LiveData<Status<AcceseTokenModel>> = _tokenData
    fun loadAccesToken(
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
        code: String
    ) {
        if (_tokenData.value == null) {
            GlobalScope.launch {
                val response = repository.getAccessToken(clientId, clientSecret, redirectUrl, code)
                _tokenData.postValue(Status.Logging)
                Log.d("LoginActivityTAG", response.code().toString())

                if (response.code() == 200) {
                    _tokenData.postValue(Status.Success(response.body()!!))
                } else {
                    _tokenData.postValue(Status.Failure(Throwable(response.errorBody().toString())))
                }
            }
        }
    }
}