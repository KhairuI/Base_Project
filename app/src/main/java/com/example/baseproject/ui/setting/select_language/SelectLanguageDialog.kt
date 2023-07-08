package com.example.baseproject.ui.setting.select_language

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.DialogSelectLanguageBinding
import com.example.baseproject.service.BaseProjectApp
import com.example.baseproject.ui.base.BaseDialogFragment
import com.example.baseproject.utils.helper.LocaleManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectLanguageDialog : BaseDialogFragment() {

    private val viewModel by viewModels<SelectLanguageDialogViewModel>()

    private var _binding: DialogSelectLanguageBinding? = null
    private val binding get() = _binding!!

    private var choose: ((language: String) -> Unit)? = null

    companion object {
        const val TAG: String = "SelectLanguageDialog"
        fun newInstance(): SelectLanguageDialog = SelectLanguageDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSelectLanguageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun setup(view: View) {
        when (BaseProjectApp.localeManager?.language) {
            LocaleManager.LANGUAGE_ENGLISH -> binding.rbEn.isChecked = true
            LocaleManager.LANGUAGE_BANGLA -> binding.rbBn.isChecked = true
        }
        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvSave.setOnClickListener { dismiss().also { setLanguage() } }
    }

    private fun setLanguage() {
        when (binding.rgLanguage.checkedRadioButtonId) {
            R.id.rbBn -> choose?.invoke(LocaleManager.LANGUAGE_BANGLA)
            R.id.rbEn -> choose?.invoke(LocaleManager.LANGUAGE_ENGLISH)
        }
    }

    override fun observeViewModel() {}

    fun choose(choose: (language: String) -> Unit) {
        this.choose = choose
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}