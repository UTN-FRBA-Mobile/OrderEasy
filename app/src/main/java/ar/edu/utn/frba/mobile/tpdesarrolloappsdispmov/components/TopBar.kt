package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@Composable
fun TopBar (userViewModel: UserViewModel){
   TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(Icons.Filled.AccountCircle, contentDescription = "usuario")
                    Text(text = userViewModel.estadoUser.nombre)
                }
            }
            Box {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = R.drawable.baseline_restaurant_24),"resto")
                    Text(text = "OrderEasy")
                }
            }
        }
    })
}