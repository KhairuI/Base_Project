package com.example.baseproject.ui.home

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.example.baseproject.databinding.ActivityHomeBinding
import com.example.baseproject.ui.base.BaseActivity
import com.example.baseproject.ui.setting.SettingActivity
import com.example.baseproject.utils.extension.buildTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, HomeActivity::class.java)

        fun getStartCleanIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            val flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(flags)
            return intent
        }
    }

    override fun getLayoutResourceId(): View {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.imgSetting.setOnClickListener { parseSetting() }
    }

    override fun observeViewModel() {}

    override fun dev() {
        binding.tvBuildTime.buildTime()
    }

    private fun parseSetting() = startActivity(SettingActivity.getStartIntent(this))

}