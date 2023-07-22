package com.example.baseproject.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.databinding.ActivitySplashBinding
import com.example.baseproject.ui.base.BaseActivity
import com.example.baseproject.ui.home.HomeActivity
import com.example.baseproject.ui.login.LoginActivity
import com.example.baseproject.utils.arch.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    private lateinit var binding: ActivitySplashBinding

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, SplashActivity::class.java)

        fun getStartCleanIntent(context: Context): Intent {
            val intent = Intent(context, SplashActivity::class.java)
            val flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(flags)
            return intent
        }
    }

    override fun getLayoutResourceId(): View {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.checkUserExistResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                Log.d("xxx", "Error: ${result.uiText.asString(this@SplashActivity)}")
                            }

                            is Result.Loading -> {}

                            is Result.Success -> {
                                if (result.data) {
                                    parseHome()
                                } else {
                                    parseLogin()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun dev() {}

    private fun parseHome() = startActivity(HomeActivity.getStartCleanIntent(this))

    private fun parseLogin() = startActivity(LoginActivity.getStartCleanIntent(this))
}