package com.example.baseproject.ui.firebase.fragment.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.databinding.FragmentListBinding
import com.example.baseproject.ui.base.BaseFragment
import com.example.baseproject.ui.firebase.fragment.list.adapter.TextListAdapter
import com.example.baseproject.ui.firebase.fragment.list.insertDialog.InsertDialog
import com.example.baseproject.ui.firebase.fragment.list.model.ModelList
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.extension.error
import com.example.baseproject.utils.extension.loading
import com.example.baseproject.utils.extension.with
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private val viewModel by viewModels<ListViewModel>()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: TextListAdapter

    companion object {
        const val TAG: String = "ListFragment"
        fun newInstance(): ListFragment = ListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun setup(view: View) {

        binding.btnInsert.setOnClickListener {
            insertDialog()
        }
        viewModel.getList()
    }

    private fun insertDialog() {
        val dialog = InsertDialog.newInstance()
        dialog.isCancelable = false
        dialog.success {
            viewModel.getList()
        }
        dialog.show(childFragmentManager, InsertDialog.TAG)
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

    private fun serData(data: List<ModelList>) {
        listAdapter = TextListAdapter()
        listAdapter.submitList(data)
        binding.listText.with(listAdapter.apply {
            click { item -> Log.d("xxx", "item: $item") }
        })
    }

    override fun dev() {}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}