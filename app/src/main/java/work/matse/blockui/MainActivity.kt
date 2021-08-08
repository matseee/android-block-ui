package work.matse.blockui

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnHideApplication = findViewById<Button>(R.id.btnHideApplication)
        btnHideApplication.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed(Runnable {
            initService()
        }, 200)
    }

    private fun initService() {
        if (checkOverlayDisplayPermission()) {
            startService()
        } else {
            requestOverlayDisplayPermission()
        }
    }

    private fun startService() {
        val overlayService = Intent(this, ToastService::class.java)
        ContextCompat.startForegroundService(this, overlayService)
    }

    private fun checkOverlayDisplayPermission(): Boolean {
        return Settings.canDrawOverlays(this)
    }

    private fun requestOverlayDisplayPermission() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Screen Overlay Permission Needed")
        builder.setMessage("Enable 'Display over other apps' from System Settings.")
        builder.setPositiveButton("Open Settings", DialogInterface.OnClickListener { _, _ ->
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, Activity.RESULT_OK)
        })

        val dialog = builder.create()
        dialog.show()
    }
}