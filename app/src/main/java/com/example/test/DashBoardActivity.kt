package com.example.test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.test.databinding.ActivityDashBoardBinding
import com.example.test.model.ResponseDataCall
import com.example.test.model.viewmodel.DashBoardViewModel

class DashBoardActivity : AppCompatActivity() {
    private val viewModel: DashBoardViewModel by viewModels()
    private lateinit var binding: ActivityDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board)
        binding.postDetails = viewModel

        // Create your request data
        val requestData = ResponseDataCall.RequestData("keyExample", "valueExample")

        // Observe LiveData from ViewModel
        viewModel.postData(requestData).observe(this, Observer { responseData ->
            // Update the UI with response data
            binding.text.text = responseData?.result
        })
    }
}