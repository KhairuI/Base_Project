package com.example.baseproject.ui.firebase.fragment.image

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baseproject.databinding.FragmentImageBinding
import com.example.baseproject.ui.base.BaseFragment
import com.example.baseproject.ui.firebase.fragment.image.adapter.ImageListAdapter
import com.example.baseproject.ui.firebase.fragment.image.imageDialog.ImageDialog
import com.example.baseproject.ui.firebase.fragment.image.model.ModelImage
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.extension.error
import com.example.baseproject.utils.extension.loading
import com.example.baseproject.utils.extension.with
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageFragment : BaseFragment() {

    private val viewModel by viewModels<ImageViewModel>()

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageAdapter: ImageListAdapter

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
        binding.btnInsert.setOnClickListener {
            insertDialog()
        }
        viewModel.getList()
    }

    private fun insertDialog() {
        val dialog = ImageDialog.newInstance()
        dialog.isCancelable = false
        dialog.success {
            viewModel.getList()
        }
        dialog.show(childFragmentManager, ImageDialog.TAG)
    }

    override fun observeViewModel() {

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getListResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                serData(result.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun serData(data: List<ModelImage>) {

        imageAdapter = ImageListAdapter()
        imageAdapter.getContext(requireContext())
        imageAdapter.submitList(data)
        binding.listText.with(imageAdapter.apply {
            click { item ->
                findNavController().navigate(
                    ImageFragmentDirections.actionImageFragmentToPreviewFragment(
                        item.url
                    )
                )
            }

        })

    }

    override fun dev() {}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}