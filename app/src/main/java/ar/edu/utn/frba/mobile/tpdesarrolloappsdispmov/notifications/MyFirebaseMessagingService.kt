package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.material.icons.materialIcon
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.InitNotifications
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.MainActivity
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //println()
        val accion:MutableMap<String,String> = message.data
        Log.i("NOTIFICACION----->",accion.get("action").toString())
        Log.i("NOTIFICACION----->",message.data.toString())
        /*message.notification?.let{
            Log.i("NOTIFICACION----->",it.body.toString())
        }
        Log.i("NOTIFICACION----->",message.from.toString())*/

        showNotification(message,accion.get("action").toString())
    }
    private fun showNotification(msj:RemoteMessage,action:String){
        val notificationManager = getSystemService(NotificationManager::class.java)
        if(action=="invite") {
            val notification1 = NotificationCompat.Builder(this,InitNotifications.NOTIFICATION_ID_CHANNEL)
                .setContentTitle(msj.notification?.title)
                .setContentText(msj.notification?.body)
                .setSmallIcon(R.drawable.icon_notification_pay)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(1,notification1)
        } else {
            val notifyIntentOk = Intent(this, TakeInviteActivity::class.java)
            val notifyIntentNot = Intent(this, RefuseInviteActivity::class.java)
            val resultPendingIntentOk = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(notifyIntentOk)
                getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }
            val resultPendingIntentNot = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(notifyIntentNot)
                getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }
            val notification2 = NotificationCompat.Builder(this,InitNotifications.NOTIFICATION_ID_CHANNEL)
                .setContentTitle(msj.notification?.title)
                .setContentText(msj.notification?.body)
                .setSmallIcon(R.drawable.icon_notification_pay)
                //.setContentIntent(resultPendingIntent)
                .addAction(R.drawable.pagodesafio_24dp,"Aceptar",resultPendingIntentOk)//PendingIntent de aceptar)
                .addAction(R.drawable.pagodesafio_24dp,"Rechazar",resultPendingIntentNot )
                .setAutoCancel(true)
                .build()
            notificationManager.notify(1,notification2)
        }
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