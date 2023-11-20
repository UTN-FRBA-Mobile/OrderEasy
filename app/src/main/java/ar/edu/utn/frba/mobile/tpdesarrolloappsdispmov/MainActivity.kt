package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navigation.MainNavigation
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Login
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserSavedData
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
                                return UserViewModel(retrofitInst,dataStore) as T
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
                    if(usuarioViewModel.estadoUser.initializatingApp){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                        Log.i("MainActivity--->","llendo a inicializar")
                        usuarioViewModel.initializating()
                    }else {
                        Starting(
                            tabStateViewModel,
                            usuarioViewModel,
                            menuStateViewModel,
                        )
                    }
                }
            }
        }
    }
    private fun mapUser(preferences: Preferences):UserSavedData {
        val idCli = preferences[intPreferencesKey("idCliente")]?:0
        val idMesa = preferences[intPreferencesKey("idMesa")]?:0
        val nomb = preferences[stringPreferencesKey("nombre")]!!
        return UserSavedData("",nomb,idCli,idMesa)
    }
}

@Composable
fun Starting(tableViewModel: TableViewModel,usuarioViewModel: UserViewModel,menuStateViewModel: MenuViewModel) {
    Firebase.messaging.token.addOnCompleteListener {
        if(!it.isSuccessful){
            println("error en obtencion de token")
            return@addOnCompleteListener
        }
        val token = it.result
        Log.i("MainActivity-Token--->",token)
        usuarioViewModel.setIdDevice(token)
    }

    if(usuarioViewModel.estadoUser.isLogged){
        Log.i("usuarioLogged->","SI")
        MainNavigation(tableViewModel,menuStateViewModel,usuarioViewModel)
    }else{
        Log.i("usuarioLogged->","NO")
            if(usuarioViewModel.estadoUser.requestingData){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else{
                Login(usuarioViewModel)
            }
    }
}
