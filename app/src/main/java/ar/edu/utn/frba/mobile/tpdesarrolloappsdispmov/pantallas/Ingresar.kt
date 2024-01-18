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
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@Composable
fun Ingresar (usuarioViewModel:VistaModeloUsuario){
    var nombre by remember { mutableStateOf("") }
    var mostrarDialog by remember { mutableStateOf(false) }
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    Scaffold (
        topBar = { BarraSuperior(userViewModel = usuarioViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                if (usuarioViewModel.estadoUser.registrandoUsuarioApi) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.login_title),
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text(text = stringResource(id = R.string.login_label)) })

                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                            .padding(vertical = 8.dp),
                        onClick = { usuarioViewModel.log(nombre) },
                        icon = { Icon(Icons.Sharp.ArrowForward, contentDescription = "volver") },
                        text = {
                            Text(
                                text = stringResource(id = R.string.login_btn),
                                style = MaterialTheme.typography.titleSmall
                            ) },
                    )
                    if (mostrarDialog) {
                        AlertDialog(
                            containerColor = Color(251, 201, 143, 255),
                            icon = { Icon(Icons.Default.Info, "call-mozo") },
                            title = { Text(text = stringResource(R.string.login_dialog_title)) },
                            text = { Text(text = stringResource(R.string.login_dialog_txt)) },
                            onDismissRequest = { },
                            confirmButton = {
                                TextButton(
                                    colors = ButtonDefaults.buttonColors (
                                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                                    ),
                                    onClick = { mostrarDialog = false }
                                ) {
                                    Text(text = stringResource(id = R.string.btn_ok))
                                }
                            })
                    }
                    if (usuarioViewModel.estadoUser.errorRegistrandoUsuarioApi){
                        toast.setGravity(Gravity.TOP,0,0)
                        toast.show()
                        usuarioViewModel.cancelarErrorRegistroUsuarioApi()
                    }
                }
            }
        }
    )
}