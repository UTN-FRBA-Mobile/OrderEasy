/*package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notifications

//import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ServicioDePedidos
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.dataStore
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.VistaModeloUsuario
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.ui.theme.TpDesarrolloAppsDispMovTheme
import kotlin.system.exitProcess

val Context.dataStore by preferencesDataStore(name="USER_DATA")
class ReqTicketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ReqTicketActivity","INICIALIZANDO")
        val retrofitInst: ServicioDePedidos = ServicioDePedidos.instance
        val usuarioViewModel by viewModels<VistaModeloUsuario>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return VistaModeloUsuario(retrofitInst, dataStore) as T
                }
            }
        })
        setContent {
            TpDesarrolloAppsDispMovTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Column {
                        ElevatedCard (
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            modifier = Modifier.padding(20.dp)
                        )
                        {
                            Text(
                                *//*text = "Se dividirá el total gastado en la mesa de $${intent.extras?.getString("total")} entre los ${intent.extras?.getString("cantidad")} comensales," +
                                    " pagando cada uno $${intent.extras?.getString("pago")} Si alguno de los integrantes de la mesa no acepta la propuesta, entonces se cancelará ésta" +
                                    " forma de pago",*//*
                                text = "Se dividirá el total gastado en la mesa de  entre los  comensales,pagando cada uno Si alguno de los integrante pago",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Justify,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Row (
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            ExtendedFloatingActionButton(
                                modifier = Modifier
                                    .padding(10.dp),
                                onClick = {
                                    usuarioViewModel.initializating()
                                    //usuarioViewModel.aceptarDividirConsumo()
                                    finish()
                                    exitProcess(0)
                                },
                                icon = { Icon(Icons.Filled.Check,  contentDescription ="volver") },
                                text = { Text(text = "SI ACEPTO", style=MaterialTheme.typography.labelSmall) },
                            )
                            ExtendedFloatingActionButton(
                                modifier = Modifier
                                    .padding(10.dp),
                                onClick = {
                                    usuarioViewModel.initializating()
                                    //usuarioViewModel.rechazarDividirConsumo()
                                    finish()
                                    //System.exit(0)
                                },
                                icon = { Icon(Icons.Filled.Close,  contentDescription ="volver") },
                                text = { Text(text = "NO ACEPTO", style=MaterialTheme.typography.labelSmall) },
                            )
                        }
                    }
                }
            }
        }
    }
}*/

//SI:
/*
val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
manager.cancel(1)
usuarioViewModel.initializating()
usuarioViewModel.aceptarDividirConsumo()
finish()
System.exit(0)
*/