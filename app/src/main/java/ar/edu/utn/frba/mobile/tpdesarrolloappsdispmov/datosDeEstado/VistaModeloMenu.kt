package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pedidosApi.ServicioDePedidos
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VistaModeloMenu (private val servicioApi: ServicioDePedidos): ViewModel() {
    var estadoMenu by mutableStateOf(MenuData())
        private set
    fun obtenerMenu (){
        viewModelScope.launch {
            estadoMenu = estadoMenu.copy(loadingMenu = true)
            val reqMenu = servicioApi.obtenerMenuPlatos()
            if(reqMenu.isSuccessful){
                if(reqMenu.body() != null){
                    estadoMenu = estadoMenu.copy(
                        platos = reqMenu.body()!!.platos,
                        loadingMenu = false,
                        menucargado = true
                    )
                }
            }else{
                estadoMenu = estadoMenu.copy( errorPedidoApi = true, loadingMenu = false, menucargado = false)
            }
        }
    }
    fun sumarItem(idPlato:Int){
       var ped = estadoMenu.pedidos.singleOrNull { e->(e.idPlato==idPlato && e.estado=="selected")}
       var peds:MutableList<PlatoPedido> = mutableListOf()
       peds.addAll(estadoMenu.pedidos)
       if(ped == null){
           peds.add(PlatoPedido(idPlato,1,"selected"))
       }else{
           val indi = peds.indexOf(ped)
           peds.set(indi,PlatoPedido(idPlato,ped.cantidad+1,"selected"))
       }
        estadoMenu = estadoMenu.copy(pedidos = peds)
    }
    fun restarItem(idPlato:Int){
        var ped = estadoMenu.pedidos.singleOrNull { e->(e.idPlato==idPlato && e.estado=="selected") }
        var peds:MutableList<PlatoPedido> = mutableListOf()
        peds.addAll(estadoMenu.pedidos)
        if(ped != null){
            if ( ped.cantidad == 1){
                val ind = peds.indexOf(ped)
                peds.removeAt(ind)
            }else{
                val ind = peds.indexOf(ped)
                peds.set(ind, PlatoPedido(idPlato,ped.cantidad-1,"selected"))
            }
            estadoMenu = estadoMenu.copy(pedidos = peds)
        }
    }
    fun ordenarItem(idMesa:Int, idCliente:Int){
        viewModelScope.launch {
            estadoMenu = estadoMenu.copy( pidiendoDatos = true)
            var param = arrayListOf<PlatoOrdenado>()
            estadoMenu.pedidos.forEach{
                if(it.estado == "selected"){
                    var plato = PlatoOrdenado(it.idPlato,it.cantidad)
                    param.add(plato)// += plato
                }
            }
            val reqOrd = servicioApi.ordenar(idMesa,idCliente,Ordenes(ordenes = param))
            if (reqOrd.isSuccessful){
                var peds:MutableList<PlatoPedido> = mutableListOf()
                peds.addAll(estadoMenu.pedidos)
                peds.forEach{
                    if(it.estado == "selected") it.estado = "pedido"
                }
                estadoMenu = estadoMenu.copy(pedidos = peds)
            }else{
                estadoMenu = estadoMenu.copy( errorPedidoApi = true)
            }
            estadoMenu = estadoMenu.copy( pidiendoDatos = false)
        }
    }
    fun cancelErrorReqApi(){
        viewModelScope.launch {
            estadoMenu = estadoMenu.copy( errorPedidoApi = false)
        }
    }
    fun obtenerPrecioCarroTotal(): Float {
        var total = 0.0f
        estadoMenu.pedidos.forEach {
            var t = estadoMenu.platos.find { s -> (s.idPlato==it.idPlato && it.estado=="selected")}
            if (t != null) {
                total = total + t.precio * it.cantidad
            }
        }
        return total
    }
}