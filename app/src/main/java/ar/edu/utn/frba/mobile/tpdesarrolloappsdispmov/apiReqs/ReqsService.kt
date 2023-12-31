package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs

import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.Invitados
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.Ordenes
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoConsumoData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoLoginData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoMenuData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoOrdenarData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoScanQrData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidosMesaData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.ResponseApiStandardData
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReqsService {
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
            .create(ReqsService::class.java)
    }
    @GET("/clientes/{idCliente}/{token")
    suspend fun updatetoken(@Path("idCliente") idCliente: Int, @Path("token") token:String): Response<ResponseApiStandardData>
    @GET("mesas/compas/{idMesa}")
    suspend fun getConsumoTable(@Path("idMesa") idMesa:Int): Response<PedidosMesaData>
    @GET("mesas/estado/{idMesa}")
    suspend fun getStateTable(@Path("idMesa") idMesa:Int): Response<PedidosMesaData>
    @GET ("/clientes/{nomb}/{idDevice}")
    suspend fun getLogged(@Path("nomb") id:String,@Path("idDevice") idDevice:String):Response<PedidoLoginData>
    @GET("/platos")
    suspend fun getMenu():Response<PedidoMenuData>
    @POST("/mesas/ordenar/{idMesa}/{idCliente}")
    suspend fun makeOrder(@Path("idMesa") idMesa:Int, @Path("idCliente") idCliente:Int,@Body body:Ordenes): Response<PedidoOrdenarData>
    @GET("mesas/pagar/desafio/{accion}/{idCliente}/{idRival}")
    suspend fun desafiar(@Path("accion") accion:String, @Path("idCliente") idCliente: Int, @Path("idRival") idRival:Int): Response<ResponseApiStandardData>
    @POST("/mesas/pagar/invitados/{idCliente}")
    suspend fun pagarInvitados(@Path("idCliente") idCliente: Int,@Body body: Invitados): Response<ResponseApiStandardData>
    @GET("/mesas/pagar/dividido/{idMesa}/{idCliente}/{accion}")
    suspend fun pagarDivididos(@Path("idMesa") idMesa: Int,@Path("idCliente") idCliente: Int, @Path("accion") accion:String): Response<ResponseApiStandardData>
    @GET("/mesas/registrarse/{idMesa}/{idCliente}/{hash}")
    suspend fun takeTable(@Path("idMesa") idMesa:Int, @Path("idCliente") idCliente:Int, @Path("hash") hash:String): Response<PedidoScanQrData>
    @GET("/mesas/consumos/{idCliente}")
    suspend fun getConsumo(@Path("idCliente") idCliente:Int): Response<PedidoConsumoData>
    @GET("/mesas/pagar/individual/{idCliente}")
    suspend fun pay(@Path("idCliente") idCliente: Int): Response<ResponseApiStandardData>
    @GET("/mesas/exit/{idCliente}")
    suspend fun exit(@Path("idCliente") idCliente: Int):Response<ResponseApiStandardData>
}