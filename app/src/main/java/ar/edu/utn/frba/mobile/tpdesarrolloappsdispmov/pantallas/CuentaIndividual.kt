package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import java.util.Locale

@Composable
fun CuentaIndividual(navCont:NavController, vistaModeloUsuario: VistaModeloUsuario){
    var total:Float=0.0f
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    Scaffold (
        topBar = { BarraSuperior(vistaModeloUsuario) },
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    text = stringResource(id = R.string.singleticket_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                if(vistaModeloUsuario.estadoUsuario.cargandoConsumo){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }else  if(vistaModeloUsuario.estadoUsuario.resultadoCargandoConsumo == 2){
                    AlertDialog(
                        containerColor = Color(251, 201, 143, 255),
                        icon = { Icon(Icons.Default.Info, "descrip") },
                        title = { Text(text = stringResource(id =R.string.topbar_app)) },
                        text = { Text(text = stringResource(id =R.string.error_api)) },
                        onDismissRequest = {},
                        confirmButton = {
                            TextButton(
                                colors = ButtonDefaults.buttonColors (
                                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                                ),
                                onClick = {
                                    navCont.navigate(route = "pedircuenta")
                                }
                            ) {
                                Text(text = stringResource(id = R.string.btn_ok))
                            }
                        })
                } else {
                    val preparando = vistaModeloUsuario.estadoUsuario.consumos.any { it.estado == "PREPARANDO" }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        items(vistaModeloUsuario.estadoUsuario.consumos) { e ->
                            if(e.estado != "PAGANDO") {
                                total += e.plato.precio * e.cantidad
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .background(color = Color(206, 222, 213, 255))
                                        .border(
                                            0.dp,
                                            color = Color.Transparent,
                                            shape = RoundedCornerShape(0.dp)
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "item",
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = e.plato.nombre,
                                        modifier = Modifier.weight(4f),
                                        style = MaterialTheme.typography.displaySmall
                                    )
                                    if (e.estado == "PREPARANDO") {
                                        Text(
                                            text = stringResource(id = R.string.orderstate_preparando),
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.weight(6f)
                                        )
                                    } else {
                                        Text(
                                            text = "(" + e.cantidad.toString() + "x $" + "%,.1f".format(
                                                Locale.GERMAN,
                                                e.plato.precio
                                            ) + ")",
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.weight(4f)
                                        )
                                        Text(
                                            text = "$" + "%,.1f".format(
                                                Locale.GERMAN,
                                                e.plato.precio * e.cantidad
                                            ),
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.weight(2f)
                                        )
                                    }
                                }
                            }
                        }
                        item{
                            Row {
                                Text(text = stringResource(id = R.string.ticket_total)+"%,.1f".format(Locale.GERMAN,total),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    style= MaterialTheme.typography.displayLarge,
                                    textAlign = TextAlign.End)
                            }
                        }
                    }
                    if(preparando){
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .padding(0.dp)
                                .wrapContentSize().align(Alignment.CenterHorizontally),
                            onClick = {  },
                            shape = CircleShape,
                            containerColor = Color(R.color.alerta),
                            contentColor = MaterialTheme.colorScheme.onError) {
                            Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                            Text(text = stringResource(id = R.string.orderstate_aviso))
                        }
                    } else {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            onClick = {
                                vistaModeloUsuario.pagar()
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_monetization_on_24),
                                    contentDescription = "volver",
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.singleticket_request),
                                    style = MaterialTheme.typography.displayMedium
                                )
                            }
                        )
                    }
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        onClick = { navCont.navigate(route="pedircuenta")},
                        icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                        text = { Text(text = stringResource(id = R.string.btn_back),style= MaterialTheme.typography.displayMedium) }
                    )
                }
                if (vistaModeloUsuario.estadoUsuario.resultPedidoApi == 1){
                    AlertDialog(
                        containerColor = Color(251, 201, 143, 255),
                        icon = { Icon(Icons.Default.Info, "descrip") },
                        title = { Text(text = stringResource(id =R.string.getticket_individual)) },
                        text = { Text(text = stringResource(id =R.string.getticket_confirm)) },
                        onDismissRequest = {
                            navCont.navigate(route = "menuprincipal")
                        },
                        confirmButton = {
                            TextButton(
                                colors = ButtonDefaults.buttonColors (
                                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                                ),
                                onClick = {
                                    vistaModeloUsuario.desactivarErrorPedidoApi()
                                    navCont.navigate(route = "menuprincipal")
                                }
                            ) {
                                Text(text = stringResource(id = R.string.btn_ok))
                            }
                        })
                }
                if (vistaModeloUsuario.estadoUsuario.resultPedidoApi == 2){
                    toast.setGravity(Gravity.TOP,0,0)
                    toast.show()
                    vistaModeloUsuario.desactivarErrorPedidoApi()
                }
            }
        }
    )
}