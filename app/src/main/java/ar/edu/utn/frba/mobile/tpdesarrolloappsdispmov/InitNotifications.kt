package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class InitNotifications : Application() {
    companion object{
        const val NOTIFICATION_ID_CHANNEL="notificaciones_fcm"
    }
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var importanciaCanal = NotificationManager.IMPORTANCE_DEFAULT
            var canal = NotificationChannel(NOTIFICATION_ID_CHANNEL,"notificaciones FCM",NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            canal.description = "Notificaciones recibidas de FCM"
            manager.createNotificationChannel(canal)
        }
    }
}