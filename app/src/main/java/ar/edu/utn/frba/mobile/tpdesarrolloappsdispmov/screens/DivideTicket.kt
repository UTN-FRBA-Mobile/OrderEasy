package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.Consumer
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel


@Composable
fun DivideTicket(navCont: NavController, userViewModel: UserViewModel, tableViewModel: TableViewModel){
    //tableViewModel.getConsumosState(userViewModel.estadoUser.idMesa)
    Scaffold (
        topBar = { TopBar(userViewModel) },
        content = { innerPadding ->
                LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxSize()
                    .padding(innerPadding)){
                    item {
                        Row (horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(fontWeight = FontWeight.Bold, text ="Gasto de los demás comensales")
                        }
                    }
                    tableViewModel.estadoMesa.consumosMesa.forEach { consm ->
                        item {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row (modifier = Modifier.padding(8.dp)){
                                        Icon(imageVector = Icons.Filled.Person,contentDescription = "user")
                                        Text(text = consm.nombre)
                                }
                                Spacer(modifier = Modifier.size(16.dp))
                                Column (){
                                    var tot:Float=0.0f
                                   consm.Pedidos.forEach {ped ->
                                        Consumer(ped)
                                       tot = tot + ped.cantidad * ped.Plato.precio
                                   }
                                   Text(text = "Total  $"+tot.toString(), modifier = Modifier.fillMaxWidth().padding(2.dp),textAlign = TextAlign.End)
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
                        onClick = { userViewModel.iniciarDividirConsumo() },
                        icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "volver") },
                        text = { Text(text = "Enviar invitación a los demás comensales") },
                    )
                }
                    item {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            onClick = { navCont.navigate(route = "requestTicket") },
                            icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "volver") },
                            text = { Text(text = "Volver") },
                        )
                    }
            }
        }
    )
}