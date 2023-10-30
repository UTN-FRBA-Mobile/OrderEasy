package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData
data class elementPlato(
    val nombre:String,
    val precio:Float
)
data class elementComensal(
    val nombre:String
)
data class elementTabState(
    val idPedido:Int,
    val estado:String,
    val cantidad:Int,
    val cancelable:Boolean,
    val horaPedido: String,
    val idCliente:Int,
    val idPlato:Int,
    val Plato:elementPlato,
    val Comensal:elementComensal
)
data class PedidosMesa(val pedidos:List<elementTabState>)
