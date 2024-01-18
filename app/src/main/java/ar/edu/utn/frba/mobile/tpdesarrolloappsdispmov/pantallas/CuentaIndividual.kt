package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import java.util.Locale

@Composable
fun CuentaIndividual(navCont:NavController, vistaModeloUsuario: VistaModeloUsuario){
    var total:Float=0.0f
    Scaffold (
        topBar = { BarraSuperior(vistaModeloUsuario) },
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    text = stringResource(id = R.string.singleticket_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                if(vistaModeloUsuario.estadoUser.loadingConsumo){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }else {

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        items(vistaModeloUsuario.estadoUser.consumos) { e ->
                            total = total+ e.Plato.precio * e.cantidad
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(color = Color(206, 222, 213, 255))
                                    .border(
                                        0.dp,
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(0.dp)
                                    )
                            ) {
                                Icon(imageVector =Icons.Filled.Done , contentDescription = "item", modifier = Modifier.size(14.dp))
                                Text(
                                    text = e.Plato.nombre,
                                    modifier = Modifier.weight(4f),
                                    //lineHeight=1.sp,
                                    style= MaterialTheme.typography.displaySmall)
                                Text(text = "("+e.cantidad.toString()+"x $"+"%,.1f".format(Locale.GERMAN,e.Plato.precio)+")",
                                    style= MaterialTheme.typography.labelSmall,
                                    modifier=Modifier.weight(4f))
                                Text(text = "$"+"%,.1f".format(Locale.GERMAN,e.Plato.precio * e.cantidad),
                                    style= MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.weight(2f))
                            }
                        }
                        item{
                            Row {
                                Text(text = stringResource(id = R.string.ticket_total)+"%,.1f".format(Locale.GERMAN,total),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp),
                                    style= MaterialTheme.typography.displayLarge,
                                    textAlign = TextAlign.End)
                            }
                        }
                    }
                }
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = {
                        vistaModeloUsuario.pagar()
                        navCont.navigate(route="mainmenu")
                    },
                    icon = { Icon(painter = painterResource(id = R.drawable.baseline_monetization_on_24),
                        contentDescription ="volver",
                        modifier = Modifier.size(30.dp)) },
                    text = { Text(
                            text = stringResource(id = R.string.singleticket_request),
                            style= MaterialTheme.typography.displayMedium
                        ) }
                )

                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    onClick = { navCont.navigate(route="requestTicket")},
                    icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                    text = { Text(text = stringResource(id = R.string.btn_back),style= MaterialTheme.typography.displayMedium) }
                )
            }
        }
    )
}