package com.warmachines.projectvet.models

import com.google.gson.annotations.SerializedName

data class Laboratorio(
    @SerializedName("id") val id: String,
    @SerializedName("codigo_Laboratorio") val Codigo_Laboratorio: String,
    @SerializedName("nombre_Laboratorio") val Nombre_Laboratorio: String,
    @SerializedName("direccion_Laboratorio") val Direccion_Laboratorio: String,
    @SerializedName("empleado") val Empleado_Laboratorio: String
)