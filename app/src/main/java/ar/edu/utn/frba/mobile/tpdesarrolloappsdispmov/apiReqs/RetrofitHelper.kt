package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restowebback-production.up.railway.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getInstance():EstadoMesaService{
        return retrofit.create(EstadoMesaService::class.java)
    }
}