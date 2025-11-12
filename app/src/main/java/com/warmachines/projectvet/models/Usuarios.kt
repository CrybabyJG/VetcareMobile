package com.warmachines.projectvet.models

data class Usuarios(
    val username : String,
    val email : String,
    val first_name : String,
    val last_name : String,
    val is_staff : Boolean,
    val is_superuser : Boolean
)