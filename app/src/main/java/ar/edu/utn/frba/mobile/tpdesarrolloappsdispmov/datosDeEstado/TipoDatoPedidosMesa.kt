package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

import com.google.gson.annotations.SerializedName

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
    @SerializedName("Plato")val plato:PlatoData
)
data class ComensalData(
    val idCliente:Int,
    val nombre:String,
    @SerializedName ("Pedidos")val pedidos:ArrayList<PedidoData>
)
data class UserInvitedData(
    val idCliente:Int,
    val nombre:String,
    val total:Float,
    var seleccionado:Boolean,
    val pedPendientes:Boolean
)
data class PedidosMesaData(val comensales:List<ComensalData>)
