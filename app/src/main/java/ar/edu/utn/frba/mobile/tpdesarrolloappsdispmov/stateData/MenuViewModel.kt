package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

//import android.util.Log
import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            var param = arrayListOf<PlatoOrdenado>()
            estadoMenu.pedidos.forEach{
                if(it.estado == "selected"){
                    var plato = PlatoOrdenado(it.idPlato,it.cantidad)
                    param.add(plato)// += plato
                }
            }
            var gson = GsonBuilder().setPrettyPrinting().create()
            val reqOrd = servicioApi.makeOrder(idMesa,idCliente,Ordenes(ordenes = param))
            if (reqOrd.isSuccessful){
                val msj = reqOrd.body()?.msg
            }

            var peds:MutableList<PlatoPedido> = mutableListOf()
            peds.addAll(estadoMenu.pedidos)
            peds.forEach{
                if(it.estado == "selected") it.estado = "pedido"
            }
            estadoMenu = estadoMenu.copy(pedidos = peds)
        }
    }
}