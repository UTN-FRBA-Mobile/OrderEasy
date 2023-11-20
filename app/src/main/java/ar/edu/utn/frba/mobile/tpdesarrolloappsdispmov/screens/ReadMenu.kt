package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import coil.compose.AsyncImage

@Composable
fun ReadMenu(navCont: NavController,menu: MenuViewModel,userViewModel: UserViewModel) {
    Scaffold (
        topBar = { TopBar(userViewModel ) },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize().padding(innerPadding)){
                Text(
                    modifier = Modifier .fillMaxWidth(),
                    text = "Platos disponibles"
                )
                if(menu.estadoMenu.loadingMenu){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                        CircularProgressIndicator()
                    }
                }else{
                    LazyColumn(modifier = Modifier.fillMaxWidth()){
                        items(menu.estadoMenu.platos){e ->
                            var cant = menu.estadoMenu.pedidos.find { p -> p.idPlato == e.idPlato }
                            Row (
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Column(
                                    verticalArrangement = Arrangement.Top,
                                    modifier = Modifier.fillMaxWidth(0.6f)//.width(140.dp)
                                ) {
                                    Text(text = e.nombre)
                                    AsyncImage(model = e.imagen, contentDescription = null, modifier = Modifier.height(20.dp))
                                    Text(text = e.precio.toString())
                                }
                                Column (
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row {
                                        SmallFloatingActionButton(onClick = { menu.addItem(e.idPlato) },
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.secondary) {
                                            Icon(Icons.Filled.Add,"small button")
                                        }
                                        SmallFloatingActionButton(onClick = { menu.delItem(e.idPlato) },
                                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                            contentColor = MaterialTheme.colorScheme.secondary) {
                                            Icon(Icons.Filled.Clear,"small button")
                                        }
                                        Text(text = menu.estadoMenu.pedidos.find{p -> (p.idPlato==e.idPlato && p.estado=="selected")}?.cantidad.toString())
                                    }
                                }
                            }
                        }
                    }
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