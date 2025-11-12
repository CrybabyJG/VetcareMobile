package com.warmachines.projectvet.ui.viewmodel

import com.warmachines.projectvet.models.Clientes
import com.warmachines.projectvet.API.Clientes.APIClient_Cliente

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ClientesViewModel : ViewModel() {
    private val api = APIClient_Cliente.create()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _clientes = MutableLiveData<List<Clientes>>(emptyList())
    val clientes: LiveData<List<Clientes>> = _clientes

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun load() {
        if (_loading.value == true) return
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                _clientes.value = api.getClientes()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error de red"
            } finally {
                _loading.value = false
            }
        }
    }

    //Agregar cliente vía POST
    fun addCliente(codigoCliente: String, nombres: String, apellido1: String, apellido2: String, correo: String, telefono: String, direccion: String) {
        if (_loading.value == true) return
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                //creamos el objeto Clientes para mandar al backend
                val nueva = Clientes(
                    Codigo_Cliente = codigoCliente,        // ajusta según tu modelo (si es "ID_Enfermedad")
                    Nombres = nombres,
                    Apellido1 = apellido1,
                    Apellido2 = apellido2,
                    Correo = correo,
                    Telefono = telefono,
                    Direccion = direccion
                )

                api.addCliente(nueva)

                // Luego refrescamos la lista
                _clientes.value = api.getClientes()

            } catch (e: Exception) {
                _error.value = e.message ?: "Error al agregar"
            } finally {
                _loading.value = false
            }
        }
    }
}