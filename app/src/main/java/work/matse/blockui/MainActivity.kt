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

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.btnPowerOff).setOnClickListener {
            finishAndRemoveTask()
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
        val overlayService = Intent(this, NotificationService::class.java)
        ContextCompat.startForegroundService(this, overlayService)
    }

    private fun checkOverlayDisplayPermission(): Boolean {
        return Settings.canDrawOverlays(this)
    }

    private fun requestOverlayDisplayPermission() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(R.string.permissionTitle)
        builder.setMessage(R.string.permissionDescription)
        builder.setPositiveButton(R.string.permissionGoToSettings, DialogInterface.OnClickListener { _, _ ->
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, Activity.RESULT_OK)
        })

        val dialog = builder.create()
        dialog.show()
    }
}