package com.rabin2123.giphyapp.presentation.fragments.fullscreengifs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import com.bumptech.glide.Glide
import com.rabin2123.giphyapp.databinding.FragmentFullscreenGifBinding
import com.rabin2123.giphyapp.presentation.ListOfGifsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FullscreenFragment : Fragment() {
    private val adapter by lazy {
        FullscreenRecyclerAdapter(
            onHidePost = vm::hidePost,
            firstIndex = arguments?.let { FullscreenFragmentArgs.fromBundle(it).currentPosition } ?: 0
        )
    }
    private var binding: FragmentFullscreenGifBinding? = null

    private val vm: ListOfGifsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullscreenGifBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.initUi()
        dataObserver()
    }

    private fun FragmentFullscreenGifBinding.initUi() {
        vpFullscreenGif.adapter = adapter
    }

    private fun dataObserver() {
        lifecycleScope.launch {
            vm.gifList.collectLatest { value ->
                adapter.submitData(value)
            }
        }
    }
}