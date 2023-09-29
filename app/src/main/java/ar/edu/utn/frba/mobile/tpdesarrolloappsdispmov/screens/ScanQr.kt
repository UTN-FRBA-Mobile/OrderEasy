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

@Composable
fun ScanQr(navCont: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "BARRA SUPERIOR DE LA APP") })
        },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize()){
                Text(
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize(),
                    text = "/* SE HABRE LA CAMARA Y SE ENFOCA EL CODIGO QR */"
                )
                Button(
                    onClick = {navCont.navigate(route="mainmenu")},
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "volver al menu")
                }
            }
        }
    )
}