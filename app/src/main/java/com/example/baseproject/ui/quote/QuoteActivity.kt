package com.example.baseproject.ui.quote

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.databinding.ActivityQuoteBinding
import com.example.baseproject.ui.base.BaseActivity
import com.example.baseproject.ui.quote.adaoter.QuoteListAdapter
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.extension.error
import com.example.baseproject.utils.extension.loading
import com.example.baseproject.utils.extension.with
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuoteActivity : BaseActivity() {

    private val viewModel by viewModels<QuoteViewModel>()

    private lateinit var binding: ActivityQuoteBinding
    private lateinit var quoteAdapter: QuoteListAdapter

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, QuoteActivity::class.java)
    }

    override fun getLayoutResourceId(): View {
        binding = ActivityQuoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {

        quoteAdapter = QuoteListAdapter()
        binding.listQuote.with(quoteAdapter.apply {
            click { item -> Log.d("xxx", "item: $item") }
        })

        binding.ivBack.setOnClickListener { finish() }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getQuoteResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                Log.d("xxx", "Error: ${result.uiText.asString(this@QuoteActivity)}")
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                quoteAdapter.submitList(result.data.quotes)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun dev() {}

}