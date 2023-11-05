package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.Consumer
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserInvitedData


@Composable
fun InviteTicket(navCont: NavController, userViewModel: UserViewModel, tableViewModel: TableViewModel){
    Scaffold (
        topBar = { TopBar(userViewModel) },
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween//.spacedBy(4.dp)
                ){
                    item {Text(text = "Elige a quien queres invitar y pagar su cuenta") }
                    item {
                        FilterChip(
                            selected = true,
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            onClick = { /*TODO*/ },
                            label = {
                                Row (
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    horizontalArrangement = Arrangement.End
                                ){
                                    val indice:Int =tableViewModel.estadoMesa.invitados.indexOfFirst{ e -> e.idCliente == userViewModel.estadoUser.idCliente}
                                    val yo = if(indice !=-1) tableViewModel.estadoMesa.invitados[indice] else null
                                   if (yo != null) {
                                        //tableViewModel.selectInvited(yo.idCliente)
                                        Text(text ="Gasto: $"+yo.total.toString())
                                    }
                                }
                            },
                            leadingIcon = {
                                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                                        Icon(Icons.Filled.AccountCircle, contentDescription = "userIn")
                                        Text(text = "Yo")
                                    }
                            },
                        )                        
                    }

                    items(tableViewModel.estadoMesa.invitados) { cons ->
                        if (cons.idCliente != userViewModel.estadoUser.idCliente){
                            FilterChip(
                                selected = cons.selected,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                onClick = {tableViewModel.selectInvited(cons.idCliente)},//cons.selected = !cons.selected },
                                label = {
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(20.dp),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Icon(Icons.Filled.AccountCircle, contentDescription = "userOut")
                                            Text(text = cons.nombre)
                                        }
                                        Row (
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(text = "Consumido: $"+cons.total.toString())
                                        }
                                    }
                                },
                                leadingIcon = {
                                    if(cons.selected) {
                                        Icon(Icons.Filled.CheckCircle, contentDescription = "userIn")
                                    }else{
                                        Icon(Icons.Filled.ArrowForward, contentDescription = "userOut")
                                    }
                                },
                                /*trailingIcon ={
                                    if(cons.selected) {
                                        Icon(Icons.Outlined.Close, contentDescription = "userIn")
                                    }else{
                                        Icon(Icons.Filled.Add, contentDescription = "userOut",
                                            modifier = Modifier.
                                    }
                                }*/
                            )                            
                        }
                    }
                    item {Text(text = "Total a Pagar: $"+tableViewModel.estadoMesa.invitados.filter { e ->e.selected }.fold(0.0f ) {acc, i -> acc + i.total}.toString() )}


                    item{
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            onClick = {
                                tableViewModel.pagarInvitado(userViewModel.estadoUser.idCliente)
                                navCont.navigate(route="mainmenu")},
                            icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                            text = { Text(text = "Pedir la cuenta") },
                        )
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            onClick = { navCont.navigate(route="requestTicket")},
                            icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                            text = { Text(text = "Volver") },
                        )

                    }
                }
            }
        }
    )
}