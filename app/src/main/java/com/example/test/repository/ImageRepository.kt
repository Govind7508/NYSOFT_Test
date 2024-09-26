package com.example.test.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.model.MultiImage
import com.example.test.network.UserApi
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File
import retrofit2.Callback
import retrofit2.Response

class ImageRepository {
    private val apiService = UserApi.getApi()

    fun uploadMultipleImages(filePaths: List<String>, description: String): LiveData<MultiImage.ApiResponse> {
        val liveData = MutableLiveData<MultiImage.ApiResponse>()

        val imageParts = filePaths.map { path ->
            val file = File(path)
            val requestFile = file.asRequestBody("image/jpeg".toMediaType())
            MultipartBody.Part.createFormData("images", file.name, requestFile)
        }

        val descriptionBody = description.toRequestBody("text/plain".toMediaType())

        apiService?.uploadMultipleImages(imageParts, descriptionBody)?.enqueue(object : Callback<MultiImage.ApiResponse> {
            override fun onResponse(call: Call<MultiImage.ApiResponse>, response: Response<MultiImage.ApiResponse>) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body())
                } else {
                    liveData.postValue(
                        MultiImage.ApiResponse(
                            success = false,
                            message = "Error: ${response.message()}",
                            data = null
                        )
                    )
                }
            }

            override fun onFailure(call: Call<MultiImage.ApiResponse>, t: Throwable) {
                liveData.postValue(
                    MultiImage.ApiResponse(
                        success = false,
                        message = "Failure: ${t.message}",
                        data = null
                    )
                )
            }
        })

        return liveData
    }
}

fun File.asRequestBody(mediaType: MediaType): RequestBody {
    return RequestBody.create(mediaType, this)
}

fun String.toRequestBody(mediaType: MediaType): RequestBody {
    return RequestBody.create(mediaType, this)
}
