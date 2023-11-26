package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

//import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun MakeOrder (navCont: NavController, menu: MenuViewModel,userViewModel: UserViewModel) {
    var total:Float = remember { 0.0f }
    total = menu.getTotalCartPrice()

    Scaffold (
    topBar = { TopBar(userViewModel)},
    content = { innerPadding ->
        Column (modifier = Modifier.fillMaxSize().padding(innerPadding)){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Pedidos"
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()){
                items(menu.estadoMenu.pedidos){p ->
                    //var cant = menu.estadoMenu.pedidos.find { p -> p.idPlato == e.idPlato }
                    if(p.estado == "selected"){
                        var e = menu.estadoMenu.platos.find { m -> m.idPlato == p.idPlato }
                        Row (
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(text = e?.nombre.toString())
                            Text(text = e?.precio.toString())
                            Text(text = (e?.precio?.times(p.cantidad)).toString())
                        }
                    }
                }
            }
            Text("TOTAL")
            Text(text =total.toString() )
            Button(
                onClick = {
                    menu.orderItem(userViewModel.estadoUser.idMesa,userViewModel.estadoUser.idCliente)
                    navCont.navigate(route="mainmenu")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .wrapContentSize()
            ) {
                Text(text = "ORDENAR")
            }
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