package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun CallMozo(navCont: NavController,userViewModel: UserViewModel) {
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .wrapContentSize(),
                    text = "/* SE CARGA ALGUN BOTON/SIMIL PARA PEDIR LA ATENCIÓN DEL MOZO */"
                )
                Button(
                    onClick = {navCont.navigate(route="mainmenu")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .wrapContentSize()
                ) {
                    Text(text = "volver al menu")
                }
            }
        }
    )
}