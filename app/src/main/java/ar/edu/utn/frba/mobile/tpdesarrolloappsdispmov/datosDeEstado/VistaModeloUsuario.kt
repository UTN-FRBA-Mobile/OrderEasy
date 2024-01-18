package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

//import android.util.Log
import android.util.Log
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
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pedidosApi.ServicioDePedidos
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.socket.SocketManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
class VistaModeloUsuario (private val usuarioServicio: ServicioDePedidos, private val dataStore: DataStore<Preferences>): ViewModel() {
    var estadoUser by mutableStateOf(TipoDatoUsuario())
        private set
    /*init {
        estadoUser = estadoUser.copy(requestingData=true)
        initializating()
    }*/
    fun setInviteDivide(tot:String,cant:String,indiv:String){
        Log.i("VistaModeloUsuario--invite-->","tot->$tot cant->$cant indiv->$indiv")
        viewModelScope.launch {
            estadoUser = estadoUser.copy(
                gastoIndDivide = indiv,
                gastoTotDivide = tot,
                cantDivide = cant,
                requestingData = false
            )
            Log.i("VistaModeloUsuario--stat-->","estado.gastoInd->${estadoUser.gastoIndDivide} .gastoTot->${estadoUser.gastoIndDivide}")
        }
    }
    fun unsetInviteDivide(){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(
                gastoIndDivide = "",
                gastoTotDivide = "",
                cantDivide = "",
            )
        }
    }
    fun log(nomb:String){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(registrandoUsuarioApi = true)
            val idCli = usuarioServicio.getLogged(nomb,estadoUser.idDevice)
            if (idCli.isSuccessful) {
                dataStore.edit { preferences ->
                    preferences[intPreferencesKey("idCliente")] = idCli.body()!!.idCliente
                    preferences[stringPreferencesKey("idDevice")] = estadoUser.idDevice
                    preferences[stringPreferencesKey("nombre")] = nomb
                }
                //SETEAR VIEWMODEL
                setUser(nomb, idCli.body()!!.idCliente)
            }else{
                estadoUser = estadoUser.copy(errorRegistrandoUsuarioApi = true)
            }
            estadoUser = estadoUser.copy(registrandoUsuarioApi = false)
        }
    }
    fun cancelarErrorRegistroUsuarioApi(){
        viewModelScope.launch {
            estadoUser = estadoUser.copy( errorRegistrandoUsuarioApi = false)
        }
    }
    fun setUser(nomb:String,idCli:Int){
        viewModelScope.launch{
            estadoUser = estadoUser.copy(
                nombre = nomb,
                idCliente = idCli,
                isLogged = true,
                //requestingLog = false
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
            Log.i("TAKE-TABLE-->","OK")
            Log.i("TAKE-TABLE-idCli->",estadoUser.idCliente.toString())
            Log.i("TAKE-TABLE-idMesa->",idMesa.toString())
            Log.i("TAKE-TABLE-hash->",hash)
            val rta= usuarioServicio.takeTable(idMesa,estadoUser.idCliente,hash)
            //Log.i("TAKE-TAB-SERV-->",rta.toString())
            estadoUser = estadoUser.copy(idMesa=idMesa, jwt =rta.body()!!.token )
            dataStore.edit { preferences -> preferences[intPreferencesKey("idMesa")]=idMesa }
        }
    }
    fun initializating(){
        Log.i("VistaModeloUsuario--->","INITIALIZATING")
        viewModelScope.launch {
            //Log.i("VistaModeloUsuario--launch1-->","launching")
            val user = mapUser(dataStore.data.first().toPreferences())
            //Log.i("VistaModeloUsuario--launch2-->",user.toString())
            estadoUser = estadoUser.copy(
                idCliente = user.idCliente,
                nombre=user.nombre,
                idMesa = user.idMesa,
                //idDevice=user.idDevice,
                initializatingApp = false,
                isLogged = if(user.idCliente==0)false else true)
            }
        Log.i("VistaModeloUsuario-->","POST-INITIALIZATING->${estadoUser.toString()}")
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
            usuarioServicio.exit(estadoUser.idCliente)
            estadoUser = estadoUser.copy(
                idMesa = 0,
                gastoIndDivide = "",
                gastoTotDivide = "",
                cantDivide = ""
            )
            dataStore.edit { preferences -> preferences[intPreferencesKey("idMesa")]=0 }
        }
    }
    fun mapUser(preferences: Preferences):TipoDatoUsuarioAlmacenado {
        val idCli = preferences[intPreferencesKey("idCliente")]?:0
        val idMesa = preferences[intPreferencesKey("idMesa")]?:0
        val nomb=preferences[stringPreferencesKey("nombre")].orEmpty()
        val idDevice=preferences[stringPreferencesKey("idDevice")]?:"vacio"
        return TipoDatoUsuarioAlmacenado(idDevice,nomb,idCli,idMesa)
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
    fun selectRival(idCli:Int,monto:Float){
        viewModelScope.launch {
            val juego:ChallengeData = ChallengeData(idCli,estadoUser.game.nombOponente,estadoUser.game.ptsPropios, estadoUser.game.ptsRival, monto,estadoUser.game.estado,estadoUser.game.idPartida)
            estadoUser = estadoUser.copy(
                game = juego
            )
        }
    }
    fun setRival(idCli: Int,nombre:String,estado:String){
        viewModelScope.launch {
            val juego:ChallengeData = ChallengeData(idCli,nombre,estadoUser.game.ptsPropios, estadoUser.game.ptsRival, estadoUser.game.gastoRival,estado,estadoUser.game.idPartida)
            estadoUser = estadoUser.copy(
                game = juego
            )
        }
    }
    fun setGame(idpartida:Int,estado:String){
        viewModelScope.launch {
            val juego:ChallengeData = ChallengeData(estadoUser.game.idOponente,estadoUser.game.nombOponente,estadoUser.game.ptsPropios, estadoUser.game.ptsRival, estadoUser.game.gastoRival,estado,idpartida)
            estadoUser = estadoUser.copy(
                game = juego
            )
        }
    }
    fun socketear(){
        SocketManager.connect()
        SocketManager.sendMsj("hola soy order easy")
        //SocketManager.disconnect()
    }
    fun llamarmozo(idMesa: Int){
        viewModelScope.launch {
            estadoUser = estadoUser.copy(pidiendoDatos = true, resultPedidoApi = 0)
            val rta = usuarioServicio.llamarmozo(idMesa)
            estadoUser = if(rta.isSuccessful){
                estadoUser.copy(resultPedidoApi = 1) //OK
            }else{
                estadoUser.copy(resultPedidoApi = 2) //ERROR
            }
            estadoUser = estadoUser.copy( pidiendoDatos = false )
        }
    }
    fun desactivarErrorPedidoApi(){
        viewModelScope.launch {
            estadoUser = estadoUser.copy( resultPedidoApi = 0)
        }
    }
}