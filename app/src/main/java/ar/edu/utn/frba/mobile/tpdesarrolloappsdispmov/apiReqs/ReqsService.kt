package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs

import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoLogin
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidoMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidosMesa
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ReqsService {
    companion object{
        val instance = Retrofit.Builder()
            .baseUrl("https://restowebback-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReqsService::class.java)
    }
    @GET("mesas/estado/{id}")
    suspend fun getStateTable(@Path("id") id:Int): Response<PedidosMesa>
    @GET ("/clientes/{nomb}")
    suspend fun getLogged(@Path("nomb") id:String):Response<PedidoLogin>
    @GET("/platos")
    suspend fun getMenu():Response<PedidoMenu>
}