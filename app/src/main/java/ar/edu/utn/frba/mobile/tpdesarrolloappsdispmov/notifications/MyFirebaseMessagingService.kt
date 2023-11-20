package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notifications

import android.app.NotificationManager
import android.app.PendingIntent
//import android.app.TaskStackBuilder
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
//import androidx.core.net.toUri
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.InitNotifications
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val payload:MutableMap<String,String> = message.data
        Log.i("NOTIFICACION----->",payload.get("action").toString())
        Log.i("NOTIFICACION----->",message.data.toString())
        /*message.notification?.let{
            Log.i("NOTIFICACION----->",it.body.toString())
        }
        Log.i("NOTIFICACION----->",message.from.toString())*/

        showNotification(message,payload.get("action").toString(),payload.get("total").toString(),payload.get("cantidad").toString(),payload.get("pago").toString())
    }
    private fun showNotification(msj:RemoteMessage,action:String,total:String,cantidad:String,pago:String){
        val notificationManager = getSystemService(NotificationManager::class.java)
        Log.i("notificacion---->>>",action)
        if(action=="invite") {
            val notification1 = NotificationCompat.Builder(this,InitNotifications.NOTIFICATION_ID_CHANNEL).also {
                it.setContentTitle(msj.notification?.title)
                it.setContentText(msj.notification?.body)
                it.setSmallIcon(R.drawable.baseline_restaurant_24)
                it.setPriority(NotificationCompat.PRIORITY_HIGH)
                //it.setStyle(NotificationCompat.BigTextStyle())
                it.setAutoCancel(true)
            }.build()
            notificationManager.notify(1,notification1)
        } else {
            Log.i("ES UNA ACTION DIVIDE---->>>",action)
            /* Con deepLink:
            val notifyIntent = Intent(
                Intent.ACTION_VIEW,
                "order://easy.com/reqTicket".toUri(),
                this,
                MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  }
            */
            val notifyIntent = Intent(this,ReqTicketActivity::class.java)
            notifyIntent.putExtra("total",total)
            notifyIntent.putExtra("cantidad",cantidad)
            notifyIntent.putExtra("pago",pago)

            val pending:PendingIntent = PendingIntent.getActivity(this,0,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            /* Con TaskStackBuilder:
            val pending: PendingIntent = TaskStackBuilder.create(this).run{
                addNextIntentWithParentStack(notifyIntent)
                getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }
            */

            val notification2 = NotificationCompat.Builder(this,InitNotifications.NOTIFICATION_ID_CHANNEL)
                .setContentTitle(msj.notification?.title)
                .setContentText(msj.notification?.body)
                .setSmallIcon(R.drawable.baseline_restaurant_24)
                .setStyle(NotificationCompat.BigTextStyle().bigText(msj.notification?.body))
                //.setContentIntent(pending)
                .addAction(R.drawable.baseline_navigate_next_24,"RESPONDER",pending)
                //.addAction(R.drawable.baseline_navigate_next_24,"Rechazar",resultPendingIntentNot )
                .setAutoCancel(true)
                .build()
            notificationManager.notify(1,notification2)
        }
    }
}