package com.warmachines.projectvet.ui.viewmodel

import com.warmachines.projectvet.models.Usuarios
import com.warmachines.projectvet.API.Usuarios.APIClient_Usuarios

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class UsuariosViewModel : ViewModel() {
    private val api = APIClient_Usuarios.create()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _usuarios = MutableLiveData<List<Usuarios>>(emptyList())
    val usuarios: LiveData<List<Usuarios>> = _usuarios

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun load() {
        if (_loading.value == true) return
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                _usuarios.value = api.getUsuarios()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error de red"
            } finally {
                _loading.value = false
            }
        }
    }
}