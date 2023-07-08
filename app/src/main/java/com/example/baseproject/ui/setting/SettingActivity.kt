package com.example.baseproject.ui.setting

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.ActivitySettingBinding
import com.example.baseproject.service.BaseProjectApp
import com.example.baseproject.ui.base.BaseActivity
import com.example.baseproject.ui.home.HomeActivity
import com.example.baseproject.ui.setting.select_language.SelectLanguageDialog
import com.example.baseproject.utils.extension.onBackAction
import com.example.baseproject.utils.helper.LocaleManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity() {

    private val viewModel by viewModels<SettingViewModel>()

    private lateinit var binding: ActivitySettingBinding

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, SettingActivity::class.java)
    }

    override fun getLayoutResourceId(): View {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.btLanguage.text =
            when (BaseProjectApp.localeManager?.language == LocaleManager.LANGUAGE_BANGLA) {
                true -> getString(R.string.bangla)
                else -> getString(R.string.english)
            }

        binding.btLanguage.setOnClickListener { changeLangDialog() }
        binding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        onBackAction(::backAction)
    }

    private fun changeLangDialog() {
        val dialog = SelectLanguageDialog.newInstance()
        dialog.choose { language ->
            BaseProjectApp.localeManager?.setNewLocale(this, language)
            recreate()
            overridePendingTransition(0, 0)
        }
        dialog.show(supportFragmentManager, SelectLanguageDialog.TAG)
    }

    override fun observeViewModel() {}

    override fun dev() {}

    private fun backAction() {
        startActivity(HomeActivity.getStartCleanIntent(this@SettingActivity))
        overridePendingTransition(0, 0)
        finish()
    }


}