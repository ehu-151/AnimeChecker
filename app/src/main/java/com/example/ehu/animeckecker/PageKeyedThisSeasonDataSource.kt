package com.example.ehu.animeckecker

import androidx.paging.PageKeyedDataSource
import com.example.ehu.animeckecker.remote.AnnictWorksModel

class PageKeyedThisSeasonDataSource() : PageKeyedDataSource<Int, AnnictWorksModel>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, AnnictWorksModel>) {
        // コールバックに結果と前のページ、次のページを渡す
//        callback.onResult(result, null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AnnictWorksModel>) {
        // コールバックに結果とその次のページを渡す
//        callback.onResult(result, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AnnictWorksModel>) {
        // コールバックに結果とその前のページを渡す
//        callback.onResult(result, params.key - 1)
    }
}