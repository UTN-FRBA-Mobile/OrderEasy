package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar

@Composable
fun Login (usuarioViewModel:UserViewModel){
    var nombre by remember { mutableStateOf("") }
    Scaffold (
        topBar = { TopBar(userViewModel = usuarioViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Elige un nombre de usuario para identificarte en la mesa",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.titleLarge
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp) ,
                    value = nombre,
                    onValueChange ={nombre=it},
                    label={Text("Ingresa tu nombre")} )
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth().wrapContentSize().padding(vertical = 8.dp),
                    onClick = {usuarioViewModel.log(nombre)},
                    icon = { Icon(Icons.Sharp.ArrowForward,  contentDescription ="volver") },
                    text = { Text(text = "Ingresar",style = MaterialTheme.typography.titleSmall) },
                )
            }
        }
    )
}