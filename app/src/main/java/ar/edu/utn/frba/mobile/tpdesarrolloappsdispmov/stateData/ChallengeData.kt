package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

data class ChallengeData(
    val idOponente:Int,
    val nombOponente:String,
    val ptsPropios:Int,
    val ptsRival:Int,
    val gastoRival:Float,
    val estado: String = "nulo",
    val idPartida:Int
)
