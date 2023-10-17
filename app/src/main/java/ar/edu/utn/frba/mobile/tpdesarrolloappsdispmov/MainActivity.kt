package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.os.Bundle
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navigation.MainNavigation
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Login
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.ui.theme.TpDesarrolloAppsDispMovTheme

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
}

@Composable
fun Starting(tableViewModel: TableViewModel,usuarioViewModel: UserViewModel,menuStateViewModel: MenuViewModel) {
    //val usuarioViewModel = UserViewModel(ReqsService.instance)
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