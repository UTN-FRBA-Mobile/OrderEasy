package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun OrdersState(navCont: NavController, viewmodelo: TableViewModel,userViewModel: UserViewModel) {
    //val viewmodelo =EstadoPedidos(RetrofitHelper.getInstance())
    //val viewmodelo = remember {TableViewModel(ReqsService.instance)}
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Lista de estados de los pedidos de la mesa"
                )
                /*if(viewmodelo.state.requestingData){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                        CircularProgressIndicator()
                    }
                }else{*/
                    LazyColumn(modifier = Modifier.fillMaxWidth()){
                        viewmodelo.estadoMesa.pedidosMesa.forEach { cli->
                            item {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Row (){
                                        Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "user")
                                        Text(text = cli.nombre)
                                    }
                                    Spacer(modifier = Modifier.size(8.dp))
                                    cli.Pedidos.forEach {  ped ->
                                            Row (horizontalArrangement = Arrangement.SpaceEvenly
                                            ){
                                                Text(text = ped.Plato.nombre)
                                                Text(text = ped.cantidad.toString())
                                                Text(text = (ped.cantidad*ped.Plato.precio).toString())
                                            }
                                        }
                                }
                            }
                        }
                    }
                //}
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = { navCont.navigate(route="mainmenu")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                    text = { Text(text = "Volver al menu") },
                )
            }
        }
    )
}