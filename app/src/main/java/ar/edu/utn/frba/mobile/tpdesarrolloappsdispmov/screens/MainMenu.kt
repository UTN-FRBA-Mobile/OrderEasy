package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
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
import androidx.navigation.NavHostController
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
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ){
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    onClick = {navCont.navigate(route="scanqr")},
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="scan" )},
                    text = {Text(text="Escanear QR")}
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    onClick = {navCont.navigate(route="readmenu")},
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="carta" )},
                    text = {Text(text="Ver el menu")}
                )
                ExtendedFloatingActionButton(
                    onClick = {navCont.navigate(route="makeorder")},
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="carta" )},
                    text ={Text(text = "Hacer el pedido")}
                )
                ExtendedFloatingActionButton(
                    onClick = {navCont.navigate(route="requestticket")},
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="carta" )},
                    text={Text(text = "Pedir la cuenta")}
                )
                ExtendedFloatingActionButton(
                    onClick = {navCont.navigate(route="callmozo")},
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="carta" )},
                    text = {Text(text = "Llamar mozo")}
                )
                ExtendedFloatingActionButton(
                    onClick = {
                        tabStateViewModel.getPedidosState(usuarioViewModel.estadoUser.idMesa)
                        navCont.navigate(route="ordersstate")},
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="carta" )},
                    text = {Text(text = "Ver pedidos de la mesa")}
                )
                ExtendedFloatingActionButton(
                    onClick = {navCont.navigate(route="closetable")},
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="carta" )},
                    text = {Text(text = "Retirarse de la mesa")}
                )
                /*ExtendedFloatingActionButton(
                    onClick = { usuarioViewModel.clearSavedData()},
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    icon = { Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription ="carta" )},
                    text = {Text(text = "LogOut")}
                )*/
            }
        }
    )
}