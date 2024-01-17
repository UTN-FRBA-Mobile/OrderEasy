package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.Consumer
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import java.util.Locale
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R


@Composable
fun DivideTicket(navCont: NavController, userViewModel: UserViewModel, tableViewModel: TableViewModel){
    val totGastoMesa = tableViewModel.estadoMesa.consumosMesa.fold(0.0f){ac,e -> ac+e.Pedidos.fold(0.0f){ acc, i -> acc+i.cantidad*i.Plato.precio} }
    val gastoIndividual = if(tableViewModel.estadoMesa.consumosMesa.size==0) 0 else totGastoMesa/tableViewModel.estadoMesa.consumosMesa.size
    var showDialog by remember { mutableStateOf(false) }
    Scaffold (
        topBar = { TopBar(userViewModel) },
        content = { innerPadding ->
            if(tableViewModel.estadoMesa.requestingData){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else {
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(innerPadding)){
                    item {
                        Column {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                text = stringResource(id = R.string.ticket_table)+"%,.1f".format(Locale.GERMAN,totGastoMesa),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                text = stringResource(id = R.string.ticket_individual)+"%,.1f".format(Locale.GERMAN,gastoIndividual),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium
                            )
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
                    tableViewModel.estadoMesa.consumosMesa.forEach { consm ->
                        item {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row (modifier = Modifier.padding(8.dp)){
                                    Icon(imageVector = Icons.Filled.Person,contentDescription = "user")
                                    Text(text = consm.nombre,style = MaterialTheme.typography.titleMedium)
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                Column (){
                                    var tot:Float=0.0f
                                    consm.Pedidos.forEach {ped ->
                                        Consumer(ped)
                                        tot = tot + ped.cantidad * ped.Plato.precio
                                    }
                                    Text(text = stringResource(id = R.string.ticket_total)+"%,.1f".format(Locale.GERMAN,tot),
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

                    item {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            onClick = {
                                userViewModel.iniciarDividirConsumo()
                                showDialog = true
                            },
                            icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "volver") },
                            text = { Text(text = stringResource(id = R.string.ticket_invite),
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center) },
                        )
                    }
                    item {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            onClick = { navCont.navigate(route = "requestTicket") },
                            icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "volver") },
                            text = { Text(text = stringResource(id = R.string.btn_back),style = MaterialTheme.typography.titleSmall) },
                        )
                    }
                    item{
                        if (showDialog) {
                            AlertDialog(
                                containerColor = Color(251, 201, 143, 255),
                                icon = { Icon(Icons.Default.Info, "call-mozo") },
                                title = { Text(text = stringResource(id = R.string.ticket_dialog_title)) },
                                text = { Text(text = stringResource(id = R.string.ticket_dialog_txt)) },
                                onDismissRequest = { },
                                confirmButton = {
                                    TextButton(
                                        colors = ButtonDefaults.buttonColors (
                                            containerColor = MaterialTheme.colorScheme.inverseSurface,
                                        ),
                                        onClick = {
                                            showDialog = false
                                            navCont.navigate(route="mainmenu")
                                        }
                                    ) {
                                        Text(text = stringResource(id = R.string.btn_ok))
                                    }
                                })
                        }
                    }
                }
            }
        }
    )
}