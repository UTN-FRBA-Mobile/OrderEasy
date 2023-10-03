package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs

import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.PedidosMesa
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EstadoMesaService {
    @GET("mesas/{id}")
    suspend fun getStateTable(@Path("id") id:String): Response<PedidosMesa>
}