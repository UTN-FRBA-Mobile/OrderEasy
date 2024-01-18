package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

//import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import java.util.Locale


@Composable
fun CuentaInvitados(navCont: NavController, userViewModel: VistaModeloUsuario, vistaModeloMesa: VistaModeloMesa){
    val toast = Toast.makeText(LocalContext.current, stringResource(id = R.string.error_api), Toast.LENGTH_SHORT)
    Scaffold (
        topBar = { BarraSuperior(userViewModel) },
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.inviteticket_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )

                if(vistaModeloMesa.estadoMesa.pidiendoDatos){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween//.spacedBy(4.dp)
                    ){

                        item {
                            FilterChip(
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(206, 222, 213, 255)),
                                selected = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                onClick = {},
                                label= {
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                                            Icon(painter = painterResource(id = R.drawable.baseline_sentiment_very_satisfied_24),
                                                contentDescription = "userOut",
                                                modifier = Modifier.size(28.dp))
                                            Text(text ="Yo",
                                                style = MaterialTheme.typography.displaySmall,
                                                textAlign= TextAlign.Center)
                                        }
                                        Row (
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            val indice:Int =vistaModeloMesa.estadoMesa.invitados.indexOfFirst{ e -> e.idCliente == userViewModel.estadoUsuario.idCliente}
                                            val yo = if(indice !=-1) vistaModeloMesa.estadoMesa.invitados[indice] else null
                                            if (yo != null) {
                                                Text(text = stringResource(id = R.string.inviteticket_consumido)+"%,.1f".format(Locale.GERMAN,yo.total),
                                                    style=MaterialTheme.typography.labelSmall)
                                            }
                                        }
                                    }
                                },
                                leadingIcon = {
                                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                                        Icon(painter = painterResource(id = R.drawable.baseline_check_box_24),
                                            contentDescription = "userIn",
                                            modifier = Modifier.size(16.dp) )
                                    }
                                }
                            )
                        }
                        items(vistaModeloMesa.estadoMesa.invitados) { cons ->
                            if (cons.idCliente != userViewModel.estadoUsuario.idCliente){
                                FilterChip(
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(206,222,213,255),
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        borderColor = Color(206, 222, 213, 255)
                                    ),
                                    selected = cons.seleccionado,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    onClick = {vistaModeloMesa.seleccionarInvitados(cons.idCliente)},
                                    label = {
                                        Row (
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(10.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                if(cons.seleccionado) {
                                                    Icon(painter = painterResource(id = R.drawable.baseline_sentiment_very_satisfied_24),
                                                        contentDescription = "userIn",
                                                        modifier = Modifier.size(28.dp) )
                                                }else{
                                                    Icon(painter = painterResource(id = R.drawable.baseline_sentiment_dissatisfied_24),
                                                        contentDescription = "userOut",
                                                        modifier = Modifier.size(28.dp) )
                                                }
                                                Text(text = cons.nombre,
                                                    style = MaterialTheme.typography.displaySmall,
                                                    textAlign= TextAlign.Center)
                                            }
                                            Row (
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                Text(text = stringResource(id = R.string.inviteticket_consumido)+"%,.1f".format(Locale.GERMAN,cons.total),
                                                    style=MaterialTheme.typography.labelSmall)
                                            }
                                        }
                                    },
                                    leadingIcon = {
                                        if (cons.seleccionado) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_check_box_24),
                                                contentDescription = "userIn",
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }else{
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_check_box_outline_blank_24),
                                                contentDescription = "userIn",
                                                modifier = Modifier.size(16.dp))
                                        }
                                    }
                                )
                            }
                        }
                        item {Text(
                            text = stringResource(id = R.string.inviteticket_total)+"%,.1f".format(Locale.GERMAN,vistaModeloMesa.estadoMesa.invitados.filter { e ->e.seleccionado }.fold(0.0f ) { acc, i -> acc + i.total}),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )}
                        item{
                            ExtendedFloatingActionButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                onClick = {
                                    vistaModeloMesa.pagarInvitado(userViewModel.estadoUsuario.idCliente)
                                    navCont.navigate(route="mainmenu")},
                                text = { Text(text = stringResource(id = R.string.singleticket_request),
                                    style = MaterialTheme.typography.titleSmall) },
                                icon = { Icon(painter = painterResource(id = R.drawable.baseline_monetization_on_24),
                                    contentDescription ="volver",
                                    modifier = Modifier.size(30.dp)) }
                            )
                            ExtendedFloatingActionButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                onClick = { navCont.navigate(route="requestTicket")},
                                icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                                text = { Text(text = "Volver",
                                    style = MaterialTheme.typography.titleSmall) },
                            )
                        }
                    }
                }
                if (vistaModeloMesa.estadoMesa.resultPedidoApi == 2){
                    toast.setGravity(Gravity.TOP,0,0)
                    toast.show()
                    vistaModeloMesa.desactivarErrorPedidoApi()
                }
            }
        }
    )
}