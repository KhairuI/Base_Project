package com.example.baseproject.ui.firebase.fragment.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.baseproject.databinding.FragmentImageBinding
import com.example.baseproject.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : BaseFragment() {

    private val viewModel by viewModels<ImageViewModel>()

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG: String = "ImageFragment"
        fun newInstance(): ImageFragment = ImageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun setup(view: View) {
    }

    override fun observeViewModel() {
    }

    override fun dev() {}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}