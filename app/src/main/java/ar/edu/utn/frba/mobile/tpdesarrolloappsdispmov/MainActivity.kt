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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.LoginScreen
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val Context.dataStore by preferencesDataStore(name="USER_DATA")
class MainActivity : ComponentActivity() {
    /*val idCli:Int
    var idMesa:Int=0
    var nomb:String=""
    var userSavData:UserSavedData =UserSavedData("","",0,0)*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val userDataSaved:Flow<UserSavedData> = dataStore.data.map {
            preferences -> mapUser(preferences)  }
        lifecycleScope.launch (Dispatchers.IO){
            userDataSaved.collect{
                withContext(Dispatchers.Main){
                    userSavData = it
                }
            }
        }*/
        //val userData:UserSavedData = mapUser(dataStore.data.first().toPreferences())
        setContent {
            TpDesarrolloAppsDispMovTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
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
                    ///Log.i("MainActivity-userSavedData>",userSavData.toString())
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
        val nomb=preferences[stringPreferencesKey("nombre")]!!
        return UserSavedData("",nomb,idCli,idMesa)
    }
    /*private fun getUserSavedData() =dataStore.data.map {
                preferences ->
            //Log.i("getUserSavedData->",preferences[stringPreferencesKey("nombre")].orEmpty())
            UserSavedData(
                idCliente=preferences[intPreferencesKey("idCliente")]?:0,
                idMesa=preferences[intPreferencesKey("idMesa")]?:0,
                nombre=preferences[stringPreferencesKey("nombre")]!!
            )
        }
    }*/
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TpDesarrolloAppsDispMovTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            // Ir sumando las rutas de las pantallas
            composable(route = "login") { LoginScreen(navController) }
            composable(route = "greeting") { Greeting("Holaa") }
        }
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
        /*if(usuario.idCliente!=0){
            Log.i("usuarioSaved->","SI")
            usuarioViewModel.setUser(usuario.nombre,usuario.idCliente)
            usuarioViewModel.takeTable(usuario.idMesa)
        }else{*/
            if(usuarioViewModel.estadoUser.requestingData){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }else{
                Login(usuarioViewModel)
            }
    }
}
