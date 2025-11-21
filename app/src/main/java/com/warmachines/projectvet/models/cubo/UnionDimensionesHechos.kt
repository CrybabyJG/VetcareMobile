package com.warmachines.projectvet.models.cubo

data class UnionDimensionesHechos (
    val cliente : DimCliente?,
    val medicamento : DimMedicamento?,
    val tiempo : DimTiempo?,
    val venta : FactVenta?
)