package com.warmachines.projectvet.API.Cubo

import com.warmachines.projectvet.models.cubo.DimCliente
import com.warmachines.projectvet.models.cubo.DimMedicamento
import com.warmachines.projectvet.models.cubo.DimTiempo
import com.warmachines.projectvet.models.cubo.FactVenta
import retrofit2.http.GET

interface ApiServices {
    @GET("api/DimClientes/DTO")
    suspend fun getClientes(): List<DimCliente>

    @GET("api/DimMedicamentos/DTO")
    suspend fun getMedicamentos(): List<DimMedicamento>

    @GET("api/DimTiempos/DTO")
    suspend fun getTiempo(): List<DimTiempo>

    @GET("api/FactVentas/DTO")
    suspend fun getFactVentas(): List<FactVenta>
}