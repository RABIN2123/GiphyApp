package com.rabin2123.giphyapp.presentation.fragments.listofgifs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rabin2123.domain.models.GifsInfoModel
import com.rabin2123.giphyapp.R
import com.rabin2123.giphyapp.databinding.FragmentListofGifBinding
import com.rabin2123.giphyapp.presentation.ListOfGifsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListOfGifsFragment : Fragment() {
    private val adapter by lazy {
        ListOfGifsRecyclerAdapter(
            onItemClicked = onItemClicked,
            onLoadNextPage = vm::loadNextPage
        )
    }

    private val onItemClicked: (GifsInfoModel.GifItem) -> Unit = { item ->
        findNavController().navigate(R.id.a_main_screen_to_fragment_slide_picture)
    }


    private val vm: ListOfGifsViewModel by viewModel()
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
        btnSearch.setOnClickListener {
            vm.searchTitle(etSearchField.text.toString())
        }
    }

    private fun dataObserver() {
        lifecycleScope.launch {
            vm.gifList.collect { value ->
                adapter.submitList(value?.gifList)
            }
        }
    }
}
