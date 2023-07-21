package com.example.baseproject.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.data.database.repository.User
import com.example.baseproject.data.preferences.PreferenceHelper
import com.example.baseproject.databinding.ActivityHomeBinding
import com.example.baseproject.ui.base.BaseActivity
import com.example.baseproject.ui.firebase.FirebaseActivity
import com.example.baseproject.ui.quote.QuoteActivity
import com.example.baseproject.ui.setting.SettingActivity
import com.example.baseproject.utils.AppConstants
import com.example.baseproject.utils.CommonUtil
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.extension.buildTime
import com.example.baseproject.utils.extension.error
import com.example.baseproject.utils.extension.getResString
import com.example.baseproject.utils.extension.loading
import com.example.baseproject.utils.extension.visible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var pref: PreferenceHelper

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
        binding.btnSetUser.setOnClickListener {
            viewModel.setUser(User(1, "khairul", "01515"))
        }
        binding.btnGetUser.setOnClickListener {
            viewModel.getAllUser()
        }
        binding.btnPost.setOnClickListener { parsePost() }
        binding.btnFirebase.setOnClickListener { parseFirebase() }
        binding.btnNotification.setOnClickListener { viewModel.sendNotification() }
        firebaseFCM()

    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getAllUserResponse.collect { result ->
                        Log.d("xxx", "observeViewModel: enter")
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                Log.d("xxx", "observeViewModel: Success")
                                Log.d("xxx", "user list: ${result.data}")
                            }
                        }
                    }
                }

                launch {
                    viewModel.sendNotificationResponse.collect { result ->
                        Log.d("xxx", "observeViewModel: enter")
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                Log.d("xxx", "observeViewModel: Success")
                                Log.d("xxx", "user list: ${result.data}")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun dev() {
        if (AppConstants.IS_BUILD_SHOW) {
            binding.tvBuildTime.visible()
            binding.tvBuildTime.text = getResString(CommonUtil.buildTime())
        }

    }

    private fun parseSetting() = startActivity(SettingActivity.getStartIntent(this))

    private fun parsePost() = startActivity(QuoteActivity.getStartIntent(this))

    private fun parseFirebase() = startActivity(FirebaseActivity.getStartIntent(this))

    private fun firebaseFCM() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            task.result?.let { token ->
                if (token != pref.getDeviceFcm()) {
                    pref.setDeviceFcm(token)
                }
            }
        })
    }

}