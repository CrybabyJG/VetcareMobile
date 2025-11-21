package com.warmachines.projectvet.ui.viewmodel

import com.warmachines.projectvet.models.Laboratorio
import com.warmachines.projectvet.API.Laboratorio.APIClient_Laboratorio

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class LaboratorioViewModel : ViewModel() {
    private val api = APIClient_Laboratorio.create()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _enfermedades = MutableLiveData<List<Laboratorio>>(emptyList())
    val laboratorio: LiveData<List<Laboratorio>> = _enfermedades

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun load() {
        if (_loading.value == true) return
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                _enfermedades.value = api.getLaboratorios()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error de red"
            } finally {
                _loading.value = false
            }
        }
    }
}