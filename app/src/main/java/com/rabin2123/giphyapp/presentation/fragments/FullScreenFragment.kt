package com.rabin2123.giphyapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rabin2123.giphyapp.databinding.FragmentFullscreenGifBinding

class FullScreenFragment : Fragment() {
    private var binding: FragmentFullscreenGifBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullscreenGifBinding.inflate(inflater, container, false)
        return binding?.root
    }
}