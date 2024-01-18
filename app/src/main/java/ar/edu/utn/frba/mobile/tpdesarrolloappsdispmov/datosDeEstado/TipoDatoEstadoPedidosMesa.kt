package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

data class TipoDatoEstadoPedidosMesa(
    val pedidosMesa:List<ComensalData> = emptyList(),
    val consumosMesa:List<ComensalData> = emptyList(),
    val invitados:MutableList<UserInvitedData> = mutableListOf(),
    val requestingData:Boolean = true
)
