package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun ScanQr(navCont: NavController,userViewModel: UserViewModel) {
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.inversePrimary)
                    .padding(40.dp)) {
                    Text(text = "El codigo qr es una URL = https://restowebback-production.up.railway.app/mesas/registrarse/:idMesa/:hash")
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.inversePrimary)
                    .padding(40.dp)) {
                    Text(text = "QR = https://restowebback-production.up.railway.app/mesas/registrarse/2/JDJiJDEwJGhNUlEvRlBWVlZ5SVI3TGlSR2NKdWU5bjA1L0NJOG9xWmxEMlFXdno1SjhQQXFtVVNtZm9P")
                }

                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = {
                        userViewModel.takeTable(2,"JDJiJDEwJGhNUlEvRlBWVlZ5SVI3TGlSR2NKdWU5bjA1L0NJOG9xWmxEMlFXdno1SjhQQXFtVVNtZm9P")
                        navCont.navigate(route="mainmenu")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver")},
                    text = { Text(text = "Tocar para simular el escaneo del qr (sobre la idMesa=5)") },
                )

                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = { navCont.navigate(route="mainmenu")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                    text = { Text(text = "Volver al menu") },
                )
            }
        }
    )
}