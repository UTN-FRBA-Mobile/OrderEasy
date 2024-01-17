package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class UserData(
    val idDevice:String="",
    val nombre:String="",
    val idCliente:Int=0,
    val idMesa:Int=0,
    val registrandoUsuarioApi:Boolean = false,
    val errorRegistrandoUsuarioApi:Boolean = false,
    val isLogged:Boolean=false,
    val requestingData:Boolean=true,
    val initializatingApp:Boolean=true,
    val requestingLog:Boolean=true,
    val jwt:String="",
    val consumos: List <ItemConsumidoData> = emptyList(),
    val loadingConsumo: Boolean = true,
    val gastoTotDivide:String = "",
    val gastoIndDivide:String = "",
    val cantDivide:String = "",
    val game:ChallengeData= ChallengeData(0,"",0,0,0.0f,"vacio",0)
)
