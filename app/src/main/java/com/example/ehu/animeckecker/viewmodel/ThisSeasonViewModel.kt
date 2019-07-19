package com.example.ehu.animeckecker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.ehu.animeckecker.remote.AnnictApiService
import com.example.ehu.animeckecker.remote.ThisSeasonDataSourceFactory
import com.example.ehu.animeckecker.remote.Works
import com.example.ehu.animeckecker.util.Status
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ThisSeasonViewModel() : ViewModel() {
    val api: AnnictApiService
    val config: PagedList.Config
    lateinit var workData: LiveData<PagedList<Works>>
    var netWorkState: MutableLiveData<Status> = MutableLiveData()

    fun loadWork(token: String, filterSeason: String, rejectAnimeIds: IntArray?) {
        val factory = ThisSeasonDataSourceFactory(api, token, filterSeason, rejectAnimeIds)
        netWorkState = factory.source.networkState
        workData = LivePagedListBuilder(factory, config).build()
    }

    init {
        //okhttpのclient作成
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        var retrofit = Retrofit.Builder()
            .baseUrl(AnnictApiService.baseUri)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        api = retrofit.create(AnnictApiService::class.java)


        config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}