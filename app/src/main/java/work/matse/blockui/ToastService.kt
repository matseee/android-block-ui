package work.matse.blockui

import android.app.PendingIntent
import android.app.Service
import android.content.Intent

import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class ToastService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, BlockUIService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.app_desc))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        log("Create foreground process")
        startForeground(1, notification)
        return START_NOT_STICKY
    }
    override fun onDestroy() {
        super.onDestroy();
        Toast.makeText(baseContext,"OverlayService.onDestroy()", Toast.LENGTH_LONG).show();
    }
}