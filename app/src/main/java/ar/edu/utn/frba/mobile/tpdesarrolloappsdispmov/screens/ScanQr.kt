package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ScanQr(navCont: NavController,userViewModel: UserViewModel) {
    val scanLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val parts = result.contents.split("/")
            val idMesa = parts[parts.indexOf("registrarse") + 1]
            val hash = parts[parts.indexOf("registrarse") + 2]
            userViewModel.takeTable(idMesa.toInt(), hash)
            navCont.navigate(route="mainmenu")
        }
    }
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = {
                        val options = ScanOptions()
                        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                        options.setPrompt("")
                        options.setBeepEnabled(false)
                        options.setBarcodeImageEnabled(true)
                        scanLauncher.launch(options)
                    },
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Escáner QR") },
                    text = { Text(text = "Escanear QR") }
                )
                /*Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.inversePrimary)
                    .padding(40.dp)) {
                    Text(text = "El codigo qr es una URL = https://restowebback-production.up.railway.app/mesas/registrarse/:idMesa/:hash")
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.inversePrimary)
                    .padding(40.dp)) {
                    Text(text = "QR = https://restowebback-production.up.railway.app/mesas/registrarse/5/JDJiJDEwJGRpSTV2QS84ZHhzeEZ6cFcxMi9QMC5vQmpBc3RBaHRZZmFCZDBxSVVYWjEvR2F5WGZEWk9L")
                }
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = {
                        userViewModel.takeTable(5,"JDJiJDEwJGRpSTV2QS84ZHhzeEZ6cFcxMi9QMC5vQmpBc3RBaHRZZmFCZDBxSVVYWjEvR2F5WGZEWk9L")
                        navCont.navigate(route="mainmenu")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver")},
                    text = { Text(text = "Tocar para simular el escaneo del qr (sobre la idMesa=5)") },
                )*/

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