package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class UserData(
    val idDevice:String="",
    val nombre:String="",
    val idCliente:Int=0,
    val idMesa:Int=0,
    //val inTable:Boolean=false,
    val isLogged:Boolean=false,
    val requestingData:Boolean=false,
    val initializatingApp:Boolean=true,
    val jwt:String=""
)
