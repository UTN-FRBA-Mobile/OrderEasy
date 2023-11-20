package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

//import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TableViewModel (private val servicioApi: ReqsService): ViewModel() {
    var estadoMesa by mutableStateOf(EstadoPedidosMesaData())
        private set
    fun getPedidosState (idMesa:Int){
        viewModelScope.launch {
            val pedidos = servicioApi.getStateTable(idMesa)
            if(pedidos.isSuccessful){
                if(pedidos.body() != null){
                    estadoMesa = estadoMesa.copy(
                        pedidosMesa = pedidos.body()!!.comensales,
                        requestingData = false
                    )
                }
            }
        }
    }
    fun getConsumosState (idMesa:Int,idCliente: Int){
        viewModelScope.launch {
            val pedidos = servicioApi.getConsumoTable(idMesa)
            delay(1000)
            if(pedidos.isSuccessful){
                if(pedidos.body() != null){
                    estadoMesa = estadoMesa.copy(
                        consumosMesa = pedidos.body()!!.comensales,
                        invitados = pedidos.body()!!.comensales.map { c ->
                            UserInvitedData(
                                c.idCliente,
                                c.nombre,
                                c.Pedidos.fold(0.0f ) {acc, i -> acc + i.Plato.precio * i.cantidad.toFloat()},
                                selected= c.idCliente==idCliente
                            )}.toMutableList(),
                        requestingData = false
                    )
                }
            }
        }
    }
    fun setRequestingDataOn(){
        estadoMesa = estadoMesa.copy(
            requestingData = true
        )
    }
    fun selectInvited (idCliente:Int){
        viewModelScope.launch {
            val invitadosAux: MutableList<UserInvitedData> = mutableListOf()
            invitadosAux.addAll(estadoMesa.invitados)
            val indice =estadoMesa.invitados.indexOfFirst { it.idCliente == idCliente }
            invitadosAux[indice].selected = !estadoMesa.invitados[indice].selected
            val bul=estadoMesa.requestingData
            estadoMesa = estadoMesa.copy( invitados = mutableListOf())// , requestingData = !bul)
            estadoMesa = estadoMesa.copy( invitados = invitadosAux)
        }
    }
    fun pagarInvitado(idCliente: Int){
        viewModelScope.launch {
            var param = arrayListOf<Int>()
            estadoMesa.invitados.forEach {
                if (it.selected && it.idCliente!=idCliente) param.add(it.idCliente)
            }
            val reqOrd = servicioApi.pagarInvitados(idCliente, Invitados(pagoscli = param))
            //Limpiar:
            estadoMesa = estadoMesa.copy(
                invitados = mutableListOf(),
            )
        }
    }
}