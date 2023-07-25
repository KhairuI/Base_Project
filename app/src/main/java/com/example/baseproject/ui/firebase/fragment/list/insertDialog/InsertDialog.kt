package com.example.baseproject.ui.firebase.fragment.list.insertDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.DialogInsertBinding
import com.example.baseproject.ui.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

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
            if(isValidate()){

            }
        }
    }

    override fun observeViewModel() {}

    fun success(success: () -> Unit) {
        this.success = success
    }

    fun isValidate(): Boolean {
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