package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

import com.google.gson.annotations.SerializedName

data class ItemPlato(
    val idPlato:Int,
    val nombre:String,
    val precio:Float
)
data class ItemConsumidoData(
    val idPedido:Int,
    val cantidad:Int,
    val estado:String,
    @SerializedName("Plato")val plato:ItemPlato
)
data class PedidoConsumoData(
    val consumo:MutableList<ItemConsumidoData>
)
