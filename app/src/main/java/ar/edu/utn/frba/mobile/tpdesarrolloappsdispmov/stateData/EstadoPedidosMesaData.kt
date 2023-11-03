package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class EstadoPedidosMesaData(
    val pedidosMesa:List<ComensalData> = emptyList(),
    val consumosMesa:List<ComensalData> = emptyList(),
    val invitados:MutableList<UserInvitedData> = mutableListOf(),
    val requestingData:Boolean = false
)
