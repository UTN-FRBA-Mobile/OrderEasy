package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

data class PlatoPedido(
    val idPlato:Int,
    var cantidad:Int,
    var estado:String
)
data class PlatoOrdenado(
    val idPlato:Int,
    val cantidad:Int,
)
data class MenuData(
    val loadingMenu:Boolean=false,
    val platos: List<Plato> = emptyList(),
    val pidiendoDatos:Boolean = false,
    val errorPedidoApi:Boolean = false,
    val menucargado:Boolean = false,
    var pedidos: MutableList<PlatoPedido> = mutableListOf()//= emptyList<PlatoPedido>()
)
