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
}