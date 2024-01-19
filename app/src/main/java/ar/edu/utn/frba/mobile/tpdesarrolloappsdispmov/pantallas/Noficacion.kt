package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario

@Composable
fun Notificacion(navController: NavController,userViewModel: VistaModeloUsuario){
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    Scaffold (
        topBar = {BarraSuperior(userViewModel) },
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                ElevatedCard (
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.padding(20.dp)
                )
                {
                    Text(
                        text = "Se dividirá el total gastado en la mesa de $${userViewModel.estadoUsuario.gastoADividir} entre los ${userViewModel.estadoUsuario.cantDividida} comensales," +
                                " pagando cada uno $${userViewModel.estadoUsuario.gastoIndDivide}. Si alguno de los integrantes de la mesa no acepta la propuesta, entonces se cancelará ésta" +
                                " forma de pago",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ){
                    if(userViewModel.estadoUsuario.pidiendoDatos){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                        if(!userViewModel.estadoUsuario.errorPedidoApi){
                            navController.navigate(route="menuprincipal")
                        }
                    }else {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .padding(10.dp),
                            onClick = {
                                userViewModel.aceptarDividirConsumo()
                            },
                            icon = { Icon(Icons.Filled.Check,  contentDescription ="ver") },
                            text = { Text(text = stringResource(id = R.string.accept), style= MaterialTheme.typography.labelSmall) },
                        )
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .padding(10.dp),
                            onClick = {
                                userViewModel.rechazarDividirConsumo()
                            },
                            icon = { Icon(Icons.Filled.Close,  contentDescription ="ver") },
                            text = { Text(text = stringResource(id = R.string.decline), style= MaterialTheme.typography.labelSmall) },
                        )
                    }
                    if (userViewModel.estadoUsuario.errorPedidoApi){
                        toast.setGravity(Gravity.TOP,0,0)
                        toast.show()
                        userViewModel.cancelarErrorPedApi()
                    }
                }
            }
        }
    )
}