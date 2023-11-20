package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ItemMenu(titulo:String,icono:ImageVector,navCont:NavController,ruta:String){
    ExtendedFloatingActionButton(
        modifier = Modifier.fillMaxWidth().padding(horizontal=40.dp, vertical = 14.dp),//.padding(14.dp),
        onClick = { navCont.navigate(route=ruta)},
        icon = { Icon(imageVector = icono, contentDescription ="carta",modifier=Modifier.size(40.dp) )},
        text = {
            Text(
                text = titulo,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                style = MaterialTheme.typography.titleLarge
            )
        },
    )
}
