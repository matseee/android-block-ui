package work.matse.blockui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import work.matse.blockui.overlay.OverlayService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(baseContext,"MainActivity.onCreate()", Toast.LENGTH_LONG).show();

        startService()
    }

    private fun startService() {
        val overlayService = Intent(this, OverlayService::class.java)
        ContextCompat.startForegroundService(this, overlayService)
    }
}