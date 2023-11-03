package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel


@Composable
fun ChallengeTicket(navCont: NavController, userViewModel: UserViewModel, tableViewModel: TableViewModel){
    Scaffold (
        topBar = { TopBar(userViewModel) },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize() .padding(innerPadding)){


                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = { navCont.navigate(route="requestTicket")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                    text = { Text(text = "Volver") },
                )
            }
        }
    )
}