package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado

data class TipoDatoUsuario(
    val idDispositivo:String="",
    val nombre:String="",
    val idCliente:Int=0,
    val idMesa:Int=0,
    val estaIngresado:Boolean=false,
    val registrandoUsuarioApi:Boolean = false,
    val errorRegistrandoUsuarioApi:Boolean = false,
    val pidiendoDatos:Boolean=true,
    val initializatingApp:Boolean=true,
    val requiriendoIngreso:Boolean=true,
    val jwt:String="",
    val consumos: List <ItemConsumidoData> = emptyList(),
    val cargandoConsumo: Boolean = true,
    val resultadoCargandoConsumo:Int = 0,
    val gastoADividir:String = "",
    val gastoIndDivide:String = "",
    val cantDividida:String = "",
    val errorPedidoApi:Boolean=false,
    val resultPedidoApi:Int = 0,
    val msjDialog:String =""
)
