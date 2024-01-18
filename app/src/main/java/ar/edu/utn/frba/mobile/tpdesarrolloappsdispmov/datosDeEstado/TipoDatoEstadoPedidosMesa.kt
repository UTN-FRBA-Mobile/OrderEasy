package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

data class TipoDatoEstadoPedidosMesa(
    val pedidosMesa:List<ComensalData> = emptyList(),
    val consumosMesa:List<ComensalData> = emptyList(),
    val invitados:MutableList<UserInvitedData> = mutableListOf(),
    val pidiendoDatos:Boolean = false,
    val resultPedidoApi:Int = 0,
    val pidiendoConsumos:Boolean = false,
    val resultPedidoConsumos:Int = 0
)
