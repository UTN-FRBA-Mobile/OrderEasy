package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel

@Composable
fun ReadMenu(navCont: NavController,menu: MenuViewModel) {
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "BARRA SUPERIOR DE LA APP") })
        },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize()){
                Text(
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize(),
                    text = "/* SE CARGA UNA LISTA DE LOS PLATOS DISPONIBLES */"
                )
                if(menu.estadoMenu.loadingMenu){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                        CircularProgressIndicator()
                    }
                }else{
                    LazyColumn(modifier = Modifier.fillMaxWidth()){
                        item {
                            Row (horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(fontWeight = FontWeight.Bold, text ="Nombre")
                                Text(fontWeight = FontWeight.Bold,text = "Cantidad")
                                Text(fontWeight = FontWeight.Bold,text = "Estado")
                            }
                        }
                        items(menu.estadoMenu.platos){e ->
                            Row (horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Spacer(modifier = Modifier.height(Dp(18F)))
                                Text(text = e.nombre)
                                Text(text = e.descripcion)
                                Text(text = e.precio.toString())
                            }
                        }
                    }
                }
                Button(
                    onClick = {navCont.navigate(route="mainmenu")},
                    modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize()
                ) {
                    Text(text = "volver al menu")
                }
            }
        }
    )
}