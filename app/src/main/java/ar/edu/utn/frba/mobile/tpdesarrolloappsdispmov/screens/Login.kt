package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar

@Composable
fun Login (usuarioViewModel:UserViewModel){
    var nombre by remember { mutableStateOf("") }
    Scaffold (
        topBar = { TopBar(userViewModel = usuarioViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .wrapContentSize(),
                    text = "/* FORMULARIO PARA INGRESAR EL NOMBRE */",
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp) ,
                    value = nombre,
                    onValueChange ={nombre=it},
                    label={Text("Ingresa tu nombre")} )
                Button(
                    onClick = {usuarioViewModel.log(nombre)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .wrapContentSize()
                ) {
                    Text(text = "ingresar")
                }
            }
        }
    )
}