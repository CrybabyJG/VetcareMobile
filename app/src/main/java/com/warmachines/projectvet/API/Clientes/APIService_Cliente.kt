package com.warmachines.projectvet.API.Clientes

import com.warmachines.projectvet.models.Clientes
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST

interface APIService_Cliente {
    // Lista todas las enfermedades
    @GET(".")
    suspend fun getClientes(): List<Clientes>

    @POST(".")
    suspend fun addCliente(@Body cliente: Clientes): Clientes
}