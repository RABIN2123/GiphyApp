package com.rabin2123.giphyapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rabin2123.domain.Repository
import com.rabin2123.domain.models.GifsInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

internal class ListOfGifsViewModel(
    private val repository: Repository
) : ViewModel() {
    private val titleGif = MutableSharedFlow<String>(1)

    init {
        searchTitle("")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _gifList: Flow<PagingData<GifsInfoModel.GifItem>> =
        titleGif.flatMapLatest { title ->
            repository.pagerGifList(title)
        }.cachedIn(viewModelScope)

    val gifList = _gifList

    fun searchTitle(title: String) {
        titleGif.tryEmit(title.trim())
    }


    fun hidePost(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.hideGif(id)
        }
    }
}