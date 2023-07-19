package com.example.baseproject.ui.post

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.databinding.ActivityPostBinding
import com.example.baseproject.ui.base.BaseActivity
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.extension.loading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostActivity : BaseActivity() {

    private val viewModel by viewModels<PostViewModel>()

    private lateinit var binding: ActivityPostBinding

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, PostActivity::class.java)
    }

    override fun getLayoutResourceId(): View {
        binding = ActivityPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.ivBack.setOnClickListener { finish() }

        viewModel.getAllPost()
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getAllPostResponse.collect { result ->
                        Log.d("xxx", "observeViewModel: enter")
                        when (result) {
                            is Result.Error -> {
                                Log.d("xxx", "Error: ${result.uiText.asString(this@PostActivity)}")
                                //error(result.uiText)
                            }

                            is Result.Loading -> {
                                Log.d("xxx", "observeViewModel: Loading")
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                Log.d("xxx", "observeViewModel: Success")
                                Log.d("xxx", "post list: ${result.data}")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun dev() {}

}