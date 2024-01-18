package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

//import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.VolverBtn
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario

@Composable
fun PedirCuenta(navCont: NavController, userViewModel: VistaModeloUsuario, tableViewModel:VistaModeloMesa) {
    Scaffold (
        topBar = { BarraSuperior(userViewModel)},
        content = { innerPadding ->
            when(userViewModel.estadoUser.game.estado){
                "jugando" -> navCont.navigate("game")
                "desafiado" -> navCont.navigate("desafio")
                else ->{
                    if(userViewModel.estadoUser.gastoTotDivide==null||userViewModel.estadoUser.gastoTotDivide=="") {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Top
                        ) {
                            item {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 14.dp),
                                    onClick = {
                                        userViewModel.getConsumo()
                                        navCont.navigate(route = "IndividualTicket")
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.pago1_48dp),
                                            contentDescription = "desc",
                                            modifier = Modifier.size(120.dp)
                                                .padding(vertical = 10.dp, horizontal = 14.dp)
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.getticket_individual),
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    },
                                )
                            }
                            item {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    onClick = {
                                        tableViewModel.setRequestingDataOn()
                                        tableViewModel.getConsumosState(
                                            userViewModel.estadoUser.idMesa,
                                            userViewModel.estadoUser.idCliente
                                        )
                                        navCont.navigate(route = "DivideTicket")
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.pago6_48dp),
                                            contentDescription = "desc",
                                            modifier = Modifier.size(130.dp)
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.getticket_divide),
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    },
                                )
                            }
                            item {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    onClick = {
                                        tableViewModel.setRequestingDataOn()
                                        tableViewModel.getConsumosState(
                                            userViewModel.estadoUser.idMesa,
                                            userViewModel.estadoUser.idCliente
                                        )
                                        navCont.navigate(route = "InviteTicket")
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.pago2_48dp),
                                            contentDescription = "desc",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(vertical = 6.dp, horizontal = 10.dp)
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.getticket_invite),
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    },
                                )
                            }
                            /*item {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    onClick = {
                                        tableViewModel.setRequestingDataOn()
                                        tableViewModel.getConsumosState(
                                            userViewModel.estadoUser.idMesa,
                                            userViewModel.estadoUser.idCliente
                                        )
                                        navCont.navigate(route = "challengeTicket")
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.pago43_48dp),
                                            contentDescription = "pagoInvitado",
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(vertical = 6.dp, horizontal = 10.dp)
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = "Desafía a un compañero de mesa a un juego de piedra-papel-tijera y quien pierda se hará cargo de ambas cuentas ",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    },
                                )
                            }*/
                            item { VolverBtn(navCont) }
                        }
                    }else{
                        navCont.navigate("notificacion")
                    }
                }
            }
        }
    )
}