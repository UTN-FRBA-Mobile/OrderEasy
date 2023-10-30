package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun CloseTable(navCont: NavController,userViewModel: UserViewModel) {
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier.padding(innerPadding).fillMaxSize()){
                Text(text = "retirarse de la mesa")

                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    onClick = {
                        userViewModel.exitTable()
                        navCont.navigate(route="mainmenu")
                    },
                    icon = { Icon(Icons.Filled.ExitToApp,  contentDescription ="salir")},
                    text = { Text(text = "Retirarse de la mesa") },
                )

                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    onClick = { navCont.navigate(route="mainmenu")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver")},
                    text = { Text(text = "Volver al menu") },
                )
            }
        }
    )
}