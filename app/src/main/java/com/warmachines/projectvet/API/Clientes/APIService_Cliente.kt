package com.warmachines.projectvet.API.Clientes

import com.warmachines.projectvet.models.Clientes
import retrofit2.http.GET

interface APIService_Cliente {
    // Lista todas las enfermedades
    @GET(".")
    suspend fun getClientes(): List<Clientes>
}