package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
//import android.content.Context
import android.app.TaskStackBuilder
//import android.app.TaskStackBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.MainActivity
//import androidx.core.net.toUri
//import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.InicializarNotificaciones
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pedidosApi.ServicioDePedidos
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//val Context.dataStore by preferencesDataStore(name="USER_DATA")
class MiServicioDeMensajeoFirebase: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            ServicioDePedidos.instance.actualizarToken(token)
        }
    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //val payload:MutableMap<String,String> = message.data
        //Log.i("NOTIFICACION----->",payload.get("action").toString())
        Log.i("NOTIFICACION----->",message.data.toString())
        createChannel()
        Log.i("**onMessageReceived","LUEGO DE CREAR CANAL")
        showNotifications(message)//,payload.get("action").toString(),payload.get("total").toString(),payload.get("cantidad").toString(),payload.get("pago").toString())
        Log.i("**onMessageReceived","LUEGO DE CREAR SHOWNOTIFICATION")
    }
    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var canal1 = NotificationChannel(
                getString(R.string.CHANNEL1_ID),
                getString(R.string.CHANNEL1_NAME),
                NotificationManager.IMPORTANCE_HIGH).apply {
                description = getString(R.string.CHANNEL1_DESCRIPTION)
            }
            /*var canal2 = NotificationChannel(
                getString(R.string.CHANNEL2_ID),
                getString(R.string.CHANNEL2_NAME),
                NotificationManager.IMPORTANCE_HIGH).apply {
                description = getString(R.string.CHANNEL2_DESCRIPTION)
            }*/
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal1)
            //manager.createNotificationChannel(canal2)
        }
    }
    private fun showNotifications(msj: RemoteMessage){
        val payload:MutableMap<String,String> = msj.data
        val action = payload.get("action").toString()
        val total = payload.get("total").toString()
        val cantidad = payload.get("cantidad").toString()
        val pago = payload.get("pago").toString()
        val idRival = payload?.get("idRival")?.toInt()
        val nombRival = payload.get("nombRival").toString()
        val idPartida = payload.get("idPartida").toString()

        Log.i("**showNotifications1-->",action)
        val intent=Intent(this, MainActivity::class.java)
        if (action == "share") {
            intent
                .putExtra("action",action)
                .putExtra("total", total)
                .putExtra("cantidad", cantidad)
                .putExtra("pago", pago)
        }
        if (action == "desafio") {
            intent
                .putExtra("action", action)
                .putExtra("idRival",idRival)
                .putExtra("nombRival", nombRival)
        }
        if (action == "jugar"){
            intent
                .putExtra("action",action)
                .putExtra("idPartida",idPartida)
        }
        intent.apply {
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pending: PendingIntent = TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val notificacion = NotificationCompat.Builder(this,getString(R.string.CHANNEL1_ID))
            .setContentTitle(msj.notification?.title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(msj.notification?.body))
            .setSmallIcon(R.drawable.baseline_restaurant_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        if(action=="share"||action=="desafio") {
            notificacion.setContentIntent(pending)
        }
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED){
            return
        }
        with(NotificationManagerCompat.from(this)){
            notify(1,notificacion.build())
        }
    }
/*
    private fun showNotification(msj:RemoteMessage,action:String,total:String,cantidad:String,pago:String){
        val notificationManager = getSystemService(NotificationManager::class.java)
        //Log.i("notificacion---->>>",action)
        if(action=="invite") {
            val notification1 = NotificationCompat.Builder(this,getString(R.string.CHANNEL1_ID)).also {
                it.setContentTitle(msj.notification?.title)
                it.setContentText(msj.notification?.body)
                it.setSmallIcon(R.drawable.baseline_restaurant_24)
                it.setPriority(NotificationCompat.PRIORITY_HIGH)
                //it.setStyle(NotificationCompat.BigTextStyle())
                it.setAutoCancel(true)
            }.build()
            if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED){
                return
            }
            notificationManager.notify(1,notification1)
        } else {
            Log.i("ES UNA ACTION DIVIDE---->>>",action)
            /* Con deepLink:
            val notifyIntent = Intent( Intent.ACTION_VIEW,
                "order://easy.com/reqTicket".toUri(), this,
                MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  }
            */
            val notifyIntent = Intent(this,ReqTicketActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            /*notifyIntent.putExtra("total",total)
            notifyIntent.putExtra("cantidad",cantidad)
            notifyIntent.putExtra("pago",pago)*/

            val pending = PendingIntent.getActivity(this,0,notifyIntent,PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
            // Con TaskStackBuilder :
            /*val pending: PendingIntent = TaskStackBuilder.create(this).run{
                addNextIntentWithParentStack(notifyIntent)
                getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }*/

            val notification2 = NotificationCompat.Builder(this,getString(R.string.CHANNEL1_ID))//.apply {
                .setContentTitle(msj.notification?.title)
                .setContentText(msj.notification?.body)
                .setSmallIcon(R.drawable.baseline_restaurant_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pending)
                .setAutoCancel(true)
            //}
            if(ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED){
                return
            }
            with(NotificationManagerCompat.from(this)){
                notify(1,notification2.build())
            }
               /* .setContentTitle(msj.notification?.title)
                //.setContentText("pago dividido")
                .setStyle(NotificationCompat.BigTextStyle().bigText(msj.notification?.body))
                //.setStyle(NotificationCompat.BigTextStyle(msj.notification?.body))
                .setSmallIcon(R.drawable.baseline_restaurant_24)
                .setStyle(NotificationCompat.BigTextStyle().bigText(msj.notification?.body))
                .setContentIntent(pending)
                //.addAction(R.drawable.baseline_navigate_next_24,"RESPONDER",pending)
                //.addAction(R.drawable.baseline_navigate_next_24,"Rechazar",resultPendingIntentNot )
                .setAutoCancel(true)
                .build()*/



            //notificationManager.notify(1,notification2)
        }
    }
*/
}
