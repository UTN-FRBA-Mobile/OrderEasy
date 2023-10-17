package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class UserData(
    val nombre:String="",
    val idCliente:Int=0,
    val inTable:Boolean=false,
    val isLogged:Boolean=true,
    val requestingData:Boolean=false
)
