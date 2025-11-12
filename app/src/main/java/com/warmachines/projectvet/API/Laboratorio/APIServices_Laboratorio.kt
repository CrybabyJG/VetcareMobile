package com.warmachines.projectvet.API.Laboratorio
import com.warmachines.projectvet.models.Laboratorio
import com.warmachines.projectvet.models.MostrarLaboratorio
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST

interface APIServices_Laboratorio {
    // Lista todas las enfermedades
    @GET(".")
    suspend fun getLaboratorios(): List<MostrarLaboratorio>

    @POST(".")
    suspend fun addLaboratorio(@Body laboratorio: Laboratorio): Laboratorio
}