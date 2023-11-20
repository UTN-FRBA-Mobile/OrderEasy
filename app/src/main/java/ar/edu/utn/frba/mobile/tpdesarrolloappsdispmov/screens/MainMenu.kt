package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(navCont: NavController) {
    Scaffold(
        topBar = {
            OderEasyTopAppBar(navCont)
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 16.dp + innerPadding.calculateTopPadding(),
                    end = 16.dp,
                    bottom = 16.dp + innerPadding.calculateBottomPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    MenuButton("Escanear QR", R.drawable.icon_notification_pay) { //TODO: buscar icono para el boton
                        navCont.navigate(route = "scanqr")
                    }
                }
                item {
                    MenuButton("Ver carta/menu", R.drawable.icon_notification_pay) { //TODO: buscar icono para el boton
                        navCont.navigate(route = "readmenu")
                    }
                }
                item {
                    MenuButton("Hacer el pedido", R.drawable.icon_notification_pay) { //TODO: buscar icono para el boton
                        navCont.navigate(route = "makeorder")
                    }
                }
                item {
                    MenuButton("Pedir la cuenta", R.drawable.icon_notification_pay) { //TODO: buscar icono para el boton
                        navCont.navigate(route = "requestticket")
                    }
                }
                item {
                    MenuButton("Llamar mozo", R.drawable.icon_notification_pay) { //TODO: buscar icono para el boton
                        navCont.navigate(route = "callmozo")
                    }
                }
                item {
                    MenuButton("Ver pedidos de la mesa", R.drawable.icon_notification_pay) { //TODO: buscar icono para el boton
                        navCont.navigate(route = "ordersstate")
                    }
                }
                item {
                    MenuButton("Retirarse de la mesa", R.drawable.icon_notification_pay) { //TODO: buscar icono para el boton
                        navCont.navigate(route = "closetable")
                    }
                }
            }
        }
    )
}

@Composable
fun MenuButton(text: String, @DrawableRes iconId: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .wrapContentSize(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
        contentPadding = PaddingValues(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = text)
        }
    }
}