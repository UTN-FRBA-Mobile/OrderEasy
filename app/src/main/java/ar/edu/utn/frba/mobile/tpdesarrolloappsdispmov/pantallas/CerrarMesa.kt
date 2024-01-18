package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.VolverBtn
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@Composable
fun CerrarMesa(navCont: NavController, vistaModeloUsuario: VistaModeloUsuario) {
    var mostrarDialog by remember { mutableStateOf(false) }
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    Scaffold (
        topBar = { BarraSuperior(vistaModeloUsuario)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                ElevatedCard (
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.padding(20.dp),
                )
                {
                    if(vistaModeloUsuario.estadoUsuario.pidiendoDatos){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                        if(!vistaModeloUsuario.estadoUsuario.errorPedidoApi){
                            mostrarDialog = true
                        }
                    }else {
                        Text(
                            text = stringResource(id = R.string.closetab_title),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.displayMedium
                        )

                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize()
                                .padding(vertical = 8.dp),
                            onClick = {
                                vistaModeloUsuario.retirarseDeMesa()
                            },
                            icon = {Icon(Icons.Filled.Notifications,contentDescription = "call" ) },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.closetab_btn),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            },
                        )
                        VolverBtn(navCont)
                        if (mostrarDialog) {
                            AlertDialog(
                                containerColor = Color(251, 201, 143, 255),
                                icon = { Icon(Icons.Default.Info, "call-mozo") },
                                title = { Text(text = stringResource(id = R.string.closetab_dialog_title)) },
                                text = { Text(text = stringResource(id = R.string.closetab_dialog_txt)) },
                                onDismissRequest = { /*TODO*/ },
                                confirmButton = {
                                    TextButton(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.inverseSurface,
                                        ),
                                        onClick = {
                                            mostrarDialog = false
                                            navCont.navigate(route = "mainmenu")
                                        }
                                    ) {
                                        Text(text = "ok")
                                    }
                                })
                        }
                        if (vistaModeloUsuario.estadoUsuario.errorPedidoApi){
                            toast.setGravity(Gravity.TOP,0,0)
                            toast.show()
                            vistaModeloUsuario.cancelarErrorPedApi()
                        }
                    }
                }
            }
        }
    )
}