package com.warmachines.projectvet.API.Enfermedades
import com.warmachines.projectvet.models.Enfermedades
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST

interface APIService_Enfermedades {
    // Lista todas las enfermedades
    @GET(".")
    suspend fun getEnfermedades(): List<Enfermedades>

    @POST(".")
    suspend fun addEnfermedad(@Body enfermedad: Enfermedades): Enfermedades
}