package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Refresh
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EstadoDeOrdenes(navCont: NavController, viewmodelo: VistaModeloMesa, vistaModeloUsuario: VistaModeloUsuario) {
    Scaffold (
        topBar = { BarraSuperior(vistaModeloUsuario)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.orderstate_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                if(viewmodelo.estadoMesa.pidiendoDatos){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }else if (viewmodelo.estadoMesa.resultPedidoApi ==2){
                    AlertDialog(
                        containerColor = Color(251, 201, 143, 255),
                        icon = { Icon(Icons.Default.Info, "call-mozo") },
                        title = { Text(text = stringResource(id = R.string.topbar_app)) },
                        text = { Text(text = stringResource(id = R.string.error_api)) },
                        onDismissRequest = {},
                        confirmButton = {
                            TextButton(
                                colors = ButtonDefaults.buttonColors (
                                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                                ),
                                onClick = {
                                    navCont.navigate(route = "menuprincipal")
                                    viewmodelo.desactivarErrorPedidoApi()
                                }
                            ) {
                                Text(text = stringResource(id = R.string.btn_ok))
                            }
                        })
                }else {
                    Box {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            viewmodelo.estadoMesa.pedidosMesa.forEach { cli ->
                                stickyHeader {
                                    Row(
                                        modifier = Modifier
                                            .padding(vertical = 10.dp)
                                            .height(38.dp)
                                            .fillMaxWidth()
                                            .background(Color(0xABD1E8FF)),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AccountCircle,
                                            contentDescription = "user"
                                        )
                                        Text(
                                            text = cli.nombre,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                                item {
                                    cli.pedidos.forEach { ped ->
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.background(
                                                color = Color(
                                                    206,
                                                    222,
                                                    213,
                                                    255
                                                )
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_flatware_24),
                                                contentDescription = "carta",
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .weight(1f)
                                            )
                                            Text(
                                                text = ped.plato.nombre + " (x" + ped.cantidad.toString() + ")",
                                                modifier = Modifier.weight(8f),
                                                style = MaterialTheme.typography.displaySmall
                                            )
                                            Text(
                                                text = ped.estado,
                                                modifier = Modifier.weight(4f),
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Spacer(modifier = Modifier.padding(vertical = 1.dp))
                                        }
                                    }
                                }
                            }
                            item {
                                ExtendedFloatingActionButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentSize()
                                        .padding(vertical = 8.dp),
                                    onClick = {
                                        viewmodelo.desactivarErrorPedidoApi()
                                        navCont.navigate(route = "menuprincipal")
                                    },
                                    icon = {
                                        Icon(
                                            Icons.Filled.ArrowBack,
                                            contentDescription = "volver"
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = stringResource(id = R.string.btn_volver),
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                    },
                                )
                            }
                        }
                        ExtendedFloatingActionButton(
                            modifier = Modifier.align(Alignment.TopEnd).padding(0.dp).wrapContentSize(),
                            onClick = { viewmodelo.obtenerEstadoPedidos(vistaModeloUsuario.estadoUsuario.idMesa) },
                            shape = CircleShape,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.secondary) {
                            Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "")
                            Text(text = "Actualizar")
                        }
                    }
                }
            }
        }
    )
}