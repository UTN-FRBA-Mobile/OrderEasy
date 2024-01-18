package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario


@Composable
fun Game(navController: NavController, userViewModel: VistaModeloUsuario){
    Scaffold (
        topBar = { BarraSuperior(userViewModel) },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize().padding(innerPadding)){
                ElevatedCard (
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.padding(20.dp)
                )
                {
                    Text(
                        text = "Tu compañero de mesa ${userViewModel.estadoUser.game.nombOponente} te desafía a un juego de piedra-papel-tijera al mejor de 3 partidas. Quien pierda" +
                                "se hará cargo de pagar ambas cuentas. El juego comenzará apenas aceptes el desafío y en cada partida dispondrás de 5 segundos para elejir una" +
                                "opcion. En caso de quedar empatados al termino de las 3 partidas se jugarán partidas adicionales hasta que uno de los dos gane. ",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ){
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .padding(10.dp),
                        onClick = {
                            //userViewModel.aceptarDividirConsumo()
                            //userViewModel.unsetInviteDivide()
                            navController.navigate(route="mainmenu")
                        },
                        icon = { Icon(Icons.Filled.Check,  contentDescription ="volver") },
                        text = { Text(text = "SI ACEPTO", style= MaterialTheme.typography.labelSmall) },
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .padding(10.dp),
                        onClick = {
                            //usuarioViewModel.rechazarDividirConsumo()
                            //userViewModel.unsetInviteDivide()
                            navController.navigate(route="mainmenu")
                        },
                        icon = { Icon(Icons.Filled.Close,  contentDescription ="volver") },
                        text = { Text(text = "NO ACEPTO", style= MaterialTheme.typography.labelSmall) },
                    )
                }
            }
        }
    )
}