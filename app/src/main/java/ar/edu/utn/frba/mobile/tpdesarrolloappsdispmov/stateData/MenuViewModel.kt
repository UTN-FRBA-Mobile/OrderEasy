package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
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
       var ped = estadoMenu.pedidos.singleOrNull { e->e.idPlato==idPlato }
       var peds:MutableList<PlatoPedido> = mutableListOf()
       peds.addAll(estadoMenu.pedidos)
       if(ped == null){
           peds.add(PlatoPedido(idPlato,1))
       }else{
           val indi = peds.indexOf(ped)
           peds.set(indi,PlatoPedido(idPlato,ped.cantidad+1))
       }
        estadoMenu = estadoMenu.copy(pedidos = peds)
    }
    fun delItem(idPlato:Int){
        var ped = estadoMenu.pedidos.singleOrNull { e->e.idPlato==idPlato }
        var peds:MutableList<PlatoPedido> = mutableListOf()
        peds.addAll(estadoMenu.pedidos)
        if(ped != null){
            val ind = peds.indexOf(ped)
            peds.set(ind, PlatoPedido(idPlato,ped.cantidad-1))
            estadoMenu = estadoMenu.copy(pedidos = peds)
        }
    }
}