package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MainMenu(navCont:NavHostController){
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "BARRA SUPERIOR DE LA APP") })
        },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .wrapContentSize(),
                    text = "/* MENU PRINCIPAL */",
                )
                Button(
                    onClick = {navCont.navigate(route="login")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .wrapContentSize()
                ) {
                    Text(text = "ir al login")
                }
            }
        }
    )
}