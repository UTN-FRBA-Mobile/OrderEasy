package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.ItemConsumidoData
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun IndividualTicket(navCont:NavController,userViewModel: UserViewModel){
    //var nombre by remember { mutableStateOf("") }
    //userViewModel.getConsumo()
    var total:Float=0.0f
    Scaffold (
        topBar = { TopBar(userViewModel) },
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    items(userViewModel.estadoUser.consumos) { e ->
                        total = total+ e.Plato.precio * e.cantidad
                        Log.i("TOTAL->",total.toString())
                        Row {
                            Icon(imageVector =Icons.Filled.Done , contentDescription = "item" , modifier = Modifier.weight(1f))
                            Text(text = e.Plato.nombre, modifier = Modifier.weight(5f))
                            Text(text="( X "+e.cantidad.toString()+")", modifier = Modifier.weight(2f))
                            Text(text = e.Plato.precio.toString(),modifier=Modifier.weight(2f))
                            Text(text = (e.Plato.precio * e.cantidad).toString(), modifier = Modifier.weight(2f))
                        }
                    }
                    item{
                        Row {
                            Text(text = "Total "+total.toString())
                        }
                    }
                }
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = {
                        userViewModel.pagar()
                        navCont.navigate(route="mainmenu")
                    },
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                    text = { Text(text = "Pagar/pediar la cuenta") }
                )

                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = { navCont.navigate(route="requestTicket")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                    text = { Text(text = "Volver") }
                )
            }
        }
    )
}