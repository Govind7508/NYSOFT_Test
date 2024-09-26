package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.test.common.BaseResponse
import com.example.test.databinding.ActivityMainBinding
import com.example.test.model.LoginModel
import com.example.test.model.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityBinding.userDetails = viewModel
        submit()
        loginObserver()

    }


    fun submit() {
        mainActivityBinding.rlSubmit.setOnClickListener {

            if (!mainActivityBinding.rlUsername.text.toString()
                    .isEmpty() || !mainActivityBinding.rlPassword.text.toString().isEmpty()
            ) {
                var username: String = mainActivityBinding.rlUsername.text.toString()
                var password: String = mainActivityBinding.rlPassword.text.toString()
                viewModel.login(username, password)
            }
        }
        mainActivityBinding.rlImage.setOnClickListener {
            val intent = Intent(this@MainActivity, ImageUploadActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginObserver() {
        viewModel._loginResponse.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is BaseResponse.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

                is BaseResponse.Success -> {
                    lifecycleScope.launch {
                        loginDetails(it)

                    }

                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun loginDetails(it: BaseResponse.Success<LoginModel>) {

        val intent = Intent(this@MainActivity, DashBoardActivity::class.java)
        startActivity(intent)
        finish()
    }

}
