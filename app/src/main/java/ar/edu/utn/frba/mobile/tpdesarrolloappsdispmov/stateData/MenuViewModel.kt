package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import java.lang.reflect.Array

class MenuViewModel (private val servicioApi: ReqsService): ViewModel() {
    var estadoMenu by mutableStateOf(MenuData())
        private set
    init {
        getMenu()
    }
    fun getMenu (){
        viewModelScope.launch {
            estadoMenu = estadoMenu.copy(loadingMenu = true)
            val reqMenu = servicioApi.getMenu()
            if(reqMenu.isSuccessful){
                if(reqMenu.body() != null){
                    Log.i("MenuViewModel-->","HACIENDO--->REQUEST-API (traer menu)******")
                    estadoMenu = estadoMenu.copy(
                        platos = reqMenu.body()!!.platos,
                        loadingMenu = false
                    )
                }
            }
        }
    }
    fun addItem(idPlato:Int){
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
    fun delItem(idPlato:Int){
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
    fun orderItem(idMesa:Int,idCliente:Int){
        Log.i("ENCARGANDO--->",estadoMenu.pedidos.toString())
        viewModelScope.launch {
            var param = arrayListOf<PlatoOrdenado>()
            estadoMenu.pedidos.forEach{
                if(it.estado == "selected"){
                    //param.plus(PlatoOrdenado(it.idPlato,it.cantidad))
                    var plato = PlatoOrdenado(it.idPlato,it.cantidad)
                    Log.i("Encargando-plato->",plato.toString())
                    param.add(plato)// += plato
                    Log.i("Encargando(launch)-0-->",param.toString())
                }
            }
            Log.i("Encargando(launch)-->",param.toString())
            Log.i("Encargando(launch)-->",Ordenes(ordenes=param).toString())
            var gson = GsonBuilder().setPrettyPrinting().create()
            Log.i("Encargando(launch)-->",gson.toJson(Ordenes(ordenes=param)).toString())
            val reqOrd = servicioApi.makeOrder(idMesa,idCliente,Ordenes(ordenes = param))
            Log.i("Encargando-Request-message-->",reqOrd.message())
            Log.i("Encargando-Request->",reqOrd.toString())
            Log.i("Encargando-Request-code->",reqOrd.code().toString())
            if (reqOrd.isSuccessful){
                val msj = reqOrd.body()?.msg
            }

            var peds:MutableList<PlatoPedido> = mutableListOf()
            peds.addAll(estadoMenu.pedidos)
            Log.i("ENCARGANDO--->",estadoMenu.pedidos.toString())
            peds.forEach{
                if(it.estado == "selected") it.estado = "pedido"
            }
            estadoMenu = estadoMenu.copy(pedidos = peds)
        }
    }
}