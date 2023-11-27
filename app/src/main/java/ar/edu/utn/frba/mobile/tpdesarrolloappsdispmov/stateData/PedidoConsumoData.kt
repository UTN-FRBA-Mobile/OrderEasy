package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class itemPlato(
    val idPlato:Int,
    val nombre:String,
    val precio:Float
)
data class ItemConsumidoData(
    val idPedido:Int,
    val cantidad:Int,
    val Plato:itemPlato
)
data class PedidoConsumoData(
    val consumo:MutableList<ItemConsumidoData>
)
