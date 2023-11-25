package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CloseTable(navCont: NavController) {
    Scaffold (
        topBar = {
            OderEasyTopAppBar(navCont)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { /* Lógica para cerrar la mesa */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp) // Altura del botón
                        .background(color = Color.Red, shape = RoundedCornerShape(16.dp)), // Color de fondo del botón
                    shape = RoundedCornerShape(16.dp), // Bordes redondeados
                ) {
                    Text(
                        text = "Cerrar Mesa",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los botones

                Button(
                    onClick = { navCont.navigate(route = "mainmenu") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(16.dp)), // Color de fondo del botón
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Volver al Menú",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    )
}