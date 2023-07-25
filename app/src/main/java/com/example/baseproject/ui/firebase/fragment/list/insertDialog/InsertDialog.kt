package com.example.baseproject.ui.firebase.fragment.list.insertDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.R
import com.example.baseproject.databinding.DialogInsertBinding
import com.example.baseproject.ui.base.BaseDialogFragment
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.UiText
import com.example.baseproject.utils.extension.error
import com.example.baseproject.utils.extension.loading
import com.example.baseproject.utils.extension.success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InsertDialog : BaseDialogFragment() {

    private val viewModel by viewModels<InsertDialogViewModel>()

    private var _binding: DialogInsertBinding? = null
    private val binding get() = _binding!!

    private var success: (() -> Unit)? = null

    companion object {
        const val TAG: String = "InsertDialog"
        fun newInstance(): InsertDialog = InsertDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogInsertBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun setup(view: View) {

        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvSave.setOnClickListener {
            if (isValidate()) {
                viewModel.insert(getInputText())
            }
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.insertResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                success(UiText.DynamicString(result.data))
                                success?.invoke().also { dismiss() }
                            }
                        }
                    }
                }
            }
        }
    }

    fun success(success: () -> Unit) {
        this.success = success
    }

    private fun isValidate(): Boolean {
        if (getInputText().isEmpty()) {
            binding.layoutText.error = getString(R.string.text_missing)
            return false
        }

        return true
    }

    private fun getInputText(): String = binding.inputText.text.toString()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}