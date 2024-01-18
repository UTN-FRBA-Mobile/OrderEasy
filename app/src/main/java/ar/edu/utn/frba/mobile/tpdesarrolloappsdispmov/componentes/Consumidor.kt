package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.PedidoData
import java.util.Locale

@Composable
fun Consumidor(ped:PedidoData){
    Row (
        //horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        verticalAlignment = Alignment.Top,
    ){
        Icon(painter = painterResource(id = R.drawable.baseline_restaurant_24),contentDescription = "resto",
            modifier = Modifier.weight(1f))
        Text(text = ped.Plato.nombre+" ($"+"%,.1f".format(Locale.GERMAN,ped.Plato.precio)+" x"+ped.cantidad.toString()+")",
            modifier = Modifier.weight(5f),
            style = MaterialTheme.typography.displaySmall)
        Text(text = "$"+"%,.1f".format(Locale.GERMAN,ped.cantidad * ped.Plato.precio),
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelSmall)
    }
}