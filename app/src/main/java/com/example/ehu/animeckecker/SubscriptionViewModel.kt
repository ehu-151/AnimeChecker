package com.example.ehu.animeckecker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ehu.animeckecker.room.SubscriptionEntity

class SubscriptionViewModel(private val repository: SubscriptionRepository) : ViewModel() {
    private val subscription: MutableLiveData<List<SubscriptionEntity>> = MutableLiveData()

    fun getSubsctiption(): MutableLiveData<List<SubscriptionEntity>> {
        subscription.postValue(repository.getAllSubsctiption())
        return subscription
    }
}
