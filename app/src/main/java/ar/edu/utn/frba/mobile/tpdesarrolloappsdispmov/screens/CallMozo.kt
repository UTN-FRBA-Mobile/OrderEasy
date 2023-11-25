package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.VolverBtn
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun CallMozo(navCont: NavController,userViewModel: UserViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                ElevatedCard (
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.padding(20.dp),
                )
                {
                    Text(
                        text = "Puedes solicitar que un mozo se acerque a la mesa si no encuentras" +
                                " ninguna opción de la aplicación que te permita realizar la acción que deseas",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.displayMedium
                    )

                    ExtendedFloatingActionButton(
                        modifier = Modifier.fillMaxWidth().wrapContentSize().padding(vertical = 8.dp),
                        onClick = {showDialog = true},
                        icon = { Icon(Icons.Filled.Notifications,  contentDescription ="call") },
                        text = { Text(
                            text = "Llamar",
                            style = MaterialTheme.typography.titleSmall
                        ) },
                    )
                    VolverBtn(navCont)
                    if (showDialog) {
                        AlertDialog(
                            containerColor = Color(251, 201, 143, 255),
                            icon = { Icon(Icons.Default.Info, "call-mozo") },
                            title = { Text(text = "Llamar al mozo") },
                            text = { Text(text = "En un momento un mozo se acercará a la mesa") },
                            onDismissRequest = { /*TODO*/ },
                            confirmButton = {
                                TextButton(
                                    colors = ButtonDefaults.buttonColors (
                                        containerColor = MaterialTheme.colorScheme.inverseSurface,
                                    ),
                                    onClick = { showDialog = false }
                                ) {
                                    Text(text = "ok")
                                }
                            })
                    }
                }
            }
        }
    )
}