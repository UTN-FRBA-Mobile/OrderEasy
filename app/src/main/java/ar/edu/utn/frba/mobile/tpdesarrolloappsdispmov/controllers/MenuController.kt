package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.controllers

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.PlatosService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Plato
import kotlinx.coroutines.launch

data class PlatosResponse(val platos:List<Plato> = emptyList())

class Platos(private val servicioApi: PlatosService): ViewModel() {
    var state by mutableStateOf(PlatosResponse())
        private set
    init{
        viewModelScope.launch {
            val platos = servicioApi.getPlatos()
            if(platos.isSuccessful){
                if(platos.body() != null){
                    state = state.copy(platos = platos.body()!!.platos)
                }
            }
        }
    }
}
