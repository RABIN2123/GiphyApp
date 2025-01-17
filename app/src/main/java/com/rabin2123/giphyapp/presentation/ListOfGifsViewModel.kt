package com.rabin2123.giphyapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rabin2123.domain.Repository
import com.rabin2123.domain.models.GifsInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListOfGifsViewModel(private val repository: Repository) : ViewModel() {
    private val _gifList = MutableStateFlow<GifsInfoModel?>(null)
    val gifList: StateFlow<GifsInfoModel?> = _gifList

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            _gifList.update { repository.getGifList(START_PAGE) }
        }
    }

    fun loadNextPage() {
        var currOffset = 0
        gifList.value?.let { item ->
            currOffset = item.offset + if (item.status != -1) CHANGE_PAGE else 0
        }
        viewModelScope.launch(Dispatchers.IO) {
            _gifList.update {
                repository.getGifList(
                page = currOffset
            ) }
        }
    }

    fun searchTitle(title: String) {

    }

    companion object {
        private const val START_PAGE = 0
        private const val CHANGE_PAGE = 50
    }
}