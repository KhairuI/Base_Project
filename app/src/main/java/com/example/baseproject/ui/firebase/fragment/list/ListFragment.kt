package com.example.baseproject.ui.firebase.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.baseproject.databinding.FragmentListBinding
import com.example.baseproject.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment() {

    private val viewModel by viewModels<ListViewModel>()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

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

    }

    override fun observeViewModel() {

    }

    override fun dev() {}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}