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
fun LlamarMozo(navCont: NavController, userViewModel: VistaModeloUsuario) {
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    Scaffold (
        topBar = { BarraSuperior(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                if(userViewModel.estadoUsuario.pidiendoDatos ){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }else {
                    ElevatedCard (
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        modifier = Modifier.padding(20.dp),
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.callmozo_title),
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
                                userViewModel.llamarmozo(userViewModel.estadoUsuario.idMesa)
                            },
                            icon = { Icon(Icons.Filled.Notifications,  contentDescription ="call") },
                            text = { Text(
                                text = stringResource(id = R.string.callmozo_btn),
                                style = MaterialTheme.typography.titleSmall
                            ) },
                        )
                        VolverBtn(navCont)
                    }
                }
                if (userViewModel.estadoUsuario.resultPedidoApi == 1) {
                    AlertDialog(
                        containerColor = Color(251, 201, 143, 255),
                        icon = { Icon(Icons.Default.Info, "call-mozo") },
                        title = { Text(text = stringResource(id = R.string.callmozo_dialog_title)) },
                        text = { Text(text = stringResource(id = R.string.callmozo_dialog_txt)) },
                        onDismissRequest = { },
                        confirmButton = {
                            TextButton(
                                colors = ButtonDefaults.buttonColors (
                                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                                ),
                                onClick = {
                                    userViewModel.desactivarErrorPedidoApi()
                                    navCont.navigate(route="mainmenu")
                                }
                            ) {
                                Text(text = stringResource(id = R.string.btn_ok))
                            }
                        })
                }
                if (userViewModel.estadoUsuario.resultPedidoApi == 2){
                    toast.setGravity(Gravity.TOP,0,0)
                    toast.show()
                    userViewModel.desactivarErrorPedidoApi()
                }
            }
        }
    )
}