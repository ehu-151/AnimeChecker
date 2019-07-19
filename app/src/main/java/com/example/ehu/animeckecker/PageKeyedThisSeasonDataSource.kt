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
    private val filterSeason: String,
    private val rejectAnimeId: IntArray?
) :
    PageKeyedDataSource<Int, Works>() {

    val networkState = MutableLiveData<Status>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Works>) {
        callAPI(1, params.requestedLoadSize, token, filterSeason, rejectAnimeId) { work, next ->
            callback.onResult(work, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Works>) {
        callAPI(params.key, params.requestedLoadSize, token, filterSeason, rejectAnimeId) { work, next ->
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
        rejectAnimeId: IntArray?,
        callback: (work: MutableList<Works>, next: Int?) -> Unit
    ) {
        Log.d("app_thisseason", "page:$page, perPage$perPage")

        networkState.postValue(Status.RUNNING)

        try {
            // 一覧を取得
            val response =
                service.getWorks(token, page = page, filterSeason = filterSeason, perPage = perPage).execute()

            if (response.code() == 401) {
                networkState.postValue(Status.FAILED_401)
            }

            response.body()?.let {
                var next: Int? = null
                // nextPageがあるなら、インクリメント
                if (it.nextPage != null) next = page + 1

                var data = it.works
                rejectAnimeId?.forEach { reject ->
                    data = data.filterNot { it.id == reject }.toMutableList()
                }

                data.retainAll { it.media == "tv" || it.media == "web" }
                // 表示する
                callback(data, next)
                networkState.postValue(Status.SUCCESS)
            }
        } catch (e: IOException) {
        }
    }
}