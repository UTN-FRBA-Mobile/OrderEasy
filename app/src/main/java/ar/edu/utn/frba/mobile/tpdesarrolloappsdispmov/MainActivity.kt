package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.content.Context
import android.os.Bundle
//import android.util.Log
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
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
        /*val bundle = intent.extras
        if (bundle != null) {
            for (key in bundle.keySet()) {
                Log.i("param(${key})--->",bundle[key].toString())
            }
        }*/
        setContent {
            TpDesarrolloAppsDispMovTheme {
                val navController= rememberNavController()
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(usuarioViewModel.estadoUser.initializatingApp){
                        Firebase.messaging.token.addOnCompleteListener {
                            if(!it.isSuccessful){
                                println("error en obtencion de token")
                                return@addOnCompleteListener
                            }
                            val token = it.result
                            usuarioViewModel.setIdDevice(token)
                        }
                        /*if(intent.extras?.getString("action")=="desafio"){
                            Log.i("MAINACTIVITY--->","DESAFIO")
                            val accion = intent.extras?.getString("action")?:""
                            val idRival = intent.extras?.getInt("idRival")?:0
                            val nombRival = intent.extras?.getString("nombRival")?:""
                            usuarioViewModel.setRival(idRival,nombRival,"desafiado")
                        }else if(intent.extras?.getString("action")=="jugar"){
                            Log.i("MAINACTIVITY--->","JUGAR")
                            val accion = intent.extras?.getString("action")?:""
                            val idPartida = intent.extras?.getInt("idPartida")?:0
                            Log.i("MainActivity->defy",accion)
                        usuarioViewModel.setGame(idPartida,"jugando")
                    }*/
                        if(intent.extras?.getString("action")=="share"){
                            usuarioViewModel.setInviteDivide(
                                intent.extras?.getString("total")?:"",
                                intent.extras?.getString("cantidad")?:"",
                                intent.extras?.getString("pago")?:""
                            )
                        }
                        usuarioViewModel.initializating()
                        menuStateViewModel.getMenu()
                    }
                    Starting(
                        tabStateViewModel,
                        usuarioViewModel,
                        menuStateViewModel,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun Starting(tableViewModel: TableViewModel,usuarioViewModel: UserViewModel,menuStateViewModel: MenuViewModel,navController: NavHostController) {
    if(usuarioViewModel.estadoUser.initializatingApp){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }else if(usuarioViewModel.estadoUser.isLogged){
        MainNavigation(tableViewModel,menuStateViewModel,usuarioViewModel, navController)
    }else{
        Login(usuarioViewModel)
    }
}
