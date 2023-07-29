package com.example.baseproject.ui.firebase.fragment.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.baseproject.databinding.FragmentPreviewBinding
import com.example.baseproject.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewFragment : BaseFragment() {

    private val viewModel by viewModels<PreviewViewModel>()

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG: String = "PreviewFragment"
        fun newInstance(): PreviewFragment = PreviewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun setup(view: View) {

    }

    override fun observeViewModel() {}

    override fun dev() {}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}