package com.warmachines.projectvet.models

import com.google.gson.annotations.SerializedName

data class Clientes(
    @SerializedName("id") val id: String,
    @SerializedName("codigo_Cliente") val Codigo_Cliente: String,
    @SerializedName("nombres") val Nombres: String,
    @SerializedName("apellido1") val Apellido1: String,
    @SerializedName("apellido2") val Apellido2: String,
    @SerializedName("correo") val Correo: String,
    @SerializedName("telefono") val Telefono: String,
    @SerializedName("direccion") val Direccion: String
)