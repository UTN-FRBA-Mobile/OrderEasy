package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

//import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pedidosApi.ServicioDePedidos
import kotlinx.coroutines.launch

class VistaModeloMesa (private val servicioApi: ServicioDePedidos): ViewModel() {
    var estadoMesa by mutableStateOf(TipoDatoEstadoPedidosMesa())
        private set
    fun obtenerEstadoPedidos (idMesa:Int){
        viewModelScope.launch {
            estadoMesa = estadoMesa.copy(pidiendoDatos = true)
            val pedidos = servicioApi.obtenerEstadoDeMesa(idMesa)
            if(pedidos.isSuccessful){
                if(pedidos.body() != null) {
                    estadoMesa = estadoMesa.copy(pedidosMesa = pedidos.body()!!.comensales, resultPedidoApi = 1)
                }
            }else{
                estadoMesa = estadoMesa.copy( resultPedidoApi = 2)
            }
            estadoMesa = estadoMesa.copy(pidiendoDatos = false)
        }
    }
    fun obtenerEstadoConsumos (idMesa:Int, idCliente: Int){
        viewModelScope.launch {
            estadoMesa = estadoMesa.copy( pidiendoConsumos = true, resultPedidoConsumos = 0)
            val pedidos = servicioApi.obtenerConsumosDeLaMesa(idMesa)
            if(pedidos.isSuccessful){
                if(pedidos.body() != null){
                    val consumComensales: MutableList<ComensalData> = mutableListOf()
                    consumComensales.addAll(pedidos.body()!!.comensales)
                    consumComensales.forEach{ e ->
                        var aux: ArrayList<PedidoData> = ArrayList<PedidoData>()
                        aux.addAll(e.pedidos)
                        e.pedidos.clear()
                        e.pedidos.addAll(aux.filter{it.estado != "PAGANDO"})
                    }
                    estadoMesa = estadoMesa.copy(
                        consumosMesa = pedidos.body()!!.comensales,
                        invitados = consumComensales.map { c ->
                            UserInvitedData(
                                c.idCliente,
                                c.nombre,
                                c.pedidos.fold(0.0f ) { acc, i -> acc + i.plato.precio * i.cantidad.toFloat()},
                                seleccionado= c.idCliente==idCliente,
                                c.pedidos.any {it.estado=="PREPARANDO"}
                            )}.toMutableList(),
                        resultPedidoConsumos = 1,
                        pidiendoConsumos = false
                    )
                }
            }else{
                estadoMesa = estadoMesa.copy( resultPedidoConsumos = 2, pidiendoConsumos = false)
            }
        }
    }
    fun setearPedidoDatos(){
        estadoMesa = estadoMesa.copy(
            pidiendoDatos = true
        )
    }
    fun limpiarPedidoConsumos(){
        estadoMesa = estadoMesa.copy( pidiendoConsumos = false)
    }
    fun seleccionarInvitados (idCliente:Int){
        viewModelScope.launch {
            val invitadosAux: MutableList<UserInvitedData> = mutableListOf()
            invitadosAux.addAll(estadoMesa.invitados)
            val indice =estadoMesa.invitados.indexOfFirst { it.idCliente == idCliente }
            invitadosAux[indice].seleccionado = !estadoMesa.invitados[indice].seleccionado
            estadoMesa = estadoMesa.copy( invitados = mutableListOf())// , requestingData = !bul)
            estadoMesa = estadoMesa.copy( invitados = invitadosAux)
        }
    }
    fun diselectAll(){
        viewModelScope.launch {
            val invitadosAux: MutableList<UserInvitedData> = mutableListOf()
            invitadosAux.addAll(estadoMesa.invitados)
            invitadosAux.forEach{it.seleccionado=false}
            estadoMesa = estadoMesa.copy(invitados = invitadosAux)
        }
    }
    fun pagarInvitado(idCliente: Int) {
        viewModelScope.launch {
            estadoMesa = estadoMesa.copy(pidiendoDatos = true)
            val param = arrayListOf<Int>()
            estadoMesa.invitados.forEach {
                if (it.seleccionado && it.idCliente != idCliente) param.add(it.idCliente)
            }
            val reqOrd = servicioApi.pagarInvitados(idCliente, Invitados(pagoscli = param))

            estadoMesa = if (reqOrd.isSuccessful) {
                //Limpiar
                estadoMesa.copy(
                    invitados = mutableListOf(),
                    resultPedidoApi = 1
                )
            } else {
                estadoMesa.copy(resultPedidoApi = 2)
            }
            estadoMesa = estadoMesa.copy(pidiendoDatos = false)
        }
    }
    fun desactivarErrorPedidoApi(){
        viewModelScope.launch {
            estadoMesa = estadoMesa.copy( resultPedidoApi = 0)
        }
    }
}