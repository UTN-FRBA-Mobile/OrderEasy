package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notifications

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.dataStore
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

class RefuseInviteActivity: ComponentActivity() {
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
        usuarioViewModel.initializating()
        usuarioViewModel.rechazarDividirConsumo()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1)
    }
}