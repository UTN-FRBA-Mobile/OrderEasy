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
    var estadoUsuario by mutableStateOf(TipoDatoUsuario())
        private set
    fun setearInvitacionDividir(tot:String, cant:String, indiv:String){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(
                gastoIndDivide = indiv,
                gastoADividir = tot,
                cantDividida = cant,
                pidiendoDatos = false
            )
        }
    }
    fun limpiarInvitacionDividir(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(
                gastoIndDivide = "",
                gastoADividir = "",
                cantDividida = "",
            )
        }
    }
    fun ingresar(nomb:String){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(registrandoUsuarioApi = true)
            val idCli = usuarioServicio.ingresar(nomb,estadoUsuario.idDispositivo)
            if (idCli.isSuccessful) {
                dataStore.edit { preferences ->
                    preferences[intPreferencesKey("idCliente")] = idCli.body()!!.idCliente
                    preferences[stringPreferencesKey("idDevice")] = estadoUsuario.idDispositivo
                    preferences[stringPreferencesKey("nombre")] = nomb
                }
                //SETEAR VIEWMODEL
                setearUsuario(nomb, idCli.body()!!.idCliente)
            }else{
                estadoUsuario = estadoUsuario.copy(errorRegistrandoUsuarioApi = true)
            }
            estadoUsuario = estadoUsuario.copy(registrandoUsuarioApi = false)
        }
    }
    fun cancelarErrorRegistroUsuarioApi(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy( errorRegistrandoUsuarioApi = false)
        }
    }
    fun cancelarErrorPedApi(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy( errorPedidoApi = false)
        }
    }
    fun setearUsuario(nomb:String, idCli:Int){
        viewModelScope.launch{
            estadoUsuario = estadoUsuario.copy(
                nombre = nomb,
                idCliente = idCli,
                estaIngresado = true,
            )
        }
    }
    fun setearDispositivoId(id:String) {
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(idDispositivo = id)
        }
    }
    fun tomarMesa(idMesa:Int, hash:String){
        viewModelScope.launch {
            Log.i("TAKE-TABLE-->","OK")
            Log.i("TAKE-TABLE-idCli->",estadoUsuario.idCliente.toString())
            Log.i("TAKE-TABLE-idMesa->",idMesa.toString())
            Log.i("TAKE-TABLE-hash->",hash)
            val rta= usuarioServicio.registrarseEnMesa(idMesa,estadoUsuario.idCliente,hash)
            if (rta.isSuccessful){
                estadoUsuario = estadoUsuario.copy(idMesa=idMesa, jwt =rta.body()!!.token )
                dataStore.edit { preferences -> preferences[intPreferencesKey("idMesa")]=idMesa }
            }else{
                estadoUsuario = estadoUsuario.copy( errorPedidoApi = true)
            }
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = false)
        }
    }
    fun inicializar(){
        Log.i("VistaModeloUsuario--->","INITIALIZATING")
        viewModelScope.launch {
            //Log.i("VistaModeloUsuario--launch1-->","launching")
            val user = mapUser(dataStore.data.first().toPreferences())
            //Log.i("VistaModeloUsuario--launch2-->",user.toString())
            estadoUsuario = estadoUsuario.copy(
                idCliente = user.idCliente,
                nombre=user.nombre,
                idMesa = user.idMesa,
                initializatingApp = false,
                estaIngresado = user.idCliente != 0//if(user.idCliente==0)false else true)
            )
            }
        Log.i("VistaModeloUsuario-->","POST-INITIALIZATING->${estadoUsuario.toString()}")
        }
    fun clearSavedData(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(
                idCliente = 0,
                nombre = "",
                idMesa = 0,
                estaIngresado = false
            )
            dataStore.edit {  preferences -> preferences.clear() }
        }
    }
    fun retirarseDeMesa(){
        viewModelScope.launch {
            usuarioServicio.retirarse(estadoUsuario.idCliente)
            estadoUsuario = estadoUsuario.copy(
                idMesa = 0,
                gastoIndDivide = "",
                gastoADividir = "",
                cantDividida = ""
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
    fun obtenerConsumo (){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy( cargandoConsumo = true)
            //delay(1000)
            val consumidos = usuarioServicio.obtenerConsumo(estadoUsuario.idCliente)
            if(consumidos.isSuccessful){
                if(consumidos.body() != null){
                    estadoUsuario = estadoUsuario.copy(
                        consumos = consumidos.body()!!.consumo,
                        resultadoCargandoConsumo = 1,
                        cargandoConsumo = false
                    )
                }
            }else{
                estadoUsuario = estadoUsuario.copy(
                    resultadoCargandoConsumo = 2,
                    cargandoConsumo = false)
            }
        }
    }
    fun pagar(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = true, resultPedidoApi = 0)
            val rta = usuarioServicio.pagarIndividual(estadoUsuario.idCliente)
            estadoUsuario = if(rta.isSuccessful){
                estadoUsuario.copy(resultPedidoApi = 1)
            }else{
                estadoUsuario.copy(resultPedidoApi = 2)
            }
            estadoUsuario = estadoUsuario.copy( pidiendoDatos = false )
        }
    }
    fun rechazarDividirConsumo(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = true)
            val rta = usuarioServicio.pagarDivididos(estadoUsuario.idMesa,estadoUsuario.idCliente,"no")
            if (rta.isSuccessful){
                limpiarInvitacionDividir()
            }else{
                estadoUsuario = estadoUsuario.copy(errorPedidoApi = true)
            }
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = false)
        }
    }
    fun aceptarDividirConsumo(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = true)
            val rta = usuarioServicio.pagarDivididos(estadoUsuario.idMesa,estadoUsuario.idCliente,"si")
            if (rta.isSuccessful){
                limpiarInvitacionDividir()
            }else{
                estadoUsuario = estadoUsuario.copy(errorPedidoApi = true)
            }
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = false)
        }
    }
    fun iniciarDividirConsumo(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = true)
            val rta =  usuarioServicio.pagarDivididos(estadoUsuario.idMesa,estadoUsuario.idCliente,"start")
            estadoUsuario = if(rta.isSuccessful){
                estadoUsuario.copy(resultPedidoApi = 1)
            }else{
                estadoUsuario.copy(resultPedidoApi = 2)
            }
            estadoUsuario = estadoUsuario.copy( pidiendoDatos = false )
        }
    }
    fun selectRival(idCli:Int,monto:Float){
        viewModelScope.launch {
            val juego:ChallengeData = ChallengeData(idCli,estadoUsuario.game.nombOponente,estadoUsuario.game.ptsPropios, estadoUsuario.game.ptsRival, monto,estadoUsuario.game.estado,estadoUsuario.game.idPartida)
            estadoUsuario = estadoUsuario.copy(
                game = juego
            )
        }
    }
    fun setRival(idCli: Int,nombre:String,estado:String){
        viewModelScope.launch {
            val juego:ChallengeData = ChallengeData(idCli,nombre,estadoUsuario.game.ptsPropios, estadoUsuario.game.ptsRival, estadoUsuario.game.gastoRival,estado,estadoUsuario.game.idPartida)
            estadoUsuario = estadoUsuario.copy(
                game = juego
            )
        }
    }
    fun setGame(idpartida:Int,estado:String){
        viewModelScope.launch {
            val juego:ChallengeData = ChallengeData(estadoUsuario.game.idOponente,estadoUsuario.game.nombOponente,estadoUsuario.game.ptsPropios, estadoUsuario.game.ptsRival, estadoUsuario.game.gastoRival,estado,idpartida)
            estadoUsuario = estadoUsuario.copy(
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
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = true, resultPedidoApi = 0)
            val rta = usuarioServicio.llamarmozo(idMesa)
            estadoUsuario = if(rta.isSuccessful){
                estadoUsuario.copy(resultPedidoApi = 1) //OK
            }else{
                estadoUsuario.copy(resultPedidoApi = 2) //ERROR
            }
            estadoUsuario = estadoUsuario.copy( pidiendoDatos = false )
        }
    }
    fun desactivarErrorPedidoApi(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy( resultPedidoApi = 0)
        }
    }
}