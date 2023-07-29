package com.example.baseproject.ui.firebase.fragment.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.baseproject.databinding.FragmentPreviewBinding
import com.example.baseproject.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewFragment : BaseFragment() {

    private val viewModel by viewModels<PreviewViewModel>()

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val args: PreviewFragmentArgs by navArgs()

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
        setArgs()

    }

    private fun setArgs() {
        args.url?.let { url ->

        }
    }

    override fun observeViewModel() {}

    override fun dev() {}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}