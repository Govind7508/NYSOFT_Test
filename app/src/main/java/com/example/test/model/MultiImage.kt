package com.example.test.model

class MultiImage {
    data class ApiResponse(
        val success: Boolean,
        val message: String,
        val data: Data? // Optional; can be null if no additional data is returned
    )

    data class Data(
        val imageUrl: String // For example, the URL of the uploaded image
    )
}