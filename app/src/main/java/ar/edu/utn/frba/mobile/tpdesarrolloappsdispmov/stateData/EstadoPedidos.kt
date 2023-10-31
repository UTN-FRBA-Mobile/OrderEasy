package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import kotlinx.coroutines.launch

class EstadoPedidos(private val servicioApi:ReqsService): ViewModel() {
    var state by mutableStateOf(EstadoPedidosMesa())
        private set
    init{
        viewModelScope.launch {
            val pedidos = servicioApi.getStateTable(12)
            if(pedidos.isSuccessful){
                if(pedidos.body() != null){
                    state = state.copy(platosData = pedidos.body()!!.pedidos)
                }
            }
        }
    }
}