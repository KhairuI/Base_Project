package com.example.baseproject.ui.firebase

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.example.baseproject.databinding.ActivityFirebaseBinding
import com.example.baseproject.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseActivity : BaseActivity() {

    private val viewModel by viewModels<FirebaseViewModel>()

    private lateinit var binding: ActivityFirebaseBinding

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, FirebaseActivity::class.java)
    }

    override fun getLayoutResourceId(): View {
        binding = ActivityFirebaseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

    }

    override fun observeViewModel() {}

    override fun dev() {}

}