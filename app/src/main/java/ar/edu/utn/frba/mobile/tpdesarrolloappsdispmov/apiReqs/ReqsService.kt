package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs

import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.Ordenes
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoLogin
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoOrdenar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidosMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PlatoOrdenado
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
            .baseUrl("https://restowebback-production.up.railway.app/")
            //.baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            //.client(OkHttpClient.Builder().build())
            .build()
            .create(ReqsService::class.java)
    }
    @GET("mesas/estado/{id}")
    suspend fun getStateTable(@Path("id") id:Int): Response<PedidosMesa>
    @GET ("/clientes/{nomb}/{idDevice}")
    suspend fun getLogged(@Path("nomb") id:String,@Path("idDevice") idDevice:String):Response<PedidoLogin>
    @GET("/platos")
    suspend fun getMenu():Response<PedidoMenu>
    @POST("/mesas/ordenar/{idMesa}/{idCliente}")
    suspend fun makeOrder(@Path("idMesa") idMesa:Int, @Path("idCliente") idCliente:Int,@Body body:Ordenes): Response<PedidoOrdenar>
}