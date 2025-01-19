package com.rabin2123.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.rabin2123.data.CombinedDataRepository
import com.rabin2123.domain.models.GifsInfoModel
import com.rabin2123.domain.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepositoryImpl(private val combinedDataRepo: CombinedDataRepository) : Repository {

    override fun pagerGifList(title: String): Flow<PagingData<GifsInfoModel.GifItem>> {
        return combinedDataRepo.pagerGifList(title).map { it.map { item -> item.toDomain() } }
    }


    override suspend fun hideGif(id: String) {
        combinedDataRepo.hideGif(id)
    }
}