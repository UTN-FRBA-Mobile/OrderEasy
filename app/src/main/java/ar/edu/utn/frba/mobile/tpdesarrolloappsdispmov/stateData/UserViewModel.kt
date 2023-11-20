package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.InitNotifications
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.socket.SocketManager
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
            //Log.d("rta---->",idCli.body()!!.toString())
        }
        //SETEAR DATASTORE
        //saveUser(nomb,estadoUser.idCliente,estadoUser.idDevice)
    }
    fun setUser(nomb:String,idCli:Int){
        Log.i("setUser-nombre-->",nomb)
        viewModelScope.launch{
            estadoUser = estadoUser.copy(
                nombre = nomb,
                idCliente = idCli,
                isLogged = true,
                requestingData = false
            )
        }
    }
    /*fun saveUser(nomb:String,idCli:Int,idDevice:String) {
        Log.i("saveUser-nombre->",nomb)
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey("idCliente")] = idCli
                preferences[stringPreferencesKey("idDevice")] =idDevice
                preferences[stringPreferencesKey("nombre")] = nomb
            }
        }
    }*/
    fun setIdDevice(id:String) {
        viewModelScope.launch {
            estadoUser = estadoUser.copy(idDevice = id)
        }
    }
    fun takeTable(idMesa:Int,hash:String){
        viewModelScope.launch {
            Log.i("takeTable-hash->",hash)
            Log.i("takeTable-mesa->",idMesa.toString())
            Log.i("takeTable-cliente->",estadoUser.idCliente.toString())
            val rta= usuarioServicio.takeTable(idMesa,estadoUser.idCliente,hash)
            Log.i("takeTable-rta>",rta.body()!!.token)
            estadoUser = estadoUser.copy(idMesa=idMesa, jwt =rta.body()!!.token )
            dataStore.edit { preferences -> preferences[intPreferencesKey("idMesa")]=idMesa }
        }
    }
    fun initializating(){
        viewModelScope.launch {
            val user = mapUser(dataStore.data.first().toPreferences())
            Log.i("initializating->",user.toString())
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
        Log.i("getConsumo----->","OUTSIDE")
        viewModelScope.launch {
            Log.i("getConsumo----->","inside")
            delay(1400)
            val consumidos = usuarioServicio.getConsumo(estadoUser.idCliente)
            Log.i("getConsumo----->","llame a usuarioServicio otra vez?")
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

    /*suspend fun setDataStore(nombre:String,estado:String,idDevice:String,idMesa:Int,idCliente:Int){
        dataStore.edit { preferences ->
            //preferences[stringPreferencesKey("nombre")]=nombre
            preferences[stringPreferencesKey("estado")]=estado
            //preferences[stringPreferencesKey("idDevice")]=idDevice
            //preferences[intPreferencesKey("idMesa")]=idMesa
            //preferences[intPreferencesKey("idCliente")]=idCliente
        }
    }*/

}