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
                val controladorDeNavegacion= rememberNavController()
                val retrofitInst:ServicioDePedidos = ServicioDePedidos.instance
                val vistaModeloUsuario by viewModels <VistaModeloUsuario>(factoryProducer = {
                    object : ViewModelProvider.Factory{
                        override fun <T:ViewModel> create (modelClass: Class<T>): T{
                            return VistaModeloUsuario(retrofitInst,dataStore) as T
                        }
                    }
                })
                val vistaModeloMesa by viewModels <VistaModeloMesa>(factoryProducer = {
                    object : ViewModelProvider.Factory{
                        override fun <T: ViewModel> create (modelClass: Class<T>): T{
                            return VistaModeloMesa(retrofitInst) as T
                        }
                    }
                })
                val vistaModeloMenu by viewModels <VistaModeloMenu>(factoryProducer = {
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
                    if(vistaModeloUsuario.estadoUsuario.initializatingApp){
                        Firebase.messaging.token.addOnCompleteListener {
                            if(!it.isSuccessful){
                                println("error en obtencion de token")
                                return@addOnCompleteListener
                            }
                            val token = it.result
                            vistaModeloUsuario.setearDispositivoId(token)
                        }
                        if(intent.extras?.getString("action")=="share"){
                            vistaModeloUsuario.setearInvitacionDividir(
                                intent.extras?.getString("total")?:"",
                                intent.extras?.getString("cantidad")?:"",
                                intent.extras?.getString("pago")?:""
                            )
                        }else{
                            vistaModeloUsuario.setearInvitacionDividir("","","")
                        }
                        vistaModeloUsuario.inicializar()
                        vistaModeloMenu.obtenerMenu()
                    }
                    Starting(
                        vistaModeloMesa,
                        vistaModeloUsuario,
                        vistaModeloMenu,
                        controladorDeNavegacion
                    )
                }
            }
        }
    }
}

@Composable
fun Starting(tableViewModel: VistaModeloMesa, usuarioViewModel: VistaModeloUsuario, menuStateViewModel: VistaModeloMenu, navController: NavHostController) {
    if(usuarioViewModel.estadoUsuario.initializatingApp || usuarioViewModel.estadoUsuario.pidiendoDatos){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }else if(usuarioViewModel.estadoUsuario.estaIngresado){
        if(usuarioViewModel.estadoUsuario.idDispositivo!=""){
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
