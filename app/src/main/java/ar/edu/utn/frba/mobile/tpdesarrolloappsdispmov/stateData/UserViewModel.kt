package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import kotlinx.coroutines.launch

class UserViewModel (private val usuarioServicio: ReqsService): ViewModel() {
    var estadoUser by mutableStateOf(UserData())
        private set
    fun log(nomb:String){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(requestingData = true)
            val idCli = usuarioServicio.getLogged(nomb)
            //Log.d("rta---->",idCli.body()!!.idCliente.toString())
            estadoUser = estadoUser.copy(
                nombre = nomb,
                idCliente = idCli.body()!!.idCliente,
                isLogged = false,
                requestingData = false
            )
        }
    }
}