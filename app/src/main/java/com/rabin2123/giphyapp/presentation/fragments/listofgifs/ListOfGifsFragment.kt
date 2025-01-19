package com.rabin2123.giphyapp.presentation.fragments.listofgifs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rabin2123.giphyapp.databinding.FragmentListofGifBinding
import com.rabin2123.giphyapp.presentation.ListOfGifsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ListOfGifsFragment : Fragment() {
    private val adapter by lazy {
        ListOfGifsRecyclerAdapter(
            onOpenFullScreen = onOpenFullScreen,
            onHidePost = vm::hidePost
        )
    }

    private val onOpenFullScreen: (Int) -> Unit = { position ->
        val action = ListOfGifsFragmentDirections.aMainScreenToFragmentSlidePicture()
            .setCurrentPosition(position)
        findNavController().navigate(action)
    }


    private val vm: ListOfGifsViewModel by viewModel(ownerProducer = {requireActivity()})
    private var binding: FragmentListofGifBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListofGifBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.initUi()
        dataObserver()
    }

    private fun FragmentListofGifBinding.initUi() {
        rvListofGif.adapter = adapter
        etSearchField.doAfterTextChanged {
            vm.searchTitle(etSearchField.text.toString())
        }
    }

    private fun dataObserver() {
        lifecycleScope.launch {
            vm.gifList.collectLatest { value ->
                adapter.submitData(value)
            }
        }
    }
}
