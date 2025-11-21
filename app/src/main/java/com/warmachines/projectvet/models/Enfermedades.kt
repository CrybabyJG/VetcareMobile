package com.warmachines.projectvet.models

import com.google.gson.annotations.SerializedName

data class Enfermedades(
    @SerializedName("id") val id: String,
    @SerializedName("codigo_Enfermedades") val Codigo_Enfermedades: String,
    @SerializedName("nombre_Enfermedad") val Nombre_Enfermedades: String
)