package com.avalon.vflapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.avalon.vflapp.ui.hub.HubActivity
import com.avalon.vflapp.databinding.ActivityLoginBinding
import com.avalon.vflapp.ui.model.AuthUser
import com.avalon.vflapp.util.DataState
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)
        subscribeObservers()
        val edtVFLMail = binding.loginVflmailEdt
        val edtPassword = binding.loginPasswordEdt
        binding.loginCommitBtn.setOnClickListener {
            val email = edtVFLMail.text.toString()
            val password = edtPassword.text.toString()
            val user = AuthUser(email, password)
            viewModel.signIn(user)
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when(dataState) {
                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val intent = Intent(this, HubActivity::class.java)
                    startActivity(intent)
                    toast("Login Success")
                }
                is DataState.Error -> {
                    when (dataState.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            when(dataState.exception.errorCode) {
                                "ERROR_INVALID_EMAIL" -> {
                                    binding.loginVflmailEdt.error = "E-posta geçersiz"
                                }
                                "ERROR_WRONG_PASSWORD" -> {
                                    toast("Hatalı Giriş bilgileri")
                                }
                                "ERROR_TOO_MANY_REQUESTS" -> {
                                    toast("Zaman aşımına uğradı")
                                }
                            }
                        }
                    }
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Cancel -> {
                    binding.progressBar.visibility = View.GONE
                    toast("Canceled")
                }
                is DataState.Loading -> binding.progressBar.visibility = View.VISIBLE
            }
        }
    }


    private fun toast(mes: String) {
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show()
    }

}