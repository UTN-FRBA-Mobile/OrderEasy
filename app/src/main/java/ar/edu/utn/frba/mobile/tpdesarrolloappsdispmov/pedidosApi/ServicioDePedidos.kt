package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pedidosApi

import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.Invitados
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.Ordenes
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.PedidoConsumoData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.TipoDatoPedidoIngreso
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.PedidoMenuData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.PedidoOrdenarData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.TipoDatoPedidoEscanQr
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.PedidosMesaData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.TipoDatoRespuestaApiEstandard
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServicioDePedidos {
    companion object{
        val instance = Retrofit.Builder()
            //.baseUrl("http://10.0.2.2:5000/")
            //baseUrl("http://192.168.0.41:5000/")
            //.baseUrl("https://restowebback-production.up.railway.app/")
            //.baseUrl("https://ordereasy-backend.onrender.com")
            .baseUrl("https://order-easy-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ServicioDePedidos::class.java)
    }
    @GET("/clientes/updatetoken/{idCliente}/{token}")
    suspend fun actualizarToken(@Path("token") token:String): Response<TipoDatoRespuestaApiEstandard>
    @GET("/clientes/{idCliente}/{token")
    suspend fun updatetoken(@Path("idCliente") idCliente: Int, @Path("token") token:String): Response<TipoDatoRespuestaApiEstandard>
    @GET("mesas/compas/{idMesa}")
    suspend fun getConsumoTable(@Path("idMesa") idMesa:Int): Response<PedidosMesaData>
    @GET("mesas/estado/{idMesa}")
    suspend fun getStateTable(@Path("idMesa") idMesa:Int): Response<PedidosMesaData>
    @GET ("/clientes/{nomb}/{idDevice}")
    suspend fun getLogged(@Path("nomb") id:String,@Path("idDevice") idDevice:String):Response<TipoDatoPedidoIngreso>
    @GET("/platos")
    suspend fun getMenu():Response<PedidoMenuData>
    @POST("/mesas/ordenar/{idMesa}/{idCliente}")
    suspend fun makeOrder(@Path("idMesa") idMesa:Int, @Path("idCliente") idCliente:Int,@Body body:Ordenes): Response<PedidoOrdenarData>
    @GET("mesas/pagar/desafio/{accion}/{idCliente}/{idRival}")
    suspend fun desafiar(@Path("accion") accion:String, @Path("idCliente") idCliente: Int, @Path("idRival") idRival:Int): Response<TipoDatoRespuestaApiEstandard>
    @POST("/mesas/pagar/invitados/{idCliente}")
    suspend fun pagarInvitados(@Path("idCliente") idCliente: Int,@Body body: Invitados): Response<TipoDatoRespuestaApiEstandard>
    @GET("/mesas/pagar/dividido/{idMesa}/{idCliente}/{accion}")
    suspend fun pagarDivididos(@Path("idMesa") idMesa: Int,@Path("idCliente") idCliente: Int, @Path("accion") accion:String): Response<TipoDatoRespuestaApiEstandard>
    @GET("/mesas/registrarse/{idMesa}/{idCliente}/{hash}")
    suspend fun takeTable(@Path("idMesa") idMesa:Int, @Path("idCliente") idCliente:Int, @Path("hash") hash:String): Response<TipoDatoPedidoEscanQr>
    @GET("/mesas/consumos/{idCliente}")
    suspend fun getConsumo(@Path("idCliente") idCliente:Int): Response<PedidoConsumoData>
    @GET("/mesas/pagar/individual/{idCliente}")
    suspend fun pay(@Path("idCliente") idCliente: Int): Response<TipoDatoRespuestaApiEstandard>
    @GET("/mesas/exit/{idCliente}")
    suspend fun exit(@Path("idCliente") idCliente: Int):Response<TipoDatoRespuestaApiEstandard>
    @GET("/mesas/llamarmozo/{idMesa}")
    suspend fun llamarmozo(@Path("idMesa") idMesa: Int):Response<TipoDatoRespuestaApiEstandard>
}