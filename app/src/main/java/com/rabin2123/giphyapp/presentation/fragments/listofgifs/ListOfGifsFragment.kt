package com.rabin2123.giphyapp.presentation.fragments.listofgifs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rabin2123.giphyapp.databinding.FragmentListofGifBinding
import com.rabin2123.giphyapp.presentation.ListOfGifsViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
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


    private val vm: ListOfGifsViewModel by viewModel(ownerProducer = { requireActivity() })
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
        binding?.dataObserver()
    }

    private fun FragmentListofGifBinding.initUi() {
        rvListofGif.adapter = adapter
        srlRecyclerview.setOnRefreshListener {
            vm.searchTitle(etSearchField.text.toString())
            srlRecyclerview.isRefreshing = false
        }

        etSearchField.setOnEditorActionListener { v, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
            }
            true
        }
    }

    @OptIn(FlowPreview::class)
    private fun FragmentListofGifBinding.dataObserver() {
        lifecycleScope.launch {
            vm.gifList.collectLatest { value ->
                adapter.submitData(value)
            }
        }
        lifecycleScope.launch {
            etSearchField.textChanges().debounce(500).distinctUntilChanged().collectLatest {
                if (etSearchField.isFocused) {
                    rvListofGif.scrollToPosition(0)
                    vm.searchTitle(it)
                }
            }
        }
    }


    private fun EditText.textChanges(): Flow<String> {
        return callbackFlow {
            val listener = doOnTextChanged { text, _, _, _ -> trySend(text.toString()) }
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text.toString()) }
    }
}
