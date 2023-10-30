package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import kotlinx.coroutines.launch

class TableViewModel (private val servicioApi: ReqsService): ViewModel() {
    var state by mutableStateOf(EstadoPedidosMesa())
        private set
    /*init {
        getPedidosState(12)
    }*/
    fun getPedidosState (idMesa:Int){
        Log.i("TableviewModel-->",idMesa.toString())
        viewModelScope.launch {
            state = state.copy(requestingData = true)
            val pedidos = servicioApi.getStateTable(idMesa)
            Log.i("getPedidosState--->",pedidos.body()!!.toString())
            Log.i("getPedidosState-SUCCESSFULL-->",pedidos.isSuccessful.toString())
            if(pedidos.isSuccessful){
                Log.i("getPedidosState--->","ES SUCCESSFUL!!")
                if(pedidos.body() != null){
                    Log.i("TableViewModel-->","HACIENDO--->REQUEST-API (estado de pedidos)******")
                    state = state.copy(
                        pedidosMesa = pedidos.body()!!.pedidos,
                        requestingData = false
                    )
                }
            }
        }
    }
}