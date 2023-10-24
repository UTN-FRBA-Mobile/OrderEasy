package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun MainMenu(navCont: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "BARRA SUPERIOR DE LA APP") })
        },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize()){
                Button(
                    onClick = {navCont.navigate(route="scanqr")},
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "Escanear QR")
                }
                Button(
                    onClick = {navCont.navigate(route="readmenu")},
                    modifier = Modifier
                        .fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "Ver carta/menu")
                }
                Button(
                    onClick = {navCont.navigate(route="makeorder")},
                    modifier = Modifier
                        .fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "Hacer el pedido")
                }
                Button(
                    onClick = {navCont.navigate(route="requestticket")},
                    modifier = Modifier
                        .fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "Pedir la cuenta")
                }
                Button(
                    onClick = {navCont.navigate(route="callmozo")},
                    modifier = Modifier
                        .fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "Llamar mozo")
                }
                Button(
                    onClick = {navCont.navigate(route="ordersstate")},
                    modifier = Modifier
                        .fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "Ver pedidos de la mesa")
                }
                Button(
                    onClick = {navCont.navigate(route="closetable")},
                    modifier = Modifier
                        .fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "Retirarse de la mesa")
                }
            }
        }
    )
}