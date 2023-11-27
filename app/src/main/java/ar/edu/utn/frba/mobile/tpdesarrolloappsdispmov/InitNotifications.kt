package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class InitNotifications : Application() {
    override fun onCreate() {
        super.onCreate()
        createChannel()
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
}