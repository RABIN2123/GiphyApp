package com.rabin2123.giphyapp.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.bumptech.glide.Glide
import com.rabin2123.domain.Repository
import com.rabin2123.domain.models.GifsInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ListOfGifsViewModel(
    private val repository: Repository,
    private val applicationContext: Context
) : ViewModel() {
    private val titleGif = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _gifList: Flow<PagingData<GifsInfoModel.GifItem>> = titleGif.flatMapLatest {title ->
        repository.pagerGifList(title).cachedIn(viewModelScope)
    }

    val gifList = _gifList

    fun searchTitle(title: String) {
        titleGif.update { title }
    }

    fun preLoadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            gifList.collect { pagerItem ->
                pagerItem.map { item ->
                    launch {
                        Glide.with(applicationContext).downloadOnly().load(item.smallPicUrl)
                            .submit()
                    }
                    launch {
                        Glide.with(applicationContext).downloadOnly().load(item.fullPicUrl).submit()
                    }
                }
            }

        }
    }

    fun hidePost(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.hideGif(id)
        }
    }
}