package com.warmachines.projectvet.models.cubo

data class FactVenta (
    val ID_Venta : Int,
    val ID_Cliente: Int,
    val ID_Medicamento: Int,
    val ID_Tiempo : Int,
    val Cantidad : Int,
    val Total : Double
)