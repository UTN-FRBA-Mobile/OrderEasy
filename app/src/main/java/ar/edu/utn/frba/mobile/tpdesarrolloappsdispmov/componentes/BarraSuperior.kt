package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@Composable
fun BarraSuperior (userViewModel: VistaModeloUsuario){
   TopAppBar(
       modifier = Modifier.height(40.dp),
        colors = TopAppBarDefaults.largeTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
        Row (
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(Icons.Filled.AccountCircle, contentDescription = "usuario", modifier = Modifier.size(20.dp))
                    Text(
                        text = userViewModel.estadoUser.nombre,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Box {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = R.drawable.baseline_restaurant_24),"resto",modifier = Modifier.size(24.dp))
                    Text(text = "OrderEasy",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    })
}