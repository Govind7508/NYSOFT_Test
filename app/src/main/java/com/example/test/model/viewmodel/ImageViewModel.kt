package com.example.test.model.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.model.MultiImage
import com.example.test.repository.ImageRepository

class ImageViewModel : ViewModel() {
    private val repository = ImageRepository()

    private val _imageUris = MutableLiveData<List<String>>()
    val imageUris: LiveData<List<String>> get() = _imageUris

    private val _uploadStatus = MutableLiveData<MultiImage.ApiResponse>()
    val uploadStatus: LiveData<MultiImage.ApiResponse> get() = _uploadStatus

    fun setImageUris(filePaths: MutableList<String>) {
        _imageUris.value = filePaths
    }

    fun uploadImages(filePaths: List<String>, description: String) {
        repository.uploadMultipleImages(filePaths, description).observeForever {
            _uploadStatus.postValue(it)
        }
    }
}