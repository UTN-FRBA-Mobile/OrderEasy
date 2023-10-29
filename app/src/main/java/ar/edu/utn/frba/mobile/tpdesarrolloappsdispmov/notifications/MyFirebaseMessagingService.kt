package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.InitNotifications
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //println()
        /*Log.i("NOTIFICACION----->",message.data)
        Log.i("NOTIFICACION----->",message.data)
        Log.i("NOTIFICACION----->",message.data)*/
        showNotification(message)
    }
    private fun showNotification(msj:RemoteMessage){
        /*val intentLanzado = Intent(applicationContext,MainActivity::class.java)
        val resultadoIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intentLanzado)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }*/

        val notification = NotificationCompat.Builder(this,InitNotifications.NOTIFICATION_ID_CHANNEL)
            .setContentTitle(msj.notification?.title)
            .setContentText(msj.notification?.body)
            .setSmallIcon(R.drawable.icon_notification_pay)
            //.setContentIntent(resultadoIntent)
            .setAutoCancel(true)
            .build()
        val notificationManager = getSystemService(NotificationManager::class.java)
        //val notificationManager = NotificationManagerCompat.from(this)
        //if(ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED){return}
        notificationManager.notify(1,notification)
    }
    /*private fun notificar(notifTitle:String,notifBody:String,){
        val intentRtaNotif = Intent(applicationContext,MainActivity::class.java)
        val resultPendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intentRtaNotif)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notificacion = NotificationCompat.Builder(this,idCanal).also{
            it.setContentTitle(notifTitle)
            it.setContentText(notifBody)
            it.setSmallIcon(R.drawable.icon_notification_pay)
            it.priority=NotificationCompat.PRIORITY_HIGH
            it.setContentIntent(resultPendingIntent)
            it.setAutoCancel(true)
        }.build()
        val notificationManager = NotificationManagerCompat.from(this)
        //if(ActivityCompat.checkSelfPermission(this,Manifest.permission.))
    }*/
}