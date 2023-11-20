package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.ItemMenu
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
                        ItemMenu(titulo = "Escanear QR",ImageVector.vectorResource(id = R.drawable.baseline_qr_code_24),
                            navCont, "scanqr")
                    }
                    item {
                        ItemMenu(titulo = "Ver el menu de platos",ImageVector.vectorResource(id = R.drawable.baseline_menu_book_24),
                            navCont, "readmenu")
                    }
                    item {
                        ItemMenu(titulo = "Ordenar platos elegidos",ImageVector.vectorResource(id = R.drawable.baseline_room_service_24),
                            navCont,"makeorder")
                    }
                    item {
                        ItemMenu(titulo = "Pedir la cuenta", ImageVector.vectorResource(id = R.drawable.baseline_receipt_24),
                            navCont,ruta="requestticket")
                    }
                    item {
                        ItemMenu(titulo = "Llamar al mozo",Icons.Filled.Notifications,navCont,"callmozo")
                    }
                    item {
                        ExtendedFloatingActionButton(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp,vertical=14.dp),
                            onClick = {
                                tabStateViewModel.setRequestingDataOn()
                                navCont.navigate(route="ordersstate")
                                tabStateViewModel.getPedidosState(usuarioViewModel.estadoUser.idMesa)
                            },
                            icon = { Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_playlist_add_check_circle_24), contentDescription ="carta",modifier=Modifier.size(40.dp) )},
                            text = {
                                Text(
                                    text = "Ver el estado de los pedidos de la mesa",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                        )
                    }
                    item {
                        ItemMenu(titulo = "Retirarse de la mesa",Icons.Filled.ExitToApp,navCont,"closetable")
                    }
                }
            }
        }
    )
}