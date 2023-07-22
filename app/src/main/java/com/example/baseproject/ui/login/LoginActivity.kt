package com.example.baseproject.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.R
import com.example.baseproject.databinding.ActivityLoginBinding
import com.example.baseproject.ui.base.BaseActivity
import com.example.baseproject.ui.home.HomeActivity
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.extension.error
import com.example.baseproject.utils.extension.loading
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class LoginActivity : BaseActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, LoginActivity::class.java)

        fun getStartCleanIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            val flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(flags)
            return intent
        }
    }

    override fun getLayoutResourceId(): View {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        signInMethod()
        binding.btnLogin.setOnClickListener {
            signIn()
        }

    }

    private fun signInMethod() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResult(task)
            } else {
                Toast.makeText(this, "Something went to wrong", Toast.LENGTH_SHORT).show()
            }

        }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.login(credential)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loginResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                if (result.data) {
                                    parseHome()
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
}