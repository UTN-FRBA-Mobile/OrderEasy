package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun CallMozo(navCont: NavController) {
    Scaffold (
        topBar = {
            OderEasyTopAppBar()
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
                    onClick = { /* TODO: Lógica para llamar al mozo */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(text = "Llamar al Mozo")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navCont.navigate(route = "mainmenu") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(text = "Volver al Menú")
                }

            }
        }
    )
}