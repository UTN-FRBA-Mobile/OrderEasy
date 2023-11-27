package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun VolverBtn(navCont: NavController){
    ExtendedFloatingActionButton(
        modifier = Modifier.fillMaxWidth().wrapContentSize().padding(vertical = 8.dp),
        onClick = { navCont.navigate(route="mainmenu")},
        icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
        text = { Text(
            text = "Volver al menu",
            style = MaterialTheme.typography.titleSmall
        ) },
    )
}