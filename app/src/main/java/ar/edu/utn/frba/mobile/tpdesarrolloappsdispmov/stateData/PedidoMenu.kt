package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class Plato(
val idPlato: Int,
val nombre: String,
val descripcion:String,
val imagen:String,
val precio:Float,
val puntaje:Int,
val categoria:String,
val ingredientes:String,
val infoNutri:String,
val disponible:Boolean
)
data class PedidoMenu(
    val platos: List<Plato> = emptyList(),
)