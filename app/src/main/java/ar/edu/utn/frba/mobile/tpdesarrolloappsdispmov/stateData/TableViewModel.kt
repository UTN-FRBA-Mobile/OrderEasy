package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TableViewModel (private val servicioApi: ReqsService): ViewModel() {
    var estadoMesa by mutableStateOf(EstadoPedidosMesaData())
        private set
    init {
        //getPedidosState(5)
        //getConsumosState(5)
    }
    fun getPedidosState (idMesa:Int){
        Log.i("TableviewModel-->",idMesa.toString())
        viewModelScope.launch {
            //estadoMesa = estadoMesa.copy(requestingData = true)
            val pedidos = servicioApi.getStateTable(idMesa)
            Log.i("getPedidosState--->",pedidos.body()!!.toString())
            Log.i("getPedidosState-SUCCESSFULL-->",pedidos.isSuccessful.toString())
            if(pedidos.isSuccessful){
                Log.i("getPedidosState--->","ES SUCCESSFUL!!")
                if(pedidos.body() != null){
                    Log.i("TableViewModel-->","HACIENDO--->REQUEST-API (estado de pedidos)******")
                    estadoMesa = estadoMesa.copy(
                        pedidosMesa = pedidos.body()!!.comensales,
                        requestingData = false
                    )
                }
            }
        }
    }
    fun getConsumosState (idMesa:Int,idCliente: Int){
        Log.i("GETCONSUMOSSTATES----->","X AQUI mesa :"+idMesa.toString())
        viewModelScope.launch {
            //estadoMesa = estadoMesa.copy(requestingData = true)
            val pedidos = servicioApi.getConsumoTable(idMesa)
            delay(1600)
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
            //Log.i("selectedInvited-state--->",state.invitados[indCli].toString())

            /*var news = state.invitados.map{
                fun(e:UserInvitedData):UserInvitedData{
                    return if(e.idCliente==idCliente){
                        UserInvitedData(e.idCliente,e.nombre,e.total,!e.selected)
                    }else{
                        UserInvitedData(e.idCliente,e.nombre,e.total,e.selected)
                    }
                }
            }*/
            //Log.i("selectedInvited-indice--->",indCli.toString())
            //Log.i("selectedInvited-selcted-despues--->",news[indCli].selected.toString())
            estadoMesa = estadoMesa.copy( invitados = mutableListOf())// , requestingData = !bul)
            estadoMesa = estadoMesa.copy( invitados = invitadosAux)
            //Log.i("selectedInvited-news-in-->",news.toString())
            //Log.i("selectedInvited-state-in-->",state.invitados[indCli].toString())
        }
        Log.i("selectedInvited-state-out-->",estadoMesa.invitados.toString())
    }
    fun pagarInvitado(idCliente: Int){
        //
        viewModelScope.launch {
            var param = arrayListOf<Int>()
            estadoMesa.invitados.forEach {
                if (it.selected && it.idCliente!=idCliente) param.add(it.idCliente)
            }
            //var gson = GsonBuilder().setPrettyPrinting().create()
            val reqOrd = servicioApi.pagarInvitados(idCliente, Invitados(pagoscli = param))
            //val reqOrd = servicioApi.makeOrder(idMesa,idCliente,Ordenes(ordenes = param))
            /*if (reqOrd.isSuccessful){
                val msj = reqOrd.body()?.msg
            }*/

            //Limpiar:
            estadoMesa = estadoMesa.copy(
                invitados = mutableListOf(),
            )
        }
    }
    /*fun getInvitados (idMesa: Int) {
        viewModelScope.launch {
            getConsumosState(idMesa)
            Log.i("GETINVITEDS----->","consumos: "+state.consumosMesa.toString())
            state = state.copy(
                invitados = state.consumosMesa.map { c ->
                    UserInvitedData(
                        c.idCliente,
                        c.nombre,
                        c.Pedidos.fold(0.0f ) {acc, i -> acc + i.Plato.precio * i.cantidad.toFloat()},
                        false
                    )}.toTypedArray()
            )
        }
    }*/
}