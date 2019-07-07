package com.example.ehu.animeckecker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.remote.AnnictWorksModel
import com.example.ehu.animeckecker.repository.ThisSeasonRepositoty
import com.example.ehu.animeckecker.util.Status
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ThisSeasonViewModel : ViewModel() {
    private val repository = ThisSeasonRepositoty()
    private val _workData: MutableLiveData<Status<AnnictWorksModel>> = MutableLiveData()
    val workData: LiveData<Status<AnnictWorksModel>> = _workData

    fun loadWorks(token: String, filterSeason: String? = null) {
        _workData.value = Status.Logging

        GlobalScope.launch {
            val response = repository.getWorks(filterSeason, token)
            if (response.code() == 200) {
                _workData.postValue(Status.Success(response.body()!!))
            } else {
                _workData.postValue(Status.Failure(Throwable(response.errorBody().toString())))
            }
        }
    }
}