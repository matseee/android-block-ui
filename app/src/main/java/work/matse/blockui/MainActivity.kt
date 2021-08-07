package work.matse.blockui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService()

        log("Started background service")
    }

    private fun startService() {
        val overlayService = Intent(this, ToastService::class.java)
        ContextCompat.startForegroundService(this, overlayService)
    }
}