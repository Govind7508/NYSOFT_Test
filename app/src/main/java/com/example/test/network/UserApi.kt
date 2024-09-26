package com.example.test.network

import com.example.test.model.LoginModel
import com.example.test.model.MultiImage
import com.example.test.model.ResponseDataCall
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
interface UserApi {
        @GET("xyz")
        suspend fun validateLogin(
            @Header("Content-Type") contentType: String?,
            @Query("username") username: String?,
            @Query("password") password: String?
        ): Response<LoginModel?>?

    @POST("your/endpoint")
    fun postData(@Body requestData: ResponseDataCall.RequestData): Call<ResponseDataCall.ResponseData>




    @Multipart
    @POST("upload/images")  // Replace with your actual endpoint
    fun uploadMultipleImages(
        @Part images: List<MultipartBody.Part>,
        @Part("description") description: RequestBody
    ): Call<MultiImage.ApiResponse>

    companion object {
            fun getApi(): UserApi? {
                return ApiClient.client?.create(UserApi::class.java)
            }

        }

    }