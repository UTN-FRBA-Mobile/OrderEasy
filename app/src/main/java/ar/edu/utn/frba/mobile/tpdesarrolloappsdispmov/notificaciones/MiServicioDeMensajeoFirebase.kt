package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.app.TaskStackBuilder
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.MainActivity
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pedidosApi.ServicioDePedidos
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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
        createChannel()
        showNotifications(message)
    }
    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val canal1 = NotificationChannel(
                getString(R.string.CHANNEL1_ID),
                getString(R.string.CHANNEL1_NAME),
                NotificationManager.IMPORTANCE_HIGH).apply {
                description = getString(R.string.CHANNEL1_DESCRIPTION)
            }
            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal1)
        }
    }
    private fun showNotifications(msj: RemoteMessage){
        val payload:MutableMap<String,String> = msj.data
        val action = payload.get("action").toString()
        val total = payload.get("total").toString()
        val cantidad = payload.get("cantidad").toString()
        val pago = payload.get("pago").toString()

        val intent=Intent(this, MainActivity::class.java)
        if (action == "share") {
            intent
                .putExtra("action",action)
                .putExtra("total", total)
                .putExtra("cantidad", cantidad)
                .putExtra("pago", pago)
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
        if(action=="share") {
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
}
