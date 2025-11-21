package com.warmachines.projectvet.API.Laboratorio
import com.warmachines.projectvet.models.Laboratorio

import retrofit2.http.GET

interface APIServices_Laboratorio {
    @GET(".")
    suspend fun getLaboratorios(): List<Laboratorio>
}