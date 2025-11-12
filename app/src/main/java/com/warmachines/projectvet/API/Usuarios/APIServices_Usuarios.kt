package com.warmachines.projectvet.API.Usuarios

import com.warmachines.projectvet.models.Usuarios

import retrofit2.http.GET

interface APIServices_Usuarios {
    // Lista todos los usuarios
    @GET(".")
    suspend fun getUsuarios(): List<Usuarios>
}