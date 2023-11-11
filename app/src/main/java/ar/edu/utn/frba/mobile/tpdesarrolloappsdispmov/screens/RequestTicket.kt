package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.VolverBtn
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RequestTicket(navCont: NavController,userViewModel: UserViewModel,tableViewModel:TableViewModel) {
    ////Log.i("RequestTicket-->",tableViewModel.estadoMesa.invitados.toString())
    //Text(text = tableViewModel.estadoMesa.invitados.toString())
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                item{
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        onClick = {
                            userViewModel.getConsumo()
                            navCont.navigate(route="IndividualTicket")
                        },
                        icon = { Icon(painter = painterResource(id = R.drawable.pago1_48dp),
                            contentDescription ="pagoindividual",
                            modifier = Modifier.size(100.dp)  )},
                        text = { Text(text = "Pagar lo consumido en forma individual") },
                    )
                }
                item{
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        onClick = {
                            tableViewModel.getConsumosState(userViewModel.estadoUser.idMesa,userViewModel.estadoUser.idCliente)
                            navCont.navigate(route="DivideTicket")
                        },
                        icon = { Icon(painter = painterResource(id = R.drawable.pago5_48dp),
                            contentDescription ="pagoDividido",
                            modifier = Modifier.size(160.dp) ) },
                        text = { Text(text = "Pagar la parte resultante de dividir el total consumido en la mesa entre todos") },
                    )
                }
                item{
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        onClick = {
                            tableViewModel.getConsumosState(userViewModel.estadoUser.idMesa,userViewModel.estadoUser.idCliente)
                            navCont.navigate(route = "InviteTicket")
                        },
                        icon = { Icon(painter = painterResource(id = R.drawable.pago2_48dp) ,
                            contentDescription="pagoInvitado",
                            modifier = Modifier
                                .size(120.dp)
                                .padding(6.dp)
                        )},
                        text = { Text(text = "Pagar lo consumido individualmente y pagar la cuenta de alguien mas") },
                    )
                }
                /*item{
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        onClick = { navCont.navigate(route = "ChallengeTicket") },
                        icon = { Icon(painter = painterResource(id = R.drawable.pagodesafio_24dp),
                            contentDescription ="pagoDesasfio",
                            modifier = Modifier.size(64.dp) ) },
                        text = { Text(text = "Desasfía a alguien de la mesa por el pago de la cuenta") },
                    )
                }*/
                item{VolverBtn(navCont)}
            }
        }
    )
}