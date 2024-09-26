package com.example.test.repository

import android.util.Log
import com.example.test.common.AppConst
import com.example.test.model.LoginModel
import com.example.test.model.ResponseDataCall
import com.example.test.network.UserApi
import retrofit2.Call
import retrofit2.Response

class UserRepository() {

    suspend fun validateLogin(username: String, password: String): Response<LoginModel?>? {
        val response = UserApi.getApi()?.validateLogin(AppConst.CONTENT_TYPE, username, password)
        Log.e("Aaradhya", "Response code: ${response?.code()}")
        Log.e("Aaradhya", "Response body: ${response?.body()}")
        return response
    }


}