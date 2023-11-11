package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging

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
            var canal = NotificationChannel(NOTIFICATION_ID_CHANNEL,"notificaciones FCM share",NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Notificaciones FCM"
            }
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }
}