package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.InitNotifications
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.launch
class UserViewModel (private val usuarioServicio: ReqsService): ViewModel() {
    var estadoUser by mutableStateOf(UserData())
        private set
    fun log(nomb:String){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(requestingData = true)
            val idCli = usuarioServicio.getLogged(nomb,estadoUser.idDevice)
            Log.d("rta---->",idCli.body()!!.toString())
            estadoUser = estadoUser.copy(
                nombre = nomb,
                idCliente = idCli.body()!!.idCliente,
                isLogged = true,
                requestingData = false
            )
        }
    }
    fun setIdDevice(id:String) {
        viewModelScope.launch {
            estadoUser = estadoUser.copy(idDevice = id)
        }
    }
    suspend fun setDataSt(nombre:String,estado:String){

    }

}