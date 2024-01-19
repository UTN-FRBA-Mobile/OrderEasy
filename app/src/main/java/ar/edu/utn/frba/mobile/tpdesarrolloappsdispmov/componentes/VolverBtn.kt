package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@Composable
fun VolverBtn(navCont: NavController){
    ExtendedFloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(vertical = 8.dp),
        onClick = { navCont.navigate(route="menuprincipal")},
        icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
        text = { Text(
            text = stringResource(id = R.string.btn_back),
            style = MaterialTheme.typography.titleSmall
        ) },
    )
}