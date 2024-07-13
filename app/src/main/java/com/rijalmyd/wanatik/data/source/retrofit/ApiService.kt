package com.rijalmyd.wanatik.data.source.retrofit

import com.rijalmyd.wanatik.data.source.retrofit.response.ClassifyResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/")
    suspend fun classify(
        @Part file: MultipartBody.Part
    ): ClassifyResponse
}