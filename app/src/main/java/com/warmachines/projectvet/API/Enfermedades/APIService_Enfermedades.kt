package com.warmachines.projectvet.API.Enfermedades
import com.warmachines.projectvet.models.Enfermedades

import retrofit2.http.GET

interface APIService_Enfermedades {
    @GET(".")
    suspend fun getEnfermedades(): List<Enfermedades>
}