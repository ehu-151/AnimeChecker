package com.example.ehu.animeckecker.remote

import androidx.paging.DataSource
import com.example.ehu.animeckecker.PageKeyedThisSeasonDataSource

class ThisSeasonDataSourceFactory(private val api: AnnictApiService) : DataSource.Factory<Int, AnnictWorksModel>() {
    override fun create(): DataSource<Int, AnnictWorksModel> {
        return PageKeyedThisSeasonDataSource(api)
    }
}
