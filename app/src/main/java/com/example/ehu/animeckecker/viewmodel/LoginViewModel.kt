package com.example.ehu.animeckecker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.remote.AcceseTokenModel
import com.example.ehu.animeckecker.repository.LoginRepository
import com.example.ehu.animeckecker.util.Status
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    private val _tokenData: MutableLiveData<AcceseTokenModel> = MutableLiveData()
    val tokenData: LiveData<AcceseTokenModel> = _tokenData
    var status: MutableLiveData<Status> = MutableLiveData()
    fun loadAccesToken(
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
        code: String
    ) {
        if (_tokenData.value == null) {
            GlobalScope.launch {
                val response = repository.getAccessToken(clientId, clientSecret, redirectUrl, code)
//                _tokenData.postValue(Status.Logging)
                if (response.code() == 200) {
                    _tokenData.postValue(response.body()!!)
                } else {
                    status.postValue(Status.FAILED)
                }
            }
        }
    }
}