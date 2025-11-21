package com.warmachines.projectvet.ui.viewmodel
import com.warmachines.projectvet.models.Enfermedades
import com.warmachines.projectvet.API.Enfermedades.APIClient_Enfermedades

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class EnfermedadesViewModel : ViewModel() {
    private val api = APIClient_Enfermedades.create()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _enfermedades = MutableLiveData<List<Enfermedades>>(emptyList())
    val enfermedades: LiveData<List<Enfermedades>> = _enfermedades

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun load() {
        if (_loading.value == true) return
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                _enfermedades.value = api.getEnfermedades()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error de red"
            } finally {
                _loading.value = false
            }
        }
    }
}