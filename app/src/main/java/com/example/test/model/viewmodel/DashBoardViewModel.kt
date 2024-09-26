package com.example.test.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.test.model.ResponseDataCall
import com.example.test.repository.DashBoardRepository

class DashBoardViewModel  : ViewModel() {
    private val repository = DashBoardRepository()

    fun postData(requestData: ResponseDataCall.RequestData): LiveData<ResponseDataCall.ResponseData> {
        return repository.postData(requestData)
    }
}