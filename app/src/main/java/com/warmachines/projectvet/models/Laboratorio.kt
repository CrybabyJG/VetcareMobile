package com.warmachines.projectvet.models

data class MostrarLaboratorio(
    val Codigo_Laboratorio: String,
    val Nombre_Laboratorio: String,
    val Direccion_Laboratorio: String
)

data class Laboratorio(
    val Codigo_Laboratorio: String,
    val Nombre_Laboratorio: String,
    val Direccion_Laboratorio: String,
    val Empleado: String
)