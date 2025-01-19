package com.rabin2123.giphyapp.presentation.fragments.fullscreengifs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.rabin2123.giphyapp.databinding.FragmentFullscreenGifBinding
import com.rabin2123.giphyapp.presentation.ListOfGifsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FullscreenFragment : Fragment() {
    private val selectedIndex by lazy {
        arguments?.let { FullscreenFragmentArgs.fromBundle(it).currentPosition } ?: 0
    }

    private val adapter by lazy {
        FullscreenRecyclerAdapter(
            onHidePost = vm::hidePost,
            firstIndex = selectedIndex
        )
    }
    private var binding: FragmentFullscreenGifBinding? = null

    private val vm: ListOfGifsViewModel by viewModel(ownerProducer = {requireActivity()})

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
        dataObserver()
        viewModelStore
        binding?.initUi()
    }

    private fun FragmentFullscreenGifBinding.initUi() {
        vpFullscreenGif.adapter = adapter
    }

    private var checkPos = true

    private fun dataObserver() {
        lifecycleScope.launch {
            vm.gifList.collectLatest { value ->
                adapter.submitData(value)
                if (checkPos) {
                    binding?.vpFullscreenGif?.setCurrentItem(selectedIndex, false)
                    checkPos = false
                }
            }
        }
    }
}