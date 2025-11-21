package com.warmachines.projectvet.API.Clientes

import com.warmachines.projectvet.API.Clientes.APIService_Cliente

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient_Cliente {
    private const val BASE_URL = "https://veterinariamongo20251120233325-dta4aecwajcda4b5.canadacentral-01.azurewebsites.net/api/clientes/"

    fun create(): APIService_Cliente {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val ok = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(ok)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService_Cliente::class.java)
    }
}