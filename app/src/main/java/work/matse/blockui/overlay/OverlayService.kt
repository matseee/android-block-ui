package work.matse.blockui.overlay

import android.app.PendingIntent
import android.app.Service
import android.content.Intent

import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import work.matse.blockui.Constants
import work.matse.blockui.MainActivity
import work.matse.blockui.R

class OverlayService : Service() {

    override fun onCreate() {
        super.onCreate();
        Toast.makeText(baseContext,"OverlayService.onCreate()", Toast.LENGTH_LONG).show();
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra(Constants.CHANNEL_ID)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, getString(R.string.channel_id))
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.app_desc))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)// 2
        return START_NOT_STICKY
    }
    override fun onDestroy() {
        super.onDestroy();
        Toast.makeText(baseContext,"OverlayService.onDestroy()", Toast.LENGTH_LONG).show();
    }
}