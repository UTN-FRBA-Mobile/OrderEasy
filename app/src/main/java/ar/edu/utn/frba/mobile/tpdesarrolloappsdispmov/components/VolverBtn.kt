package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun VolverBtn(navCont: NavController){
    ExtendedFloatingActionButton(
        modifier = Modifier.fillMaxWidth().wrapContentSize().padding(vertical = 8.dp),
        onClick = { navCont.navigate(route="mainmenu")},
        icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
        text = { Text(
            text = "Volver al menu",
            fontWeight = FontWeight.W600,
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal
            )
        ) },
    )
}