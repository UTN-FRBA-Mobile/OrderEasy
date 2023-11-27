package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData
data class PlatoData(
    val nombre:String,
    val precio:Float
)
data class PedidoData(
    val idPedido:Int,
    val estado:String,
    val cantidad:Int,
    val cancelable:Boolean,
    val horaPedido: String,
    val idCliente:Int,
    val idMesa:Int,
    val idPlato:Int,
    val Plato:PlatoData
)
data class ComensalData(
    val idCliente:Int,
    val nombre:String,
    val Pedidos:Array<PedidoData>
)
data class UserInvitedData(
    val idCliente:Int,
    val nombre:String,
    val total:Float,
    var selected:Boolean
)
data class PedidosMesaData(val comensales:List<ComensalData>)
