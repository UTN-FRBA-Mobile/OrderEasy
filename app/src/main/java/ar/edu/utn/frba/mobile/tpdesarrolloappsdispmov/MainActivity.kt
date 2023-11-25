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
        Log.i("MainActivity0->extras",intent.extras?.getString("total").toString())
        Log.i("MainActivity1->extras",intent.extras?.getString("action").toString())

        val bundle = intent.extras
        if (bundle != null) {
            for (key in bundle.keySet()) {
                Log.i("param(${key})--->",bundle[key].toString())
            }
        }
        setContent {
            TpDesarrolloAppsDispMovTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
                    //Log.i("MainActivity->extras2",intent.extras?.getString("inviteDivide").toString())

                    if(intent.extras?.getString("action")=="desafio"){
                        val accion = intent.extras?.getString("action")?:""
                        val idRival = intent.extras?.getInt("idRival")?:0
                        val nombRival = intent.extras?.getString("nombRival")?:""
                        Log.i("MainActivity->defy",accion)
                        Log.i("MainActivity->defy",idRival.toString())
                        Log.i("MainActivity->defy",nombRival)
                        usuarioViewModel.setRival(idRival,nombRival,"desafiado")
                    }
                    if(intent.extras?.getString("action")=="jugar"){
                        val accion = intent.extras?.getString("action")?:""
                        val idPartida = intent.extras?.getInt("idPartida")?:0
                        Log.i("MainActivity->defy",accion)
                        usuarioViewModel.setGame(idPartida,"jugando")
                    }
                    if(intent.extras?.getString("total")!=null){
                        Log.i("MainActivity->pago",intent.extras?.getString("pago").toString())
                        Log.i("MainActivity->total",intent.extras?.getString("total").toString())
                        Log.i("MainActivity->cantidad",intent.extras?.getString("cantidad").toString())
                        /*Notificacion(
                            userViewModel = usuarioViewModel,
                            navController = navController,
                            total = intent.extras?.getString("total")!!,
                            individual = intent.extras?.getString("pago")!!,
                            cantidad = intent.extras?.getString("cantidad")!!
                        )*/
                        val total = intent.extras?.getString("total")?:""
                        val cant = intent.extras?.getString("cantidad")?:""
                        val pago = intent.extras?.getString("pago")?:""
                        getIntent().removeExtra("total")
                        getIntent().removeExtra("cantidad")
                        getIntent().removeExtra("pago")
                        Log.i("MainActivity2->pago",intent.extras?.getString("pago").toString())
                        Log.i("MainActivity2->total",intent.extras?.getString("total").toString())
                        Log.i("MainActivity2->cantidad",intent.extras?.getString("cantidad").toString())
                        usuarioViewModel.setInviteDivide(total,cant,pago)
                    }//else {
                        Starting(
                            tabStateViewModel,
                            usuarioViewModel,
                            menuStateViewModel,
                            navController
                        )
                   // }
                }
            }
        }
    }
}

@Composable
fun Starting(tableViewModel: TableViewModel,usuarioViewModel: UserViewModel,menuStateViewModel: MenuViewModel,navController: NavHostController) {
    Log.i("MAINACTIVITY--->","Starting")
    if(usuarioViewModel.estadoUser.isLogged){
        MainNavigation(tableViewModel,menuStateViewModel,usuarioViewModel, navController)
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
    Firebase.messaging.token.addOnCompleteListener {
        if(!it.isSuccessful){
            println("error en obtencion de token")
            return@addOnCompleteListener
        }
        val token = it.result
        usuarioViewModel.setIdDevice(token)
    }
}
