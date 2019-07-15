package com.example.ehu.animeckecker

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.ehu.animeckecker.remote.AnnictApiService
import com.example.ehu.animeckecker.remote.Works
import com.example.ehu.animeckecker.util.Status
import java.io.IOException

class PageKeyedThisSeasonDataSource(
    private val service: AnnictApiService,
    private val token: String,
    private val filterSeason: String
) :
    PageKeyedDataSource<Int, Works>() {

    val networkState = MutableLiveData<Status<List<Works>>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Works>) {
        callAPI(1, params.requestedLoadSize, token, filterSeason) { repos, next ->
            callback.onResult(repos, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Works>) {
        callAPI(params.key, params.requestedLoadSize, token, filterSeason) { work, next ->
            callback.onResult(work, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Works>) {
        // no
    }

    private fun callAPI(
        page: Int,
        perPage: Int,
        token: String,
        filterSeason: String,
        callback: (work: MutableList<Works>, next: Int?) -> Unit
    ) {
        Log.d("app_thisseason", "page:$page, perPage$perPage")

        networkState.postValue(Status.Logging)

        try {
            // 一覧を取得
            val response =
                service.getWorks(token, page = page, filterSeason = filterSeason, perPage = perPage).execute()

            response.body()?.let {
                var next: Int? = null
                // nextPageがあるなら、インクリメント
                if (it.nextPage != null) next = page + 1

                callback(it.works, next)
                networkState.postValue(Status.Success(it.works))
            }
        } catch (e: IOException) {
        }
    }
}