package com.example.test.model.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.common.BaseResponse
import com.example.test.model.LoginModel
import com.example.test.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
        var userRepo = UserRepository()
        val _loginResponse: MutableLiveData<BaseResponse<LoginModel>> = MutableLiveData()


        fun login(_username: String, _password: String) {

            _loginResponse.value = BaseResponse.Loading()
            viewModelScope.launch {
                try {

                    if (_username.toString().isEmpty() || _password.toString().isEmpty()) {
                        // Handle empty username or password
                        Log.e("Aaradhya", "fill your Credentials !")
                    } else {


                        val loginResp =  userRepo.validateLogin(_username, _password)

                        if (loginResp?.code() == 200) {
                            _loginResponse.value =  BaseResponse.Success(loginResp.body())
                        } else {
                            _loginResponse.value = BaseResponse.Error(loginResp?.message())
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Aaradhya", "Error" + e)
                }
            }
        }
}