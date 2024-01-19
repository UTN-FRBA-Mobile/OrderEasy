package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
class VistaModeloUsuario (private val usuarioServicio: ServicioDePedidos, private val almacenDatos: DataStore<Preferences>): ViewModel() {
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
    private fun limpiarInvitacionDividir(){
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
                almacenDatos.edit { preferences ->
                    preferences[intPreferencesKey("idCliente")] = idCli.body()!!.idCliente
                    preferences[stringPreferencesKey("idDevice")] = estadoUsuario.idDispositivo
                    preferences[stringPreferencesKey("nombre")] = nomb
                }
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
    private fun setearUsuario(nomb:String, idCli:Int){
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
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = true)
            val rta= usuarioServicio.registrarseEnMesa(idMesa,estadoUsuario.idCliente,hash)
            if (rta.isSuccessful){
                estadoUsuario = estadoUsuario.copy(idMesa=idMesa, jwt =rta.body()!!.token )
                almacenDatos.edit { preferences -> preferences[intPreferencesKey("idMesa")]=idMesa }
            }else{
                estadoUsuario = estadoUsuario.copy( errorPedidoApi = true)
            }
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = false)
        }
    }
    fun inicializar(){
        viewModelScope.launch {
            val user = mapUser(almacenDatos.data.first().toPreferences())
            estadoUsuario = estadoUsuario.copy(
                idCliente = user.idCliente,
                nombre=user.nombre,
                idMesa = user.idMesa,
                inicializandoAplicacion = false,
                estaIngresado = user.idCliente != 0//if(user.idCliente==0)false else true)
            )
            }
        }
    fun retirarseDeMesa(){
        viewModelScope.launch {
            estadoUsuario = estadoUsuario.copy(pidiendoDatos = true)
            // chequeos pagos pendientes
            val rta = usuarioServicio.retirarse(estadoUsuario.idCliente)
            if (rta.isSuccessful){
                if(rta.body()?.e == 0 && rta.body()?.p ==0 ){
                    estadoUsuario = estadoUsuario.copy(
                        idMesa = 0,
                        gastoIndDivide = "",
                        gastoADividir = "",
                        cantDividida = "",
                        msjDialog = "Gracias por tu visita"
                    )
                    almacenDatos.edit { preferences -> preferences[intPreferencesKey("idMesa")]=0 }
                }
                else{
                    //hay platos pendientes
                    estadoUsuario = estadoUsuario.copy(
                        msjDialog = "Tienes platos pendientes de entrega y/o pago."
                    )
                }
            }else{
                estadoUsuario = estadoUsuario.copy(errorPedidoApi = true)
            }
            estadoUsuario = estadoUsuario.copy( pidiendoDatos = false )
        }
    }
    private fun mapUser(preferences: Preferences):TipoDatoUsuarioAlmacenado {
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