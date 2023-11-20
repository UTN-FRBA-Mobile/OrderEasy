package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class InitNotifications : Application() {
    companion object{
        const val NOTIFICATION_ID_CHANNEL="id_canal_notificacion_fcm"
    }
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var canal = NotificationChannel(
                NOTIFICATION_ID_CHANNEL,
                "notificaciones FCM share",
                NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Notificaciones FCM"
            }
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }
}