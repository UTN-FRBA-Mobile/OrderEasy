package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class PlatoPedido(
    val idPlato:Int,
    var cantidad:Int
)
data class MenuData(
    val loadingMenu:Boolean=false,
    val platos: List<Plato> = emptyList(),
    var pedidos: MutableList<PlatoPedido> = mutableListOf()//= emptyList<PlatoPedido>()
)
