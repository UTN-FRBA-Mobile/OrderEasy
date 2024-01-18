package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import android.view.Gravity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun MenuPrincipal(navCont: NavController, usuarioViewModel: VistaModeloUsuario, tabStateViewModel:VistaModeloMesa) {
    var mostrarDialog by remember { mutableStateOf(true) }
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    val scanLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val parts = result.contents.split("/")
            val idMesa = parts[parts.indexOf("registrarse") + 1]
            val hash = parts[parts.indexOf("registrarse") + 2]
            usuarioViewModel.tomarMesa(idMesa.toInt(), hash)
        }
    }
    Scaffold (
        topBar = {BarraSuperior(usuarioViewModel)},
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                if(usuarioViewModel.estadoUsuario.idMesa == 0 && mostrarDialog) {
                    AlertDialog(
                        containerColor = Color(251, 201, 143, 255),
                        icon = { Icon(Icons.Default.Info, "call-mozo") },
                        title = { Text(text = stringResource(id = R.string.topbar_app)) },
                        text = { Text(text = stringResource(id = R.string.mainmenu_msj)) },
                        onDismissRequest = {},
                        confirmButton = {
                            TextButton(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                                ),
                                onClick = {
                                    mostrarDialog=false
                                }
                            ) {
                                Text(text = stringResource(id = R.string.btn_ok))
                            }
                        })
                }
                if(usuarioViewModel.estadoUsuario.pidiendoDatos){ //escaneando){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }else {
                    LazyColumn {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                                enabled = (usuarioViewModel.estadoUsuario.idMesa == 0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 8.dp),
                                onClick = {
                                    val options = ScanOptions()
                                    options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                                    options.setPrompt("")
                                    options.setBeepEnabled(false)
                                    options.setBarcodeImageEnabled(true)
                                    scanLauncher.launch(options)
                                },
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 14.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_qr_code_24),
                                        contentDescription = "carta",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.mainmenu_scan),
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
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                                enabled = (usuarioViewModel.estadoUsuario.idMesa != 0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 8.dp),
                                onClick = {
                                    navCont.navigate(route = "readmenu")
                                },
                                ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 14.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_menu_book_24),
                                        contentDescription = "carta",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.mainmenu_carta),
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
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                                enabled = (usuarioViewModel.estadoUsuario.idMesa != 0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 8.dp),
                                onClick = { navCont.navigate(route = "makeorder") },

                                ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 14.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_room_service_24),
                                        contentDescription = "call",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.mainmenu_ordenar),
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
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                                enabled = (usuarioViewModel.estadoUsuario.idMesa != 0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 8.dp),
                                onClick = { navCont.navigate(route = "requestticket") },

                                ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 14.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_receipt_24),
                                        contentDescription = "call",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.mainmenu_ticket),
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
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                                enabled = (usuarioViewModel.estadoUsuario.idMesa != 0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 8.dp),
                                onClick = {
                                    navCont.navigate(route = "callmozo")
                                },
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 14.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Filled.Notifications,
                                        contentDescription = "call",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.mainmenu_mozo),
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
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                                enabled = (usuarioViewModel.estadoUsuario.idMesa != 0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 8.dp),
                                onClick = {
                                    tabStateViewModel.setearPedidoDatos()
                                    navCont.navigate(route = "ordersstate")
                                    tabStateViewModel.obtenerEstadoPedidos(usuarioViewModel.estadoUsuario.idMesa)
                                },
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 8.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_playlist_add_check_circle_24),
                                        contentDescription = "carta",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.mainmenu_estado),
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
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                ),
                                enabled = (usuarioViewModel.estadoUsuario.idMesa != 0),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp, vertical = 8.dp),
                                onClick = {
                                    navCont.navigate(route = "closetable")
                                },
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 14.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Filled.ExitToApp,
                                        contentDescription = "exit",
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.mainmenu_exit),
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
                if (usuarioViewModel.estadoUsuario.errorPedidoApi){ //.errorEscan){
                    toast.setGravity(Gravity.TOP,0,0)
                    toast.show()
                    usuarioViewModel.cancelarErrorPedApi()
                }
            }
        }
    )
}