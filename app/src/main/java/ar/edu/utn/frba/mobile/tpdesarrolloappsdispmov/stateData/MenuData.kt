package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class PlatoPedido(
    val idPlato:Int,
    val cantidad:Int
)
data class MenuData(
    val loadingMenu:Boolean=false,
    val platos: List<Plato> = emptyList(),
    val pedidos: List<PlatoPedido> = emptyList()
)
