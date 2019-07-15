package com.example.ehu.animeckecker.remote

import androidx.paging.DataSource
import com.example.ehu.animeckecker.PageKeyedThisSeasonDataSource

class ThisSeasonDataSourceFactory(
    private val service: AnnictApiService,
    private val token: String,
    private val filterSeason: String,
    private val rejectAnimeId: IntArray?
) : DataSource.Factory<Int, Works>() {
    override fun create(): DataSource<Int, Works> {
        return PageKeyedThisSeasonDataSource(service, token, filterSeason, rejectAnimeId)
    }
}
