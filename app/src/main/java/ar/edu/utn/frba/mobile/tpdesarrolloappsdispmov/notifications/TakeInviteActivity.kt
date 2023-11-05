package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notifications

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.InitNotifications
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.Starting
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.dataStore
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.ui.theme.TpDesarrolloAppsDispMovTheme

//val Context.dataStore by preferencesDataStore(name="USER_DATA")
class TakeInviteActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofitInst: ReqsService = ReqsService.instance
        val usuarioViewModel by viewModels<UserViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(retrofitInst, dataStore) as T
                }
            }
        })
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1)
        usuarioViewModel.initializating()
        usuarioViewModel.aceptarDividirConsumo()
        finish()
        System.exit(0)
        /*setContent {
            TpDesarrolloAppsDispMovTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Column {
                        Text(text = "Has aceptado dividir la cuenta total de la mesa entre los comensales")
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            onClick = {
                                finish()
                                System.exit(0)
                            },
                            icon = { Icon(Icons.Filled.ArrowBack,  contentDescription ="volver") },
                            text = { Text(text = "ACEPTAR") },
                        )
                    }
                }
            }
        }*/
    }
}