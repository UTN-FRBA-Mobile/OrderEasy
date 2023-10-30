package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs

import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.controllers.PlatosResponse
import retrofit2.Response
import retrofit2.http.GET

interface PlatosService {
    @GET("platos/")
    suspend fun getPlatos(): Response<PlatosResponse>
}