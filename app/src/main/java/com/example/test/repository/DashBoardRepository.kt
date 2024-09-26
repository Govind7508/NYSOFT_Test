package com.example.test.repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.model.ResponseDataCall
import com.example.test.network.UserApi

class DashBoardRepository {

    private val apiService = UserApi.getApi()


        fun postData(requestData: ResponseDataCall.RequestData): LiveData<ResponseDataCall.ResponseData> {
            val liveData = MutableLiveData<ResponseDataCall.ResponseData>()
            apiService?.postData(requestData)?.enqueue(object : Callback<ResponseDataCall.ResponseData> {
                override fun onResponse(call: Call<ResponseDataCall.ResponseData>, response: Response<ResponseDataCall.ResponseData>) {
                    if (response.isSuccessful) {
                        liveData.postValue(response.body())
                    } else {
                        // Handle error response
                    }
                }

                override fun onFailure(call: Call<ResponseDataCall.ResponseData>, t: Throwable) {
                    // Handle failure
                }
            })
            return liveData
        }
    }

