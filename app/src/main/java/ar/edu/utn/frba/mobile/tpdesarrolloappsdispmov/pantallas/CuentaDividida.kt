package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.Consumidor
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import java.util.Locale
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.ComensalData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.PedidoData

@Composable
fun CuentaDividida(navCont: NavController, userViewModel: VistaModeloUsuario, vistaModeloMesa: VistaModeloMesa){
    val consumComensales: MutableList<ComensalData> = mutableListOf()
    consumComensales.addAll(vistaModeloMesa.estadoMesa.consumosMesa)
    consumComensales.forEach{ e ->
        var aux: ArrayList<PedidoData> = ArrayList<PedidoData>()
        aux.addAll(e.pedidos)
        e.pedidos.clear()
        e.pedidos.addAll(aux.filter{it.estado != "PAGANDO"})
    }
    val totGastoMesa = consumComensales.fold(0.0f){ ac, e -> ac+e.pedidos.fold(0.0f){ acc, i -> acc+i.cantidad*i.plato.precio} }
    val gastoIndividual = if(consumComensales.isEmpty()) 0 else totGastoMesa/consumComensales.size
    var mostrarDialog by remember { mutableStateOf(false) }
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    Scaffold (
        topBar = { BarraSuperior(userViewModel) },
        content = { innerPadding ->
            if(vistaModeloMesa.estadoMesa.pidiendoConsumos){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else if(vistaModeloMesa.estadoMesa.resultPedidoConsumos==2){
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
                                vistaModeloMesa.limpiarPedidoConsumos()
                                navCont.navigate(route ="pedircuenta")
                            }
                        ) {
                            Text(text = stringResource(id = R.string.btn_ok))
                        }
                    })
            } else {
                val pendientes = vistaModeloMesa.estadoMesa.consumosMesa.any { it.pedidos.any{ it.estado=="PREPARANDO"} }
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(innerPadding)){
                    item {
                        Column {
                            if (pendientes){
                                ExtendedFloatingActionButton(
                                    modifier = Modifier
                                        .offset(y=2.dp)
                                        .padding(0.dp)
                                        .wrapContentSize().align(Alignment.CenterHorizontally),
                                    onClick = {  },
                                    shape = CircleShape,
                                    containerColor = Color(R.color.alerta),
                                    contentColor = MaterialTheme.colorScheme.onTertiary) {
                                    Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                                    Text(text = stringResource(id = R.string.orderstate_aviso))
                                }
                            }else {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    text = stringResource(id = R.string.ticket_table) + "%,.1f".format(Locale.GERMAN, totGastoMesa),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    text = stringResource(id = R.string.ticket_individual) + "%,.1f".format( Locale.GERMAN, gastoIndividual),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                text = stringResource(id = R.string.ticket_detalle),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    }
                    consumComensales.forEach { consm ->
                        item {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row (modifier = Modifier.padding(8.dp)){
                                    Icon(imageVector = Icons.Filled.Person,contentDescription = "user")
                                    Text(text = consm.nombre,style = MaterialTheme.typography.titleMedium)
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                Column {
                                    var tot=0.0f
                                    consm.pedidos.forEach { ped ->
                                        Consumidor(ped)
                                        tot += ped.cantidad * ped.plato.precio
                                    }
                                    Text(text = stringResource(id = R.string.ticket_total)+"$"+"%,.1f".format(Locale.GERMAN,tot),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        textAlign = TextAlign.End,
                                        style = MaterialTheme.typography.titleMedium)
                                }
                            }
                            Spacer(modifier = Modifier.size(40.dp))
                        }
                    }
                    if(!pendientes) {
                        item {
                            ExtendedFloatingActionButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                onClick = {
                                    userViewModel.iniciarDividirConsumo()
                                },
                                icon = {
                                    Icon(
                                        Icons.Filled.ArrowBack,
                                        contentDescription = "volver"
                                    )
                                },
                                text = {
                                    Text(
                                        text = stringResource(id = R.string.ticket_invite),
                                        style = MaterialTheme.typography.titleSmall,
                                        textAlign = TextAlign.Center
                                    )
                                },
                            )
                        }
                    }
                    item {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            onClick = { navCont.navigate(route = "pedircuenta") },
                            icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "volver") },
                            text = { Text(text = stringResource(id = R.string.btn_back),style = MaterialTheme.typography.titleSmall) },
                        )
                    }
                }
            }
            if (userViewModel.estadoUsuario.resultPedidoApi == 1) {
                AlertDialog(
                    containerColor = Color(251, 201, 143, 255),
                    icon = { Icon(Icons.Default.Info, "call-mozo") },
                    title = { Text(text = stringResource(id = R.string.ticket_dialog_title)) },
                    text = { Text(text = stringResource(id = R.string.ticket_dialog_txt)) },
                    onDismissRequest = {},
                    confirmButton = {
                        TextButton(
                            colors = ButtonDefaults.buttonColors (
                                containerColor = MaterialTheme.colorScheme.inverseSurface,
                            ),
                            onClick = {
                                mostrarDialog = false
                                userViewModel.desactivarErrorPedidoApi()
                                navCont.navigate(route="menuprincipal")
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
    )
}