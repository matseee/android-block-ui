package work.matse.blockui

import android.app.Service
import android.content.Intent

import android.os.IBinder
import android.widget.Toast

class OverlayService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate();
        Toast.makeText(baseContext,"onCreate()", Toast.LENGTH_LONG).show();
    }

    override fun onDestroy() {
        super.onDestroy();
        Toast.makeText(baseContext,"onDestroy()", Toast.LENGTH_LONG).show();
    }
}