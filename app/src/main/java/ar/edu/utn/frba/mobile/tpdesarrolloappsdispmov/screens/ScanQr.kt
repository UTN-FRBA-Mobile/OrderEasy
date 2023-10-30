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
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun ScanQr(navCont: NavController,userViewModel: UserViewModel) {
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
                Text(
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize(),
                    text = "El codigo qr es una URL = https://restowebback-production.up.railway.app/mesas/registrarse/:idMesa/:hash"
                )
                Button(
                    onClick = {navCont.navigate(route="mainmenu")},
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "volver al menu")
                }
                Button(
                    onClick = {
                        userViewModel.takeTable(5)
                        navCont.navigate(route="mainmenu")
                    },
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "simulando que se escaneo el codigo qr en la MESA=5")
                    Text(text = "QR = https://restowebback-production.up.railway.app/mesas/registrarse/5/JDJiJDEwJHE4Tk0uTS5zSXVldGFzdElacnI2a2VDM1guYlU5SS4vZ0V2N09BRnBod2QydEIvTXlWTEJp")
                }
            }
        }
    )
}