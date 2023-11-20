package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

//import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.socket.SocketManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
class UserViewModel (private val usuarioServicio: ReqsService,private val dataStore: DataStore<Preferences>): ViewModel() {
    var estadoUser by mutableStateOf(UserData())
        private set
    fun log(nomb:String){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(requestingData = true)
            //API REQUEST
            val idCli = usuarioServicio.getLogged(nomb,estadoUser.idDevice)
            dataStore.edit { preferences ->
                preferences[intPreferencesKey("idCliente")] = idCli.body()!!.idCliente
                preferences[stringPreferencesKey("idDevice")] =estadoUser.idDevice
                preferences[stringPreferencesKey("nombre")] = nomb
            }
            //SETEAR VIEWMODEL
            setUser(nomb,idCli.body()!!.idCliente)
        }
    }
    fun setUser(nomb:String,idCli:Int){
        viewModelScope.launch{
            estadoUser = estadoUser.copy(
                nombre = nomb,
                idCliente = idCli,
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
    fun takeTable(idMesa:Int,hash:String){
        viewModelScope.launch {
            val rta= usuarioServicio.takeTable(idMesa,estadoUser.idCliente,hash)
            estadoUser = estadoUser.copy(idMesa=idMesa, jwt =rta.body()!!.token )
            dataStore.edit { preferences -> preferences[intPreferencesKey("idMesa")]=idMesa }
        }
    }
    fun initializating(){
        viewModelScope.launch {
            val user = mapUser(dataStore.data.first().toPreferences())
            estadoUser = estadoUser.copy(
                idCliente = user.idCliente,
                nombre=user.nombre,
                idMesa = user.idMesa,
                idDevice=user.idDevice,
                initializatingApp = false,
                isLogged = if(user.idCliente==0)false else true)
            }
        }
    fun clearSavedData(){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(
                idCliente = 0,
                nombre = "",
                idMesa = 0,
                isLogged = false
            )
            dataStore.edit {  preferences -> preferences.clear() }
        }
    }
    fun exitTable(){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(idMesa = 0)
            dataStore.edit { preferences -> preferences[intPreferencesKey("idMesa")]=0 }
        }
    }
    fun mapUser(preferences: Preferences):UserSavedData {
        val idCli = preferences[intPreferencesKey("idCliente")]?:0
        val idMesa = preferences[intPreferencesKey("idMesa")]?:0
        val nomb=preferences[stringPreferencesKey("nombre")].orEmpty()
        val idDevice=preferences[stringPreferencesKey("idDevice")]?:"vacio"
        return UserSavedData(idDevice,nomb,idCli,idMesa)
    }
    fun getConsumo (){
        viewModelScope.launch {
            delay(1000)
            val consumidos = usuarioServicio.getConsumo(estadoUser.idCliente)
            if(consumidos.isSuccessful){
                if(consumidos.body() != null){
                    estadoUser = estadoUser.copy(
                        consumos = consumidos.body()!!.consumo,
                        loadingConsumo = false
                    )
                }
            }
        }
    }
    fun pagar(){
        viewModelScope.launch {
            usuarioServicio.pay(estadoUser.idCliente)
        }
    }
    fun rechazarDividirConsumo(){
        viewModelScope.launch {
            usuarioServicio.pagarDivididos(estadoUser.idMesa,estadoUser.idCliente,"no")
        }
    }
    fun aceptarDividirConsumo(){
        viewModelScope.launch {
            usuarioServicio.pagarDivididos(estadoUser.idMesa,estadoUser.idCliente,"si")
        }
    }
    fun iniciarDividirConsumo(){
        viewModelScope.launch {
            usuarioServicio.pagarDivididos(estadoUser.idMesa,estadoUser.idCliente,"start")
        }
    }
    fun socketear(){
        SocketManager.connect()
        SocketManager.sendMsj("hola soy order easy")
        //SocketManager.disconnect()
    }

}