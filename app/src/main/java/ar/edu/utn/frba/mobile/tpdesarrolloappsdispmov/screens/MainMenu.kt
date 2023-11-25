package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun MainMenu(navCont: NavController,usuarioViewModel: UserViewModel,tabStateViewModel:TableViewModel) {
    Scaffold (
        topBar = {TopBar(usuarioViewModel)},
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                LazyColumn{
                    item {
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            enabled= (usuarioViewModel.estadoUser.idMesa==0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            onClick = { navCont.navigate(route="scanqr")},

                        ){
                            Row (
                                modifier = Modifier.padding(horizontal=10.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_qr_code_24), contentDescription ="carta",modifier=Modifier.size(40.dp) )
                                Text(
                                    text =  "Escanear QR",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    item {
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            onClick = { navCont.navigate(route="readmenu")},

                            ){
                            Row (
                                modifier = Modifier.padding(horizontal=10.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_menu_book_24), contentDescription ="carta",modifier=Modifier.size(40.dp) )
                                Text(
                                    text =  "Ver el menu de platos",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    item {
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            enabled= (usuarioViewModel.estadoUser.idMesa!=0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            onClick = { navCont.navigate(route="makeorder")},

                            ){
                            Row (
                                modifier = Modifier.padding(horizontal=10.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_room_service_24), contentDescription ="call",modifier=Modifier.size(40.dp) )
                                Text(
                                    text =  "Ordenar platos elegidos",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    item {
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            enabled= (usuarioViewModel.estadoUser.idMesa!=0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            onClick = { navCont.navigate(route="requestticket")},

                            ){
                            Row (
                                modifier = Modifier.padding(horizontal=10.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_receipt_24), contentDescription ="call",modifier=Modifier.size(40.dp) )
                                Text(
                                    text =  "Pedir la cuenta",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    item {
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            enabled= (usuarioViewModel.estadoUser.idMesa!=0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            onClick = {
                                navCont.navigate(route="callmozo")
                            },
                            ){
                            Row (
                                modifier = Modifier.padding(horizontal=10.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(Icons.Filled.Notifications, contentDescription ="call",modifier=Modifier.size(40.dp) )
                                Text(
                                    text =  "Llamar al mozo",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    item {
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            enabled= (usuarioViewModel.estadoUser.idMesa!=0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            onClick = {
                                tabStateViewModel.setRequestingDataOn()
                                navCont.navigate(route="ordersstate")
                                tabStateViewModel.getPedidosState(usuarioViewModel.estadoUser.idMesa)
                            },
                            ){
                            Row (
                                modifier = Modifier.padding(horizontal=10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_playlist_add_check_circle_24), contentDescription ="carta",modifier=Modifier.size(40.dp) )
                                Text(
                                    text =  "Ver el estado de los pedidos de la mesa",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    item {
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            enabled= (usuarioViewModel.estadoUser.idMesa!=0),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            onClick = { navCont.navigate(route="closetable")},
                            ){
                            Row (
                                modifier = Modifier.padding(horizontal=10.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(Icons.Filled.ExitToApp, contentDescription ="exit",modifier=Modifier.size(40.dp) )
                                Text(
                                    text =  "Retirarse de la mesa",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}