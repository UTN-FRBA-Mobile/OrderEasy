package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

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
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.VolverBtn
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersState(navCont: NavController, viewmodelo: TableViewModel,userViewModel: UserViewModel) {
    Scaffold (
        topBar = { TopBar(userViewModel)},
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
                if(viewmodelo.estadoMesa.requestingData){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
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
                                    cli.Pedidos.forEach { ped ->
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
                                                text = ped.Plato.nombre + " (x" + ped.cantidad.toString() + ")",
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
                                VolverBtn(navCont)
                            }
                        }
                        ExtendedFloatingActionButton(
                            modifier = Modifier.align(Alignment.TopEnd).padding(0.dp)
                                .wrapContentSize(),
                            onClick = { viewmodelo.getPedidosState(userViewModel.estadoUser.idMesa) },
                            shape = CircleShape,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.secondary
                        ) {
                            Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "")
                            Text(text = "Actualizar")
                        }
                    }
                }
            }
        }
    )
}