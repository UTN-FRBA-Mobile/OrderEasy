package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navigation.MainNavigation
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Login
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.ui.theme.TpDesarrolloAppsDispMovTheme
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

val Context.dataStore by preferencesDataStore(name="USER_DATA")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TpDesarrolloAppsDispMovTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val retrofitInst:ReqsService = ReqsService.instance
                    val usuarioViewModel by viewModels <UserViewModel>(factoryProducer = {
                        object : ViewModelProvider.Factory{
                            override fun <T:ViewModel> create (modelClass: Class<T>): T{
                                return UserViewModel(retrofitInst) as T
                            }
                        }
                    })
                    val tabStateViewModel by viewModels <TableViewModel>(factoryProducer = {
                        object : ViewModelProvider.Factory{
                            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                                return TableViewModel(retrofitInst) as T
                            }
                        }
                    })
                    val menuStateViewModel by viewModels <MenuViewModel>(factoryProducer = {
                        object : ViewModelProvider.Factory{
                            override fun <T: ViewModel> create (modelClass: Class<T>): T{
                                return MenuViewModel(retrofitInst) as T
                            }
                        }
                    })
                    Starting(tabStateViewModel,usuarioViewModel,menuStateViewModel)
                }
            }
        }
    }
    private suspend fun almacenar(){
        dataStore
    }
}

@Composable
fun Starting(tableViewModel: TableViewModel,usuarioViewModel: UserViewModel,menuStateViewModel: MenuViewModel) {
    //val usuarioViewModel = UserViewModel(ReqsService.instance)
    Firebase.messaging.token.addOnCompleteListener {
        if(!it.isSuccessful){
            println("error en obtencion de token")
            return@addOnCompleteListener
        }
        val token = it.result
        //println("token->$token")
        Log.i("MainActivity-Token--->",token)
        usuarioViewModel.setIdDevice(token)
    }

    if(usuarioViewModel.estadoUser.isLogged){
        MainNavigation(tableViewModel,menuStateViewModel)
    }else{
        if(usuarioViewModel.estadoUser.requestingData){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }else{
            Login(usuarioViewModel)
        }
    }
}
