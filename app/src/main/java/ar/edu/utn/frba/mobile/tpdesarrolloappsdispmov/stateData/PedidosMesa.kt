package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData
data class elementPlato(
    val nombre:String,
    val precio:Float
)
data class elementTabState(
    val idPedido:String,
    val estado:String,
    val cantidad:Int,
    val cancelable:Boolean,
    val horaPedido: String,
    val idCliente:Int,
    val idPlato:Int,
    val Plato:elementPlato
)
data class PedidosMesa(val rta:List<elementTabState>)
