package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

//import android.util.Log
import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pedidosApi.ServicioDePedidos
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navegacion.NavegacionPrincipal
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.Ingresar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.ui.theme.TpDesarrolloAppsDispMovTheme
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

val Context.dataStore by preferencesDataStore(name="USER_DATA")
class MainActivity : ComponentActivity() {
    private val REQUEST_CODE_POST_NOTIFICATIONS = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val bundle = intent.extras
        if (bundle != null) {
            for (key in bundle.keySet()) {
                Log.i("param(${key})--->",bundle[key].toString())
            }
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_GRANTED -> Log.i(
                    "Permiso de notificaciones->",
                    "concedida"
                )

                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.notif_perm_title))
                        .setMessage(getString(R.string.notif_perm_txt))
                        .setPositiveButton(getString(R.string.btn_ok)) { dialog, which ->
                            requestPermissions(
                                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                                REQUEST_CODE_POST_NOTIFICATIONS
                            )
                        }
                        .setNegativeButton(getString(R.string.btn_cancel)) { dialog, which ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
                else -> {
                    requestPermissions(
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        REQUEST_CODE_POST_NOTIFICATIONS
                    )
                }
            }
        }
        setContent {
            TpDesarrolloAppsDispMovTheme {
                val navController= rememberNavController()
                val retrofitInst:ServicioDePedidos = ServicioDePedidos.instance
                val usuarioViewModel by viewModels <VistaModeloUsuario>(factoryProducer = {
                    object : ViewModelProvider.Factory{
                        override fun <T:ViewModel> create (modelClass: Class<T>): T{
                            return VistaModeloUsuario(retrofitInst,dataStore) as T
                        }
                    }
                })
                val tabStateViewModel by viewModels <VistaModeloMesa>(factoryProducer = {
                    object : ViewModelProvider.Factory{
                        override fun <T: ViewModel> create (modelClass: Class<T>): T{
                            return VistaModeloMesa(retrofitInst) as T
                        }
                    }
                })
                val menuStateViewModel by viewModels <VistaModeloMenu>(factoryProducer = {
                    object : ViewModelProvider.Factory{
                        override fun <T: ViewModel> create (modelClass: Class<T>): T{
                            return VistaModeloMenu(retrofitInst) as T
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
                        }else{
                            usuarioViewModel.setInviteDivide("","","")
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
fun Starting(tableViewModel: VistaModeloMesa, usuarioViewModel: VistaModeloUsuario, menuStateViewModel: VistaModeloMenu, navController: NavHostController) {
    if(usuarioViewModel.estadoUser.initializatingApp || usuarioViewModel.estadoUser.requestingData){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }else if(usuarioViewModel.estadoUser.isLogged){
        if(usuarioViewModel.estadoUser.idDevice!=""){
            NavegacionPrincipal(tableViewModel,menuStateViewModel,usuarioViewModel, navController)
        }else{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }else{
        Ingresar(usuarioViewModel)
    }
}
