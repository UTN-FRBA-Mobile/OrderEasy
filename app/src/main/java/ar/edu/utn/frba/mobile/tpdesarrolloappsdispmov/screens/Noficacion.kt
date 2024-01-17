package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun Notificacion(navController: NavController,userViewModel: UserViewModel){//,total:String,individual:String,cantidad:String) {
    Scaffold (
        topBar = {TopBar(userViewModel) },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize().padding(innerPadding)){
                ElevatedCard (
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.padding(20.dp)
                )
                {
                    Text(
                        text = "Se dividirá el total gastado en la mesa de $${userViewModel.estadoUser.gastoTotDivide} entre los ${userViewModel.estadoUser.cantDivide} comensales," +
                                " pagando cada uno $${userViewModel.estadoUser.gastoIndDivide}. Si alguno de los integrantes de la mesa no acepta la propuesta, entonces se cancelará ésta" +
                                " forma de pago",
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
                            userViewModel.unsetInviteDivide()
                            userViewModel.aceptarDividirConsumo()
                            navController.navigate(route="mainmenu")
                        },
                        icon = { Icon(Icons.Filled.Check,  contentDescription ="volver") },
                        text = { Text(text = stringResource(id = R.string.accept), style= MaterialTheme.typography.labelSmall) },
                    )
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .padding(10.dp),
                        onClick = {
                            userViewModel.unsetInviteDivide()
                            userViewModel.rechazarDividirConsumo()
                            navController.navigate(route="mainmenu")
                        },
                        icon = { Icon(Icons.Filled.Close,  contentDescription ="volver") },
                        text = { Text(text = stringResource(id = R.string.decline), style= MaterialTheme.typography.labelSmall) },
                    )
                }
            }
        }
    )
}